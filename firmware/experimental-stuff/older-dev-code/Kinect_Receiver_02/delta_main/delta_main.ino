/* Delta Robot Main Code 
Authors: Angelo Del Mundo, Andy Hoac, Jennifer Lew, Azhar Meyer, Jamie Young
*/
// Units in inches
float a = 8; // length of hip, flat piece
float b = 14; // length of ankle, tube
float r0 = 5; // radius of base
float R0 = 2; // radius of effector

float incrementx = 0.10;
float incrementy = 0.10;
float incrementz = 0.10;

float xg = 0; //-incrementx; // position of effector
float yg = 0; //-incrementy;
float zg = 0;
float theta[]={0, 120*3.14159/180, -120*3.14159/180};

float r[3][3]={{0,0,0},{0,0,0}, {0, 0, 0}};
float R[3][3]={{0,0,0},{0,0,0}, {0, 0, 0}};

float phi_prev[3] = {0,0,0};
float phiA[3];
float phi[3]= {90,90,90};
float diff[3];

// Nunchuck 
#include <Wire.h>
//#include "nunchuck_funcs.h"
//#include <WProgram.h>
int loop_cnt=0;
byte accx,accy,accz,zbut,cbut,joyx,joyy;
int ledPin = 7;

byte testpos=0;
int j = 0;

// Servos
#include <Servo.h> 
Servo servoA, servoB, servoC;
// LED Pins
int ledR = 3;
int ledG = 5;
int ledB = 6;

char buffer[10];


byte latchstate = 0;

void setup() 
{
  Serial.begin(57600);
  
  //Wiisetup();
  Servosetup();
  initdelta();
}

void loop()
{
   //Hold();
   //return;
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
       int x = (buffer[3]&0xff)-128;
       int y = (buffer[4]&0xff)-128;
       int z = (buffer[5]&0xff)-128;

       xg = (float)x*0.03;
       yg = (float)y*0.03;
       zg = (float)z*0.03;
       
       zg += 15.0;
       
//       servoA.write(servoVal(buffer[3]));
//       servoB.write(servoVal(buffer[4]));
//       servoC.write(servoVal(buffer[5]));
//       
//       delay(18);
       
       doLED( buffer[3]&0xff, buffer[4]&0xff, buffer[5]&0xff );
     }
   }
 
  InvKinA();
        Serial.print("\txg: "); Serial.print(xg);
        Serial.print("\tyg: "); Serial.print(yg);
        Serial.print("\tzg: "); Serial.println(zg);
//        Serial.print("\tangle1: "); Serial.print(phi[0]);
//        Serial.print("\tangle2: "); Serial.print(phi[1]);
//        Serial.print("\tangle3: "); Serial.println(phi[2]);
  Servoloop(); 
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

