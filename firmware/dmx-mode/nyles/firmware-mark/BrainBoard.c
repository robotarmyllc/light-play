/*********************************************
* vim: set ai sw=4 ts=8 si et :
* Author: Nyles Nettleton
* Brain Board Control Code
* 
* Clock frequency     : Crystal clock 16 Mhz

TODO:
- Get timer/interrupt based PWM working
- Add delta code
- Add DMX code

*********************************************/
#include <avr/io.h>
#include <avr/interrupt.h>
#define F_CPU 16000000UL  // 16 MHz
#include <util/delay.h>
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// LINK LIBM.A

// Comment out for older REV D boards.
#define LED_INVERT   //  Rev.E Boards use inverted LED B-G are also swapped

    // Bit definitions for servos
#define SERVOX   0x02
#define SERVOY   0x04
#define SERVOZ   0x08
#define SERVO_ON 0x01

    // Servo angle definitions
#define MIN_SERVO 44            // Was 55...  turns out these are inverted too
#define MAX_SERVO 139           // Was 140...  max is min and vice versa
                                //  so why oh why are we losing range on the output?  Hmm.
//#define FLAT_SERVO 120
#define FLAT_SERVO 100   // Calibration value

#ifdef LED_INVERT
    // Bit definitions for LEDs  REV E Board
    #define LED_RED   0x08
    #define LED_GREEN 0x20
    #define LED_BLUE  0x40
#else
    // Bit definitions for LEDs   REV D board
    #define LED_RED   0x08
    #define LED_GREEN 0x40
    #define LED_BLUE  0x20
#endif

    #define DEF_RED 0             // Default red level
    #define DEF_GRN 0             // Default green level
    #define DEF_BLU 255           // Default blue level

    #define NOSIG_RED 255         // No-signal red level
    #define NOSIG_GRN 100         // No-signal green level
    #define NOSIG_BLU 0           // No-signal blue level

    // Definitions for delta kinetics
#define DK_A 3.0        // Length of hip, flat piece
#define DK_B 8.0        // Length of ankle, tube
#define DK_BR0 1.5      // Radius of base (r0)
#define DK_ER0 1.0      // Radius of effector (R0)
#define DK_ZMAX 9.5     // Z max input (when x,y=0)
#define DK_ZMIN 5.0     // Z min input (when x,y=0)

#define DK_PI 3.14159265
#define DK_THETA0 0
#define DK_THETA1 ( 120.0 * DK_PI / 180.0)
#define DK_THETA2 (-120.0 * DK_PI / 180.0)

    // Acceleration control
#define ACC_TICK 0.020  // Tick, also PWM period in seconds
// #define ACC_TICK 0.015  // Tick, also PWM period in seconds
#define SLEW_RANGE 7.0  // Range that the 0-255 input gets normalized to, actually +-2.0
/*
 * The following parameters are the main tuning for acceleration
 *   The following example is way slower than 'reality',
 *   and ACC_TIME has been adjusted to match the same ratio as the 'stock' code
 *
#define SLEW_TIME 3.0   // Time to slew the servo completely, guessing half a second
#define ACC_TIME 42      // Time to go from standing start to full speed, in ACC_TICKs
*/
/*
#define SLEW_TIME 0.2   // Time to slew the servo completely, guessing half a second
#define ACC_TIME 6      // Time to go from standing start to full speed, in ACC_TICKs
*/
#define SLEW_TIME 0.3   // Time to slew the servo completely, guessing half a second
#define ACC_TIME 9      // Time to go from standing start to full speed, in ACC_TICKs
                        // Maximum velocity, in distance/ACC_TICK
#define ACC_MAX_V (SLEW_RANGE/(SLEW_TIME/ACC_TICK))
                        // Acceleration increment, in distance/tick
#define ACC_INC (ACC_MAX_V/ACC_TIME)

    // DMX
#define DMX_MAX (86 * 6)      // Anything past this channel is illegal, and naughty

void led_check( void );
void dmx_read( void );
void compute (unsigned char x, unsigned char y, unsigned char z);

unsigned char lx, ly, lz;           // Current cartesian XYZ values
    /* The next PWM event */
unsigned char nextevent;
unsigned char int_pwmserv;
unsigned char pwm_eptr;         // Event pointer
unsigned char pwm_event[3];
unsigned char pwm_tptr;         // Time pointer
unsigned int pwm_time[3];

float serv_pos[3];          // Servo positions, nominally 0-180, 0=x, 1=y, 2=z
// float serv_inc;                         // Increment

// unsigned char up, down, toggle;
// unsigned char upC, downC;

    /* PWM period */
unsigned char pwm_pcount;
unsigned char int_ppserv;

    /* LED brightness */
unsigned char led_pwm[3];               // 1 = Red, 2 = Green, 3 = Blue
unsigned int now2cnt, last2cnt;

//
// DELTA KINETICS
//
//    ported from Arduino, heaven help me
//
float phi[3], diff[3], phi_prev[3];
float phiA[3];
float xg, yg, zg;               // Position of effector
float R[3][3];
float r[3][3];

float theta[3];

//
// ACCELERATION
//
unsigned char accel_enable;     // Enable acceleration control
float v_x, v_y, v_z;            // Velocity vector
float vp_x, vp_y, vp_z;         // Potential location based on velocity
float t_x, t_y, t_z;            // Target location
float p_x, p_y, p_z;            // Previous location
float d_x, d_y, d_z;            // Delta location
float d_x2, d_y2, d_z2;         // Delta location
float velocity, old_v;          // Current and old 3-space velocity magnitude
float vt_mag, vpt_mag;          // Distances from points to target

//
// DMX
//
//   May want to put in some semaphores if we're in danger of 
//   buffer overrun.
//
unsigned char dmx_ibuf[6];      // Input buffer, copy to command when complete
unsigned char dmx_iptr;         // Points into input buffer, non-zero means we're recieving
unsigned char dmx_cbuf[6];      // Command buffer

unsigned int  dmx_chan;         // Which slot to listen for
unsigned int  dmx_rxp;          // How far into the DMX packet
unsigned char dmx_recv;         // Flags that we're receiving the packet
unsigned char dmx_ichar;        // The latest character

//
// Activity timeout
//
unsigned int timeout;           // If we get to 5 seconds, change color to defaults

//
// PWM period service
//
ISR(TIMER0_COMPA_vect)
{
    /* Note that the interrupt has been serviced */
    int_ppserv = 1;

    TIMSK0 = 0x00;         // Disable timer 0 interrupt
}

//
// Main PWM waveform service
//
ISR(TIMER1_COMPA_vect)
{
    PORTB &= ~(nextevent);        // Clear the PWM bit
    /* Note that the interrupt has been serviced */
    int_pwmserv = 1;

    TIMSK1 = 0x00;  // Disable timer 1 interrupt

}

//
// USART Interrupt vector for while we're running in compute()
//     Interruts get turned on while compute() is running, since
//     it takes so long to do float math that the serial input
//     buffer overflows if you're just polling.
//
//     This interrupt gets turned off for the PWM phase, since
//     we don't want to interfere with our pretty little servos.
//
ISR(USART_RX_vect)
{
    dmx_read();
}

//
// LED PWM
//    This is a polled function, since the timer it's following
//    is running so slow.  It also doesn't interfere with the PWM
//    this way.
//
void led_check( void )
{
    now2cnt = TCNT2;
    if (now2cnt == last2cnt) return;

    last2cnt = now2cnt;

#ifdef LED_INVERT
    if (now2cnt > led_pwm[0]) PORTD &= ~LED_RED;     // We've hit the PWM limit for Red
    else PORTD |= LED_RED;

    if (now2cnt > led_pwm[1]) PORTD &= ~LED_GREEN;   // We've hit the PWM limit for Green
    else PORTD |= LED_GREEN;

    if (now2cnt > led_pwm[2]) PORTD &= ~LED_BLUE;    // We've hit the PWM limit for Blue
    else PORTD |= LED_BLUE;
#else
    if (now2cnt > led_pwm[0]) PORTD |= LED_RED;     // We've hit the PWM limit for Red
    else PORTD &= ~LED_RED;
    
    if (now2cnt > led_pwm[1]) PORTD |= LED_GREEN;   // We've hit the PWM limit for Green
    else PORTD &= ~LED_GREEN;
    
    if (now2cnt > led_pwm[2]) PORTD |= LED_BLUE;    // We've hit the PWM limit for Blue
    else PORTD &= ~LED_BLUE;
#endif
}

// _delay_ms uses a floating point datatype if you call
// that function in many places in your code then it becomes
// very fat. An integer is enough for us:
//
// delay x milliseconds:
void delay_ms(unsigned int xms)
{
    while(xms){

        _delay_ms(0.96);
        xms--;

    }
}

//
// DMX Monitor Function
//
//    Watches for break, then starts watching for the start channel,
//    then fills input buffer.
//
//    Once the current command queue is flagged complete, new complete
//    command blocks are loaded and buffer is marked empty
//
//    Note that the first byte of the stream is supposed to be a 0, and
//    the first real data byte follows, at channel 1
//
void dmx_read( void )
{
    unsigned char i, istat;

    // Check to see if we're in the middle of a packet
    //
    // Check for frame error 0x10 in UCSR0A, which indicates break
    //

        //
        // Check for received character, even if it's a break
        //
    if (!(UCSR0A & 0x80)) return;       // Nothing going on, go home

    if (!dmx_recv) {            // Entry point is a break

        istat = UCSR0A;
        dmx_ichar = UDR0;

        // Not into a packet yet, check for frame error
        if (istat & 0x10) { // WE GOT A BREAK
// PORTC = PORTC | (1 << 4);
            // while ((UCSR0A & 0x1c) != 0) { // Clear out junk
                // // Flush the input buffer
                // dmx_ichar = UDR0;
            // }
            dmx_recv = 1;       // Flag that we're now in a packet
            dmx_rxp = 0;
            dmx_iptr = 0;

        } else {
            while (UCSR0A & 0x80) {     // Get rid of any extraneous chars
                dmx_ichar = UDR0;
            }
        }
    }
    
    if (dmx_recv) {
        if ((UCSR0A & 0x1C) != 0) {            // Check for broken char 1C 08
            dmx_ichar = UDR0;
            // if (dmx_rxp != 0) {
// PORTC = PORTC | (1 << 4);
                dmx_recv = 0;           // Can't have any broken characters in the data input
            // }
            while ((UCSR0A & 0x1c) != 0) {            // If it's a broken char, discard it
                dmx_ichar = UDR0;
            }

        } else {

            while (UCSR0A & 0x80) { // WE GOT A CHARACTER
                dmx_ichar = UDR0;

                if ((dmx_rxp == 0) && (dmx_ichar != 0))  {
                    dmx_recv = 0;   // Check for invalid start byte, haven't seen this happen
                } else {

// PORTC = PORTC | (1 << 5);
                    if (dmx_rxp == dmx_chan) dmx_iptr = 0;      // If we're at the first channel, init pointer

                    // IF we're in the channel range, load up the input buffer
                    if ((dmx_rxp >= dmx_chan) && (dmx_rxp <= (dmx_chan + 5))) {
                        dmx_ibuf[dmx_iptr] = dmx_ichar;
                        dmx_iptr++;

                        // If this was the last channel, do post
                        if (dmx_rxp >= (dmx_chan + 5)) {
                            // Transfer to command buffer  PROBABLY NOT NEEDED
                            for (i = 0; i < 6; i++) {
                                dmx_cbuf[i] = dmx_ibuf[i];
                            }
                            // Load up the LEDs
                            for (i = 0; i < 3; i++) {
                                if ((dmx_chan < DMX_MAX) && (dmx_chan > 1))
                                    led_pwm[i] = ~dmx_ibuf[i]&0xFF;      // Inverted
                            }
                            // Check for 0,0,0 invalid value
                            if ((dmx_ibuf[3] != 0) || (dmx_ibuf[4] != 0) || (dmx_ibuf[5] != 0)) {
                                // Set the servo cartesians if it's a valid value
                                lx = ~dmx_ibuf[3];
                                ly = ~dmx_ibuf[4];
                                lz = ~dmx_ibuf[5];
                            }

                            timeout = 0;

                            dmx_recv = 0;
                            dmx_iptr = 0;
                        }
                    }
                    dmx_rxp++;          // Increment packet index, zero is a valid value
                }
            }
        }
    }
}

//
// Delta Kinetics - compute servo angles from cartesian XYZ
//
void compute (unsigned char x, unsigned char y, unsigned char z)
{
    int i;
    float B, beta, tempf, temp2f, temp3f, tv;
    unsigned char destinated;

    // Enable RX interrupt, otherwise we get input buffer overflow
    UCSR0B = 0x90;

    destinated = 0;

    led_check ();       // Check LED PWM
  
    // Set target location, normalized to +-2.0
    //   These are hard coded for speed                      ORIGINAL PRE ACCEL
    // xg = (((float)x) - 127.0) / 64.0;  // Should result in +-2.0
    // yg = (((float)y) - 127.0) / 64.0;
    // zg = (((float)z) / 56.88888889) + 5.0;
    //t_x = (((float)x) - 127.0) / 64.0;  // Should result in +-2.0
    t_x = (((float)x) - 127.0) / 45.0;  // Should result in +-2.0
    t_y = (((float)y) - 127.0) / 45.0;
    t_z = (((float)z) / 56.88888889) + 5.0;

    led_check ();       // Check LED PWM

    if (accel_enable) {
        //
        // CALCULATE NEXT POINT TO HEAD TOWARDS BASED ON VELOCITY, ACCELERATION AND LOCATION
        //
        /* 
         * hack it up into bits, deal with acceleration and deceleration
         *  And then my brain went... oogle fluga munga bargh
         *  which eventually means:
         *  - Calculate distance from current point to target (D0)
         *  - Calculate new coordinates from current point using current velocity (P1, V)
         *  - Calculate distance from new coords to target (D1)
         *  - if (D1 > (D0 - V)) we aren't heading straight for the target
         *  -    if so, decelerate along V until V=0.  Then head to the target.
         *  may need to implement an error window in the comparison since it's
         *  in floating point.
         *  All this is simpler than messing with just accelerating towards the target,
         *  since that's the basic orbital equation, which isn't too likely to complete.
         */

        /*
         * Calculate distance from here to target
         */
            // Direction vector to target
        d_x = t_x - p_x;
        d_y = t_y - p_y;
        d_z = t_z - p_z;

        vt_mag = sqrt(d_x*d_x + d_y*d_y + d_z*d_z);    // Here to target vector magnitude

        /*
         *  Calculate new coordinates from current point using current velocity (P1, V)
         */
        vp_x = p_x + v_x;
        vp_y = p_y + v_y;
        vp_z = p_z + v_z;

         /*
          * Calculate distance from new coords to target (D1)
          */
            // Direction vector to target
        d_x2 = t_x - vp_x;
        d_y2 = t_y - vp_y;
        d_z2 = t_z - vp_z;

    led_check ();       // Check LED PWM
  
        vpt_mag = sqrt(d_x2*d_x2 + d_y2*d_y2 + d_z2*d_z2);    // Velocity point to target vector magnitude

        /*
         * Check to see if we're heading straight (enough) at the target
         */
        tempf = (vpt_mag - (vt_mag - velocity));         // Check to see how close we are to collinear


        /*
         * TUNING VALUE
         */
        if (fabs(tempf) < 0.0001) {   // We're heading enough in the right direction

            // Check to see if it's time to accelerate or decelerate
                // Calculate deceleration distance for this velocity
                //   since time is in ticks, which we're measuring in
                //
            // I worked out this equation (((V + R) / 2) * I) + R
            //   gives the distance to decelerate based on:
            //   V velocity
            //   R fractional part of velocity
            //   I integer part of velocity
            //
            //  Velocity has to get scaled to ACC_INC units
            //
            if (velocity > ACC_INC) {
                tv = velocity / ACC_INC;                // Velocity scaled to ACC_INC units
                temp2f = (tv) - 1.0;
                tempf = floor(temp2f);                  // Integer portion of velocity
                temp2f -= tempf;                        // Fractional portion of velocity
                temp3f = (((tv + temp2f)/2) * tempf) + temp2f;    // Decel distance in ACC_INC units
                temp3f = temp3f * ACC_INC;              // Decel distance
            } else {
                temp3f = velocity;
            }

    led_check ();       // Check LED PWM
  
            // If it's time to decelerate
            if (vt_mag <= temp3f) {
                if (velocity <= ACC_INC) {          // Are we within range of stopping
                    destinated = 1;
                    velocity = 0.0;
                } else {
                    velocity -= ACC_INC;            // Decelerate
                }

            } else {
        // if (vpt_mag > 0.0)
// PORTC = PORTC & ~(1 << 4);       // Trigger scope

                // If there's room to accelerate
                if (vt_mag > (temp3f + velocity + ACC_INC)) {
                    velocity += ACC_INC;
                }

                // Otherwise, no mod to velocity, cruise at this speed

            }

        } else {                    // We're not heading in the right direction, decelerate
            velocity -= ACC_INC;
        }

        // Keep velocity within allowable range
        velocity = fmin (velocity, ACC_TIME);     // Cap at maximum V
        velocity = fmax (velocity, 0.0);          // Limit to positive or 0

        // Calculate velocity component vectors in XYZ
        
        if ((vt_mag > 0.0) && !destinated) {
            tempf = velocity / vt_mag;          // Scale factor for vector: Velocity / distance 
            v_x = d_x * tempf;                  // Fractional vector in X
            v_y = d_y * tempf;                  // Fractional vector in Y
            v_z = d_z * tempf;                  // Fractional vector in Z
        } else {
            v_x = 0.0;                          // Zero out velocity vector components
            v_y = 0.0;
            v_z = 0.0;
        }

    led_check ();       // Check LED PWM
  
        if (destinated) {
            xg = t_x;                   // Final X target
            yg = t_y;                   // Final Y target
            zg = t_z;                   // Final Z target
        } else {
            xg = p_x + v_x;             // New X interim target
            yg = p_y + v_y;             // New Y interim target
            zg = p_z + v_z;             // New Z interim target
        }

        // Save the next iteration's 'here'
        p_x = xg;
        p_y = yg;
        p_z = zg;

// PORTC = PORTC | (1 << 4);       // Trigger scope

    } else {            // Acceleration off
        xg = t_x;                   // X target
        yg = t_y;                   // Y target
        zg = t_z;                   // Z target
    }

    led_check ();       // Check LED PWM

    //
    // Do the normal cartesian to delta calculation below
    //

    for (i = 0; i < 3; i++) {
        r[i][0] = 0;
        r[i][1] = DK_BR0 * cos(theta[i]);
        r[i][2] = DK_BR0 * sin(theta[i]);

    led_check ();       // Check LED PWM
  
        R[i][0] = zg + 0; 
        R[i][1] = xg + DK_ER0 * cos(theta[i]);
        R[i][2] = yg + DK_ER0 * sin(theta[i]);

    led_check ();       // Check LED PWM
  
    }
    for (i = 0; i < 3; i++) {
        // float Cr =  (R[i][1]-r[i][1])*cos(theta[i]) + (R[i][2]-r[i][2])*sin(theta[i]);
        float Cr =  (R[i][1]-r[i][1])*cos(theta[i]);
        Cr += (R[i][2]-r[i][2])*sin(theta[i]);

    led_check ();       // Check LED PWM
  
        // float Ct = -(R[i][1]-r[i][1])*sin(theta[i]) + (R[i][2]-r[i][2])*cos(theta[i]);
        float Ct = -(R[i][1]-r[i][1])*sin(theta[i]);
        Ct += (R[i][2]-r[i][2])*cos(theta[i]);

    led_check ();       // Check LED PWM
  
        float Cz = zg;
        float A = DK_A;
        tempf = DK_B*DK_B - Ct*Ct;
        B = sqrt(tempf);
        float C = sqrt(Cr*Cr + Cz*Cz);

    led_check ();       // Check LED PWM
  
        float alpha = atan2(Cz,Cr)*180.0/DK_PI;
        float gamma = (A*A+C*C-B*B)/(2.0 * A*C);  // Per the recommendation in the manual, moved this calc outside abs function

    led_check ();       // Check LED PWM
  
        if (fabs(gamma) <= 1.0) {
            // tempf = (A*A+C*C-B*B)/(2.0 * A*C);
            tempf = gamma;
            beta = acos(tempf) * 180.0 / DK_PI;
            // float beta = acos((A*A+C*C-B*B)/(2*A*C)) * 180.0 / DK_PI;
            phiA[i] = -(alpha-beta);

    led_check ();       // Check LED PWM
  
            // phiA[i] = constrain(phiA[i],-40.0,40.0);         // Arduino command
            if (phiA[i] < -40.0) phiA[i] = -40.0;
            if (phiA[i] >  40.0) phiA[i] =  40.0;

            // phi[i]=map(phiA[i],-40.0,40.0,DeltaServos::MIN_SERVO,DeltaServos::MAX_SERVO); // Servo range
            phi[i]= ((phiA[i] + 40.0) * ((float)(MAX_SERVO - MIN_SERVO))
                      / 80.0) + (float)MIN_SERVO;      // Servo range
        } else {
            phi[i] = phi_prev[i];

            // COMMENT ME OUT AFTER DEBUG - MAYBE
            // PORTC = PORTC | (1 << 4);       // Trigger scope

            //Serial.println("Warning: Position Out of Range");
        }
        phi_prev[i] = phi[i];
    }
    
    // Disable RX interrupt
    UCSR0B = 0x10;              // RX only, no USART interrupt

}


int main(void)
{

    unsigned char i;
    unsigned int t1max;

    // Turn on pullups for dip switches
    DDRC = 0;
    PORTC = 0x2F;
    PORTD = 0x94;

    ADMUX = 0x00;       // Set up ADC stuff to not interfere
    ADCSRA = 0x00;
    ADCSRB = 0x00;
    DIDR0 = 0x00;

    // Init DMX
    for (i = 0; i < 6; i++) {
        dmx_ibuf[i] = 0;
        dmx_cbuf[i] = 0;
    }
    dmx_iptr = 0;

    // Initialize acceleration control
    accel_enable = 1;
    velocity = 0.0;
    old_v = 0.0;             // Velocity magnitude
    v_x = 0.0;
    v_y = 0.0;
    v_z = 0.0;              // Velocity vector
    t_x = 0.0;
    t_y = 0.0;
    t_z = 0.0;              // Target location
    p_x = 0.0;
    p_y = 0.0;
    p_z = 0.0;              // Previous location

    // Construct channel
    dmx_chan =   ~PINC & 0x0F;
    // dmx_chan |= (~PINC & 0x20) << 3;     // PC5 is bit 8 - NOW IT IS ACCEL CONT ENABLE
    dmx_chan |= (~PIND & 0x04) << 4;     // PD2 is bit 7
    dmx_chan |= (~PIND & 0x10) << 1;  // was '<< 2'   // PD4 is bit 6   BUG FIX 20150708  // 5 & 6 were swapped MJK
    dmx_chan |= (~PIND & 0x80) >> 3;     // PD7 is bit 5
    //
    // Channel 0 is a mode byte, not data - so add 1
    // dmx_chan = (dmx_chan * 6) + 1;
    dmx_chan = (dmx_chan * 6);
    
    //accel_enable = ((~PINC & 0x20) != 0);      // Acceleration enable - dip switch '8'

    // dmx_chan = 1;            // Hacks to fake up channel addresses so we can test
    // dmx_chan = 86 * 6 + 1;   //    downstream logic

    dmx_rxp = 0;                // Indicates which channel has been received (first channel is 1)
    dmx_recv = 0;

    // Init Delta Kinetics
    xg = 0.0; // position of effector
    yg = 0.0;
    zg = 0.0;

    theta[0] =  0.0;
    theta[1] = ( 120.0 * DK_PI) / 180.0;
    theta[2] = (-120.0 * DK_PI) / 180.0;

    for (i = 0; i < 3; i++) {
        phi_prev[i] = 127.0;
        phi[i] = 127.0;
    }

    lx = 127;
    ly = 127;
    lz = 40;

    // toggle = 0;
    last2cnt = 0;

    // Set default servo positions
    serv_pos[0] = 90.0;
    serv_pos[1] = 90.0;
    serv_pos[2] = 90.0;

    led_pwm[0] = ~DEF_RED&0xFF;
    led_pwm[1] = ~DEF_GRN&0xFF;
    led_pwm[2] = ~DEF_BLU&0xFF;

    timeout = 0;

    //
    // Set up USART
    //
        /* Enable rx for dmx receive mode */
    UCSR0B = 0x10;              // RX only, no interrupts
    UCSR0C = 0x0e;              // Async, no parity, 2 stop bits, 8 bit
    // UCSR0C = 0x06;              // Async, no parity, 1 stop bit, 8 bit

        /* Set the baud rate */
    UBRR0H = 0x00;
    UBRR0L = 0x03;              // 250Kbaud

    //
    // Set up timer 0 - PWM period
    //
    TCCR0A = 0;
    TCCR0B = 0;  // Turn off counter for the moment
    TCNT0 = 0;
    OCR0A = 156;  // Just about 10ms
    // OCR0B = 0x00;
    // TIMSK0 = 0x02;         // A match interrupt
    TIFR0 = 0x02;

    //
    // Set up timer 1 - PWM control
    //

    TCCR1A = 0;
    TCCR1B = 0;  // Turn off counter for the moment
    // TCCR1C = 0;
    OCR1BH = 0x02;
    OCR1BL = 0x00;
    TCNT1H = 0;         // Clear count
    TCNT1L = 0;
    TIMSK1 = 0x02;         // A match interrupt
    TIFR1 = 0x02;             // Clear interrupts

    //
    // Set up timer 2 - LED PWM control
    //
    TCCR2A = 0;
    TCCR2B = 0;  // Turn off counter for the moment
    TCNT2 = 0;   // Clear counter
    // OCR2A = 156;  // Just about 10ms
    // OCR2B = 0x00;
    // TIFR2 = 0x02;
    // TCCR2B = 0x06;  // Free run, 256 prescale
    TCCR2B = 0x07;  // Free run, 1024 prescale

    // led_check ();       // Check LED PWM
    // dmx_read ();            // Look at the incoming DMX stream

    DDRB |= 0x0f;  /* Turn on the servo PWM output bits, servo control */
    // DDRC |= 3 << 4;  /* Turn on the output bits, debug output */
    DDRD |= LED_RED | LED_GREEN | LED_BLUE;  /* Turn on the LEDs */

// PORTC = PORTC | (1 << 4);       // Trigger scope

    PORTB &= ~0x01;     /* Turn on Z servo enable */

    // Set events, basically the servo port bits
    pwm_event[0] = PORTB | SERVOX;
    pwm_event[1] = PORTB | SERVOY;
    pwm_event[2] = PORTB | SERVOZ;

    // Main control loop
    while (1) {

        //
        // Check for input timeout, 5 seconds
        //
        if (timeout > (5000 / 18)) {

            // Set LEDs back to defaults
            led_pwm[0] = ~NOSIG_RED&0xFF;
            led_pwm[1] = ~NOSIG_GRN&0xFF;
            led_pwm[2] = ~NOSIG_BLU&0xFF;

        } else {
            timeout++;
        }

        
        led_check ();           // Check LED PWM
        dmx_read ();            // Look at the incoming DMX stream

        //
        // Set off PWM period timer for calculation phase
        //
            // pwm_pcount = 0;
            // int_ppserv = 0;
        TCCR0B = 0;             // Turn counter 0 off
        TCNT0 = 0;              // Clear timer 0
        // OCR0A = 78;             // Just about 5ms
        // OCR0A = 156;             // Just about 10ms
        OCR0A = 125;             // Just about 8ms
        TIFR0 = 0x02;           // Clear interrupt
        TIMSK0 = 0x02;          // Enable A match interrupt, timer 0
        TCCR0B = 0x05;          // Enable counter 0, Prescaler 1024

        sei();                  // Enable global interrupts

        led_check ();       // Check LED PWM
        dmx_read ();            // Look at the incoming DMX stream

        //
        // Compute servo angles from cartesian input
        //
//PORTC = PORTC | (1 << 4);       // Trigger scope
        compute (lx, ly, lz);
//PORTC = PORTC & ~(1 << 4);       // unTrigger scope

        for (i = 0; i < 3; i++) {
            // Set to flat if the switches are set to 0         (dmx_chan = 1)
            if ((dmx_chan == 0) || (dmx_chan >= DMX_MAX)) {
                serv_pos[i] = FLAT_SERVO;
                if (dmx_chan == 0) led_pwm[i]++;        // LED cycles if switches are 0
                if (dmx_chan >= DMX_MAX) {              // LED is red if out of range
                    if (i == 0) led_pwm[i] = 0;
                    else led_pwm[i] = 255;
                }
            } else {
                serv_pos[i] = phi[i];
                if (serv_pos[i] > MAX_SERVO) serv_pos[i] = MAX_SERVO;       // Constrain angles
                if (serv_pos[i] < MIN_SERVO) serv_pos[i] = MIN_SERVO;
            }
        }

        while (int_ppserv == 0) {       // Wait out the rest of the first 5ms
                                        //   where we do the heavy calculation
            led_check ();       // Check LED PWM
            dmx_read ();            // Look at the incoming DMX stream
        }

        int_ppserv = 0;

        //
        // Set off PWM period timer again to fill out next 10ms
        //    where we do the servo PWM
        //
        TIMSK0 = 0x00;          // Disable A match interrupt, timer 0
        TCCR0B = 0;             // Turn counter 0 off
        TCNT0 = 0;              // Clear timer 0
        OCR0A = 156;            // Just about 10ms
        TIFR0 = 0x02;           // Clear interrupt
        TIMSK0 = 0x02;          // Enable A match interrupt, timer 0
        TCCR0B = 0x05;          // Enable counter 0, Prescaler 1024

        //
        // Calculate times
        //
        //   Expected values are float 0-180
        //
        for (i = 0; i < 3; i++) {
        
            led_check ();       // Check LED PWM
            dmx_read ();            // Look at the incoming DMX stream

            // Works with our 0.5 to 2.5ms servos
            // pwm_time[i] = (unsigned int)(40000 - (unsigned int)(((float)32000.0 * serv_pos[i]) / 180.0));
            //
            // Tuned these numbers to match the range of motion we get with the Arduino code
            pwm_time[i] = (unsigned int)(40000 - (unsigned int)(((float)30500.0 * serv_pos[i]) / 180.0));

        }

        for (i = 0; i < 3; i++) {               // Do the 3 servos in series since there's time

            led_check ();           // Check LED PWM
            dmx_read ();            // Look at the incoming DMX stream

            TCCR1B = 0;             // Turn counter 1 off
            TIMSK1 = 0x00;          // Disable A match interrupt, timer 1

            TCNT1H = 0;             // Clear timer 1
            TCNT1L = 0;

            int_pwmserv = 0;               // Clear PWM timer semaphore

            nextevent = pwm_event[i];      // Set up to end this pulse in interrupt vector
            
            OCR1AH = pwm_time[i] >> 8;     // Set pulse width in timer 1
            OCR1AL = pwm_time[i] & 0xff;

            TIFR1 = 0x02;             // Clear interrupts
            TIMSK1 = 0x02;            // Enable A match interrupt

            TCCR1B = 0x01;   // Enable counter 1, Prescaler 1, 62.5ns resolution

            PORTB = nextevent;     // Set the PWM bit
            
            //  All PWM waveform stuff is supposed to happen before the first
            //    period interrupt
            //
            //  Waiting for the PWM to finish idle loop
            //       Going to do more here as this develops
            //
            while (int_pwmserv == 0) {
                led_check ();       // Check LED PWM
                dmx_read ();            // Look at the incoming DMX stream
            }

            if (pwm_eptr == 4)

            int_pwmserv = 0;        // Clear PWM timer semaphore

            //
            // Wait out the rest of 3ms semi-period
            //
            TIMSK1 = 0x00;         // Disable A match interrupt, timer 1

            t1max = 48000;
            OCR1AH = t1max >> 8;          // Set 3ms semi-period in timer 1
            OCR1AL = t1max & 0xff;

            TIMSK1 = 0x02;         // Enable A match interrupt

            while (int_pwmserv == 0) {
                led_check ();       // Check LED PWM
                dmx_read ();            // Look at the incoming DMX stream
            }


        }

        while (int_ppserv == 0) {       // Wait out the rest of 10ms
            led_check ();       // Check LED PWM
            dmx_read ();            // Look at the incoming DMX stream
        }

        int_ppserv = 0;
        
        // COMMENT ME OUT AFTER DEBUG - MAYBE
        //PORTC = PORTC & ~(1 << 4);       // Untrigger scope

    }

    return(0);
}

