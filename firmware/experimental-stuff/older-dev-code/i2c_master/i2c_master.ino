/* Delta Army Master

*/
#include <Wire.h>
#include <Serial.h>

char serialBuf[10];
byte i2cBuf[6];

void setup() {
  Wire.begin(); // join i2c bus (address optional for master)
  Serial.begin(57600);
}

void loop() {
//  servoDoUpDown(1);
//  delay(1000);
//  servoDoCircle(1);
//  delay(1000);
}

void serialEvent() {
 
  // Serial: We are looking only for 10 byte packets that end in '\n'
  // Serial Packet format:
  //    <'D'><'D'><CHANNEL><X><Y><Z><R><G><B><'\n'>
  //
  // I2C Packet Format:
  //    <X><Y><Z><R><G><B>
  if ( Serial.readBytesUntil(0x0A, serialBuf, 10) > 8 ) {    
    if ( serialBuf[0]==0x44 && serialBuf[1]==0x44 ) {
   //writePacket(1,0,0,0,255,0,0);
   //delay(200);
   //writePacket(1,0,0,0,255,0,0);
      i2cBuf[0] = serialBuf[3];
      i2cBuf[1] = serialBuf[4];
      i2cBuf[2] = serialBuf[5];
      i2cBuf[3] = serialBuf[6];
      i2cBuf[4] = serialBuf[7];
      i2cBuf[5] = serialBuf[8];
      // Decode channel and pass messge to i2c.
      Wire.beginTransmission(serialBuf[2]); // transmit to device
      Wire.write(i2cBuf, 6);
      Wire.endTransmission();    // stop transmitting
    }
    //writePacket(1,0,0,0,0,255,0);

  }
}

void servoDoUpDown(int channel) {
  writePacket(channel,127,127,0,0,0,0);
  delay(1000);
  for (int i=0; i< 255; i+=5) {   
    writePacket(channel,127,127,i,i,0,0);
    delay(15);
  }
}

void servoDoCircle(int channel) {
  writePacket(channel,127,127,0,0,0,0);
  delay(1000);
  int last=0;
  for (int i=0; i< 255; i+=2) {   
    writePacket(channel,i,127,127,0,i,0);
    delay(15);
    last = i;
  }
  int last2 = 0;
  for (int i=0; i< 255; i+=2) {   
    writePacket(channel,last,i,127,0,i,0);
    delay(15);
    last2=i;
  }
  for (int i=255; i> 0; i-=2) {   
    writePacket(channel,i,last2,127,0,0,i);
    delay(15);
    last = i;
  }
  for (int i=255; i> 0; i-=2) {   
    writePacket(channel,last,i,127,0,i,0);
    delay(15);
  }
}

void writePacket(int channel, int servoA, int servoB, int servoC, int r, int g, int b) {
  byte arr[6];
  arr[0] = servoA;
  arr[1] = servoB;
  arr[2] = servoC;
  arr[3] = r;
  arr[4] = g;
  arr[5] = b;

  Wire.beginTransmission(channel); // transmit to device #4
  Wire.write(arr, 6);
//  Wire.write(servoA);              // sends one byte  
//  Wire.write(servoB);              // sends one byte  
//  Wire.write(servoC);              // sends one byte  
//  Wire.write(r);              // sends one byte  
//  Wire.write(g);              // sends one byte  
//  Wire.write(b);              // sends one byte  
  Wire.endTransmission();    // stop transmitting

  
}
  
