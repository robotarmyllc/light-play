/*  
  I2C Slave
 */
#include <Wire.h>
#include <Serial.h>
#include <Servo.h>
#include "DeltaBrain.h"
#include "DeltaServos.h"
#include "DeltaKinetics.h"

DeltaBrain    brain;
DeltaKinetics delta;
DeltaServos   servos;
 
void setup() {
  delta.begin();
  brain.begin();
  brain.enableMotors();
  servos.begin();
  servos.initArms();    // make arms go fully up and fully down.
  
  Wire.begin(brain.getAddress()); // join i2c bus with addr
  //Wire.begin(5);                // join i2c bus with addr
  
  Wire.onReceive(receiveEvent);   // register event
}

void loop() {   
  //delay(100);    // Nothing to do here.
} 

// function that executes whenever data is received from master
// this function is registered as an event, see setup()
void receiveEvent(int howMany) {
  if ( howMany < 6 ) return;  // Packet size is six!

//  float x = (float)Wire.read()*0.035;
//  float y = (float)Wire.read()*0.035;
//  float z = (float)Wire.read()*0.035;
  float x = (float)Wire.read();
  float y = (float)Wire.read();
  float z = (float)Wire.read();
  
  //z -= 2;
 
  brain.setLED(Wire.read(), Wire.read(), Wire.read());  
  delta.compute( x, y, z );
  servos.updateServos( delta.get() );
}


