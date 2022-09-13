/**
 * Delta PCB Functions.
 *
 */

#define REV_E

#include "DeltaBrain.h"
#include "Arduino.h"
#include <Servo.h>

DeltaBrain::DeltaBrain() {}

void DeltaBrain::begin() {
  // The third servo signal is shared with the ISCP programming MOSI line.
  // If we didn't disable the servo signal here, then the servo would wildly
  // twitch whenever we programmed the ATmega chip using the ISCP header.
  pinMode(MOTOR_EN, OUTPUT);  // Servo enable switch.
  
  pinMode(LED_ONBOARD, OUTPUT);    // LED on board.
  
  digitalWrite(MOTOR_EN, LOW); // Enable the third motor to run. (Rev. D and above boards)

  pinMode(A0, INPUT);
  pinMode(A1, INPUT);
  pinMode(A2, INPUT);
  pinMode(A3, INPUT);
  
  pinMode(7, INPUT);
  pinMode(4, INPUT);
  pinMode(2, INPUT);
  //pinMode(A5, INPUT);

  digitalWrite(A0, HIGH);  // set pullup on analog pin 0 
  digitalWrite(A1, HIGH);  // set pullup on analog pin 1 
  digitalWrite(A2, HIGH);  // set pullup on analog pin 2 
  digitalWrite(A3, HIGH);  // set pullup on analog pin 3  
  
  digitalWrite(7, HIGH);  // set pullup on analog pin 4 
  digitalWrite(4, HIGH);  // set pullup on analog pin 5 
  digitalWrite(2, HIGH);  // set pullup on analog pin 6 
  //digitalWrite(A5, HIGH);  // set pullup on analog pin 3  

}

int DeltaBrain::getDIP() {
  int dip = 0;
  
  dip =  !digitalRead(A0);
  dip += !digitalRead(A1)<<1;
  dip += !digitalRead(A2)<<2;
  dip += !digitalRead(A3)<<3;

  dip += !digitalRead(7)<<4;
  dip += !digitalRead(4)<<5;
  dip += !digitalRead(2)<<6;
  //dip += !digitalRead(A5)<<7;
  
  return dip;
}

void DeltaBrain::flashOBLED(){
  digitalWrite(LED_ONBOARD, HIGH);
  delay(100);
  digitalWrite(LED_ONBOARD, LOW);
}


/**
  Set intensity of each RGB LED channel with values of 0 (off) to 255(full bright).
 */
void DeltaBrain::setLED( int r, int g, int b ) {
  // Range limiting;
  if ( r > 255 ) r = 255;
  if ( g > 255 ) g = 255;
  if ( b > 255 ) b = 255;
  if ( r < 0 ) r = 0;
  if ( g < 0 ) g = 0;
  if ( b < 0 ) b = 0;

#ifdef REV_E
  // Rev. E and above, we inverted the LED on values:
  //    So writing 0 is full on and writing 255 is off.
  r = 255 - r;
  g = 255 - g;
  b = 255 - b;  
  analogWrite(LED_R, r);
  analogWrite(LED_G, g);
  analogWrite(LED_B, b);
#else
  analogWrite(LED_R, r);
  analogWrite(LED_G, b);   // B and G swapped on Rev. D.
  analogWrite(LED_B, g);  
#endif

}

void DeltaBrain::flashLED( int count, int r, int g, int b ) {
  flashLED( count, r, g, b, 200, 200 );
}
  
void DeltaBrain::flashLED( int count, int r, int g, int b, int onTime, int offTime ) {
  for ( int i=0; i < count; i++) {
    setLED(r,g,b);
    delay(onTime);
    setLED(0,0,0);
    delay(offTime);
  }
}
