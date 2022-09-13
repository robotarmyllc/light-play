/*  
 * Maker Faire Demo
 * 
 * http://robot-army.com
 * 
 */
#define MAINFILE

#include <Wire.h>
#include <Serial.h>
#include <Servo.h>
#include "starter_kit.h"


int dipSw = 0;

void setup() {

  brain.begin();
  //brain.enableMotors();

  delta.begin();
  servos.begin();
  
  servos.refresh();

  dipSw = brain.getDIP();    
  switch ( dipSw ) {
    case 0:  // Calibration Mode
      delay(500);
      brain.flashLED(3, 255, 0,   0   );  // Red
      delay(500);
      brain.flashLED(3, 0,   255, 0   );  // Blue
      delay(500);
      brain.flashLED(3, 0,   0,   255 );  // Green
      break;
    case 1: 
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:  
    case 7:      
    case 8:
    case 9:
    case 10:
    case 11:
    case 12:
    case 13: // Mode Serial Direct (No address).
      //brain.flashLED(6, 0, 0, 255);
      Serial.begin(57600);
      modeSerialXYZ.begin();
      servos.updateServos(
        DeltaServos::FLAT_SERVO,
        DeltaServos::FLAT_SERVO,
        DeltaServos::FLAT_SERVO  );  // Calibration :: arms horizontal with table
      break;
    case 14:   // Mode Serial Direct
      brain.flashLED(7, 0, 0, 255);
      Serial.begin(57600);
      modeSerialDirect.begin();
      break;
    case 15:  // Chicken Mode
      break;
  } 
}

void loop() {
  switch ( dipSw ) {
    case 0:  // Calibrate
      servos.updateServos(
        DeltaServos::FLAT_SERVO,
        DeltaServos::FLAT_SERVO,
        DeltaServos::FLAT_SERVO  );  // Calibration :: arms horizontal with table
      break;
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:  // Mode Fast-as-can-be
    case 6:  // Delta Serial  : Handled by serialEvent()
    case 7:  // Direct Serial : Handled by serialEvent()
    case 8:
    case 9:
    case 10:
    case 11:
    case 12:
    case 13:
    case 14: // Serial
      break;
    case 15:   // Chicken
      modeChicken.update();     
      break;   
  }
} 

void serialEvent() {
  switch(dipSw) {
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    case 9:
    case 10:
    case 11:
    case 12:
    case 13:
      modeSerialXYZ.serialEvent(dipSw);
      break;
    case 14:  // Use for debug
      modeSerialDirect.serialEvent();
      break;
  }
}
