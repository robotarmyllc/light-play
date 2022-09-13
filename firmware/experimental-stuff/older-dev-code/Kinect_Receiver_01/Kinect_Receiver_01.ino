/* Light Play  -- Kinect Receiver Demo  
 *
 */
#include <Servo.h>

char buffer[10];

Servo servoA, servoB, servoC;

// LED Pins
int ledR = 3;
int ledG = 5;
int ledB = 6;

int minPulse = 700;
int maxPulse = 2000;

int servoMin = 100;
int servoMax = 170;

void setup() {
//  servoA.setMinimumPulse(minPulse);
//  servoA.setMaximumPulse(maxPulse);
//  servoB.setMinimumPulse(minPulse);
//  servoB.setMaximumPulse(maxPulse);
//  servoC.setMinimumPulse(minPulse);
//  servoC.setMaximumPulse(maxPulse);

  servoA.attach(9);   // attaches the servo on pin 9
  servoB.attach(10);  // attaches the servo on pin 10
  servoC.attach(11);  // attaches the servo on pin 11
  
  Serial.begin(57600);
}

void loop() {
   /*  check if data has been sent from the computer: */
   
   /*    Serial Packet:  DDD<byte><byte><byte><byte><byte><byte><LF>
   */
   if ( Serial.readBytesUntil(0x0A, buffer, 10) > 5 ) {
//       byte buf[10];
//       
//       buf[0] = buffer[0];
//       buf[1] = buffer[1];
//       buf[2] = buffer[2];
//       buf[3] = buffer[3];
//       buf[4] = buffer[4];
//       buf[5] = buffer[5];
//       buf[6] = buffer[6];
//       buf[7] = buffer[7];
//       buf[8] = buffer[8];
//       buf[9] = buffer[9];
//       
//       Serial.write(buf, 10);


     //Serial.write("Yay!\n");
     if ( isD(buffer[0]) && isD(buffer[1]) && isD(buffer[2]) ) {
       // Yay!  We have a good packet!
       
       servoA.write(servoVal(buffer[3]));
       servoB.write(servoVal(buffer[4]));
       servoC.write(servoVal(buffer[5]));
       
       delay(18);
       
       doLED( buffer[3], buffer[4], buffer[5] );
     }
   }
}
  
boolean isD(byte b) {
    if ( b == 0x44 ) {
      return true;
    } else {
      return false;
    }
}

void doLED( byte r, byte g, byte b ) {
  analogWrite(ledR, r);
  analogWrite(ledG, g);
  analogWrite(ledB, b);
}

int servoVal(int rawVal ) {
   if ( rawVal<servoMin ) {
    return servoMin;
   }
  if (rawVal>servoMax) {
    return servoMax;
  }
  
  return rawVal;
}

