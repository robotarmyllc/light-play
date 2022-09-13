/**
 * Delta PCB Functions.
 *
 */

#include "DeltaBrain.h"

DeltaBrain::DeltaBrain() {
}

void DeltaBrain::begin() {
  pinMode(8, OUTPUT);  // Servo enable switch.  

  pinMode(A0, INPUT);
  pinMode(A1, INPUT);
  pinMode(A2, INPUT);
  pinMode(A3, INPUT);

  digitalWrite(A0, HIGH);  // set pullup on analog pin 0 
  digitalWrite(A1, HIGH);  // set pullup on analog pin 1 
  digitalWrite(A2, HIGH);  // set pullup on analog pin 2 
  digitalWrite(A3, HIGH);  // set pullup on analog pin 3  
}

int DeltaBrain::getAddress() {
  int addr = 0;
  
  addr =  !digitalRead(A0);
  addr += !digitalRead(A1)<<1;
  addr += !digitalRead(A2)<<2;
  addr += !digitalRead(A3)<<3;

  return addr;
}

void DeltaBrain::enableMotors() {
  digitalWrite(8, HIGH);
  delay(100);
}

void DeltaBrain::disableMotors() {
  digitalWrite(8, LOW);
}

void DeltaBrain::setLED( int r, int g, int b ) {
  analogWrite(led_r, r);
  analogWrite(led_g, g);
  analogWrite(led_b, b);
}
