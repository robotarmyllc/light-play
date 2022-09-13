#include <WProgram.h>
#include <stdint.h>
#include <avr/io.h>
#include <avr/interrupt.h>

#define byte uint8_t
#define N_SERVOS 3

#define MAX_PULSE 32000
#define MIN_PULSE 16000


unsigned int pulseW[N_SERVOS];

volatile byte event_running;
volatile byte event[N_SERVOS*2];
volatile int ev_time[N_SERVOS*2];
unsigned int eventp;
unsigned int nevents;

/**
 * Interrupt routine for timer 1 compare A. 
 * Used for timing each pulse width for the servo PWM.
 */
ISR(TIMER1_COMPA_vect) {
  // digitalWrite(9, LOW);    // PWM pulse LOW
  PORTB = PINB & ~event[eventp]; 
  eventp++;
  if (eventp >= nevents)
    event_running = 0;
}

void setup() {
    pinMode(8, OUTPUT);  // Servo enable switch. 
    digitalWrite(8, HIGH);
    //delay(200);    // Wait a little.
    

  // put your setup code here, to run once:
/*
  Serial.begin (9600);
  
  Serial.print ("Hello World\n");
  
  Serial.print ("EICRA = 0x");
  Serial.println (EICRA, HEX);
  Serial.print ("EIMSK = 0x");
  Serial.println (EIMSK, HEX);
  Serial.print ("EIFR = 0x");
  Serial.println (EIFR, HEX);
  Serial.print ("PCICR = 0x");
  Serial.println (PCICR, HEX);
  Serial.print ("PCIFR = 0x");
  Serial.println (PCIFR, HEX);
  Serial.print ("PCMSK2 = 0x");
  Serial.println (PCMSK2, HEX);
  Serial.print ("PCMSK1 = 0x");
  Serial.println (PCMSK1, HEX);
  Serial.print ("PCMSK0 = 0x");
  Serial.println (PCMSK0, HEX);
  Serial.println ("");

  Serial.print ("TCCR0A = 0x");
  Serial.println (TCCR0A, HEX);
  Serial.print ("TCCR0B = 0x");
  Serial.println (TCCR0B, HEX);
  Serial.print ("TIFR0 = 0x");
  Serial.println (TIFR0, HEX);
  Serial.print ("TIMSK0 = 0x");
  Serial.println (TIMSK0, HEX);
  Serial.print ("TCCR1A = 0x");
  Serial.println (TCCR1A, HEX);
  Serial.print ("TCCR1B = 0x");
  Serial.println (TCCR1B, HEX);
  Serial.print ("TIFR1 = 0x");
  Serial.println (TIFR1, HEX);
  Serial.print ("TIMSK1 = 0x");
  Serial.println (TIMSK1, HEX);
  Serial.print ("TCCR2A = 0x");
  Serial.println (TCCR2A, HEX);
  Serial.print ("TCCR2B = 0x");
  Serial.println (TCCR2B, HEX);
  Serial.print ("TIFR2 = 0x");
  Serial.println (TIFR2, HEX);
  Serial.print ("TIMSK2 = 0x");
  Serial.println (TIMSK2, HEX);
  Serial.println ("");

  Serial.print ("GTCCR = 0x");
  Serial.println (GTCCR, HEX);
  Serial.print ("SREG = 0x");
  Serial.println (SREG, HEX);
  Serial.println ("");

  Serial.print ("UCSR0A = 0x");
  Serial.println (UCSR0A, HEX);
  Serial.print ("UCSR0B = 0x");
  Serial.println (UCSR0B, HEX);
  Serial.print ("UCSR0C = 0x");
  Serial.println (UCSR0C, HEX);
  // Serial.print ("0x00 = 0x");
  // Serial.println (0x00, HEX);
  // Serial.print ("0x00 = 0x");
  // Serial.println (0x00, HEX);
  // Serial.print ("0x00 = 0x");
  // Serial.println (0x00, HEX);
  // Serial.print ("0x00 = 0x");
  // Serial.println (0x00, HEX);
*/
TIMSK0 = 0;

  //pulseW[0] = MAX_PULSE;
  pulseW[0] = MIN_PULSE;
  pulseW[1] = MIN_PULSE;
  pulseW[2] = MIN_PULSE;

  event_running = 0;
  eventp = 0;
  nevents = 0;

    // Timer 1 setup(16 bit):
  TCCR1A = 0;                     // Normal counting mode 
  TCCR1B = 1;                     // Set prescaler to 1 
  TCNT1 = 0;                      // Clear timer count 
  TIFR1 = 255;                    // Clear  pending interrupts
  // TIMSK1 = 6;                     // Enable the output compare A and B interrupt 
  TIMSK1 = 2;                     // Enable the output compare A interrupt 

    // Timer 2 setup(8 bit):
  TCCR2A = 0;                     // Normal counting mode 
  TCCR2B = 6;                     // Set prescaler to 256
  TCNT2 = 0;                      // Clear timer count 
  TIFR2 = 255;                    // Clear pending interrupts
  // TIMSK2 = 6;                     // Enable the output compare A and B interrupt 
  // OCR2A = 93;                     // Set counter A for about 500us before counter B below;
  // OCR2B = 124;                    // Set counter B for about 2000us (20ms/10, where 20ms is 50Hz);

  pinMode(9,  OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
  unsigned long i, j;

  TCNT1 = 0;                      // Clear timer count 

  /* Schedule events */
  eventp = 0;
  event[0] = 2;
  //event[2] = 3;
  //event[4] = 4;
  ev_time[0] = pulseW[0];
  //ev_time[2] = pulseW[1];
  //ev_time[4] = pulseW[2];
  
  nevents = 1;

  /* Initialize PWM cycle */
  event_running = 1;
  OCR1A = ev_time[eventp];
  TCNT1 = 0;                      // Clear fast timer count 
  TCNT2 = 0;                      // Clear 20ms timer count 
  digitalWrite(9, HIGH);   // Pulse HIGH

        digitalWrite(10, HIGH);   // Pulse HIGH
        digitalWrite(11, HIGH);   // Pulse HIGH

  // delay(1);               // wait for a second
  while (1) {
     // if (TCNT1 > 48000) {
        // Serial.print ("Timeout\n");
        // break;
     // }
     if (event_running == 0) break;
  }
  // digitalWrite(9, LOW);    // turn the LED off by making the voltage LOW

  // delay(18);     // wait for the rest of the PWM cycle
  for (i = 0; i < 10; i++) {
	  while (1) {
	     if (TCNT2 >= 124) break;
	  }
          TCNT2 = 0;
  }

  pulseW[0] += 40;
  pulseW[1] += 40;
  pulseW[2] += 40;
  if (pulseW[0] > MAX_PULSE) pulseW[0] = MIN_PULSE;
  if (pulseW[1] > MAX_PULSE) pulseW[1] = MIN_PULSE;
  if (pulseW[2] > MAX_PULSE) pulseW[2] = MIN_PULSE;
}

