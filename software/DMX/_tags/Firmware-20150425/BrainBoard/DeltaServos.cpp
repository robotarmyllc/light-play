/**
 * DeltaServos - Class to handle the three Delta Servos
 *
 */
#include "BrainBoard.h"
#include "DeltaServos.h"
#include "Arduino.h"

EXTERN  DeltaKinetics delta;
 
DeltaServos::DeltaServos() {
  led_r_val = 0;
  led_g_val = 0;
  led_b_val = 0;
  
  int servo_dir = -1;
  int servo_delay = 15;
}

DeltaServos::~DeltaServos() { }

Servo DeltaServos::get( int servo ) {
  switch ( servo ) {
    case 1: return servoA;
    case 2: return servoB;
    case 3: return servoC;
    default: return servoA;
  }
}

void DeltaServos::begin() { // TODO: Pass the three pins here?
  servoA.attach(9); // attaches servo A to pin 9  
  servoB.attach(10); // attaches servo B to pin 10 
  servoC.attach(11); // attaches servo C to pin 11
}

void DeltaServos::updateServos( float sa, float sb, float sc ) {
  updateServos( (int)sa, (int)sb, (int)sc );
}

void DeltaServos::updateServos( float * abc ) {
  updateServos(abc[0], abc[1], abc[2]);
}

void DeltaServos::updateServos( int sa, int sb, int sc ) {
  delta.phi[0] = map(sa,0,255, MIN_SERVO, MAX_SERVO);
  delta.phi[1] = map(sb,0,255, MIN_SERVO, MAX_SERVO);
  delta.phi[2] = map(sc,0,255, MIN_SERVO, MAX_SERVO);
  
  refresh();
}

void DeltaServos::refresh() {
  servoA.write(delta.phi[0]);   // tell servo to go to positions in variable 'phi' 
  servoB.write(delta.phi[1]);
  servoC.write(delta.phi[2]);
  delay(servoDelay); 
}

void DeltaServos::upDown( int cycleTime ) {
  //int STEPS = 32;
  int STEPS = 255;
  int INCR = 255/STEPS;
  int dly = (cycleTime/STEPS/2);
  if (dly < servoDelay) dly = servoDelay;
  
  int i = 0;
  
  while ( i < 255 ) {
    brain.setLED( i,i,i );
    updateServos( i,i,i );
    delay(dly-servoDelay);
    i+= INCR;
  }
  delay(500);
  i = 255;
  while ( i > 0 ) {
    brain.setLED( i,i,i );
    updateServos( i,i,i );
    delay(dly-servoDelay);
    i-= INCR;
  }
  delay(500);
  
}
