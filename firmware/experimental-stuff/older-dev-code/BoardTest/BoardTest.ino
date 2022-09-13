/*
 *  Robot Army  Rev. E Board Test
 *
 *
 */
#define MAINFILE
#include "BoardTest.h"
#include <Serial.h>

int dipSw = 0;
const static int BPS = 57600;
char serialBuf[11];
char message2[] = "DELTA-LOVE\r\n";
String message = "DELTA-LOVE\r\n";

void setup() {
  Serial.begin(57600);
  brain.begin();
  dipSw = brain.getDIP();    
}


void loop() {
  brain.setLED(0,255,255);
  delay(1000);
  brain.setLED(255,0,255);
  delay(1000);
  brain.setLED(255,255,0);
  delay(1000);
  brain.setLED(0,0,0);
  brain.flashD13(dipSw);
  delay(1000);
  brain.setLED(0,0,0);
  
  digitalWrite(DeltaBrain::OBLED, LOW);  // Clear OB led
  
  Serial.print(message2);
  
  //Serial.flush();
  
  delay(2000);
}

void serialEvent() {
   digitalWrite(DeltaBrain::OBLED, HIGH);
   delay(100);   
   digitalWrite(DeltaBrain::OBLED, LOW);
   
   if(Serial.readBytesUntil('\0', serialBuf, 10)) {
     if ( serialBuf[0]=='D' 
       && serialBuf[1]=='E' 
       && serialBuf[2]=='L' 
       && serialBuf[3]=='T' 
       && serialBuf[4]=='A' 
       && serialBuf[5]=='-' 
//       && serialBuf[6]=='L' 
//       && serialBuf[7]=='O' 
//       && serialBuf[8]=='V' ¬´√•√Ö
//       && serialBuf[9]=='E' 
     ) {
         digitalWrite(DeltaBrain::OBLED, HIGH);         
     }
       delay(1000);
       digitalWrite(DeltaBrain::OBLED, LOW);         
  }
  
}


