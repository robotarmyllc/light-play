/**
 *  Local Servo Variables
 *
 */
#ifndef CUDDLE_SERVO_H
#define CUDDLE_SERVO_H

#include <WProgram.h>
#include <stdint.h>
#include <avr/io.h>

#define byte uint8_t
#define N_SERVOS 3


class CuddleServo {
  public:
    CuddleServo();
    void begin();
    void move(int chan, long pulse, long spd, long t); 
    void groupMove(int ch, long pulse, long spd, long t);
    void groupMoveActivate();
    void realTime50Hz();
    void isrTimer1CompAvect();
    void isrTimer1CompBvect();
    void isrTimer2CompAvect();
    void isrTimer2CompBvect();
    

    // Most other servos
//    const static int MIN_SERVO = 145; // Down Position
//    const static int MAX_SERVO = 85;  // Up Position

    // Power HD 3001HB
    const static int MIN_SERVO = 50; // Down Position
    const static int MAX_SERVO = 145;  // Up Position
    const static int MOTOR_EN = 8;
   
    unsigned int pulseW[N_SERVOS];
    
  private:
    long checkRange(long val);

    long stepsHD[N_SERVOS]; //= {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    long lastPos[N_SERVOS]; //= {24000,24000,24000,24000,24000,24000,24000,24000,24000,24000,24000,24000,24000,24000,24000,24000,24000,24000,24000,24000};
    long stepsRemaining[N_SERVOS]; //= {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};

    //static int channelCount;
    byte Timer2Toggle;
    volatile uint8_t* OutPort1A; // = &PORTD;
    //static volatile uint8_t *OutPort1A; // = &PORTD;
    volatile uint8_t* OutPort1B; // = &PORTB;
    uint8_t OutBit1A; // = 4;
    uint8_t OutBit1B; // = 16;
    volatile uint8_t* OutPortNext1A; // = &PORTD;
    volatile uint8_t* OutPortNext1B; // = &PORTB;
    uint8_t OutBitNext1A; // = 4;
    uint8_t OutBitNext1B; // = 16;

    long groupStepsRemaining; // = 0;
    long groupLastPos[N_SERVOS];
    long groupStepsHD[N_SERVOS];
    int groupChannel[N_SERVOS];
    int nChannels; // = 0;
          
    volatile uint8_t* outPortTable[N_SERVOS]; // = {&PORTD,&PORTD,&PORTD,&PORTD,&PORTD,&PORTD,&PORTB,&PORTB,&PORTB,&PORTB,&PORTB,&PORTB,&PORTC,&PORTC,&PORTC,&PORTC,&PORTC,&PORTC,&PORTD,&PORTD};
    uint8_t outBitTable[N_SERVOS]; // = {4,8,16,32,64,128,1,2,4,8,16,32,1,2,4,8,16,32,1,2};
    byte servoInvert[N_SERVOS]; // = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
};


static CuddleServo servos;

#endif
