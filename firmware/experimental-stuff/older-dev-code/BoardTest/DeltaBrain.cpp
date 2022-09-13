/**
 * Delta PCB Functions.
 *
 */

#define REV_D
// FLIP_EN is for Rev.D and above boards.
#define FLIP_EN 



#include "DeltaBrain.h"
#include "Arduino.h"

DeltaBrain::DeltaBrain() {}

void DeltaBrain::begin() {
  pinMode(MOTOR_EN, OUTPUT);  // Servo enable switch.
  pinMode(OBLED,    OUTPUT);  // On Boad LED.
  
#ifdef FLIP_EN
  digitalWrite(MOTOR_EN, LOW); // Enable the third motor to run. (Rev. D and above boards)
#else
  digitalWrite(MOTOR_EN, HIGH); // Enable the motors to run (Rev. B/C boards.)
#endif

  pinMode(A0, INPUT);
  pinMode(A1, INPUT);
  pinMode(A2, INPUT);
  pinMode(A3, INPUT);

  digitalWrite(A0, HIGH);  // set pullup on analog pin 0 
  digitalWrite(A1, HIGH);  // set pullup on analog pin 1 
  digitalWrite(A2, HIGH);  // set pullup on analog pin 2 
  digitalWrite(A3, HIGH);  // set pullup on analog pin 3  
}

int DeltaBrain::getDIP() {
  int dip = 0;
  
  dip =  !digitalRead(A0);
  dip += !digitalRead(A1)<<1;
  dip += !digitalRead(A2)<<2;
  dip += !digitalRead(A3)<<3;

  return dip;
}

//void DeltaBrain::enableMotors() {
//  digitalWrite(MOTOR_EN, HIGH);
//  delay(200);    // Wait a little.
//}

void DeltaBrain::disableMotors() {
  digitalWrite(MOTOR_EN, LOW);
}

void DeltaBrain::setLED( int r, int g, int b ) {
  analogWrite(LED_R, r);
  analogWrite(LED_G, g);
  analogWrite(LED_B, b);
}

void DeltaBrain::flashLED( int count, int r, int g, int b ) {
  for ( int i=0; i < count; i++) {
    setLED(r,g,b);
    delay(300);
    setLED(0,0,0);
    delay(300);
  }
}

void DeltaBrain::flashD13( int count ) {
  for ( int i=0; i < count; i++) {
    digitalWrite(OBLED, HIGH);
    delay(300);
    digitalWrite(OBLED, LOW);
    delay(300);
  }
}


