/*  
 * Light Play
 * 
 * http://robot-army.com
 * 
 *  DMX Firmware
 *
 */
 
#define MAINFILE

#include <Servo.h>
#include "BrainBoard.h"
#include "DMXSerial.h"

#define RedDefaultLevel   200
#define GreenDefaultLevel 255
#define BlueDefaultLevel  10

int dipSw = 0;
int count = 0;

void setup() {
  DMXSerial.init(DMXReceiver);

  brain.begin();
  delta.begin();
  servos.begin();
  servos.refresh();
  
  dipSw = brain.getDIP()*6;

  if ( dipSw == 0 ) {
    brain.setLED( 255, 0, 0 );
  } else if ( dipSw > 504 ) {
    brain.setLED( 255, 0, 0 );
  }   else {
    brain.setLED( 0, 255, 0 );
    // set some default position values
    DMXSerial.write(dipSw+3, 127);
    DMXSerial.write(dipSw+4, 127);
    DMXSerial.write(dipSw+5, 127); 
  }
  
}

void loop() {
  if ( dipSw == 0 ) {
    // Calibrate Mode
    servos.updateServos( servos.FLAT_SERVO, servos.FLAT_SERVO, servos.FLAT_SERVO );
    
    if ( count < 100 ) {
      brain.setLED( 255, 0, 0 );
    } else if ( count < 200 ) {
      brain.setLED( 0, 255, 0 );
    } else {
      brain.setLED( 0, 0, 255 );
    }
    count++;
    if ( count > 300 ) {
      count = 0;
    }
  } else if ( dipSw > 504 ) {
    // Invalid Address.  Do nothing
  } else {
    // Calculate how long no data backet was received
    unsigned long lastPacket = DMXSerial.noDataSince();
    
    if (lastPacket < 5000) {
      brain.setLED( DMXSerial.read(dipSw), DMXSerial.read(dipSw+1), DMXSerial.read(dipSw+2) );
      delta.compute( DMXSerial.read(dipSw+3), DMXSerial.read(dipSw+4), DMXSerial.read(dipSw+5)  );
      servos.refresh();
  
    } else {
      // TODO:  Slowly go back to default position and color.
      brain.setLED( RedDefaultLevel, GreenDefaultLevel, BlueDefaultLevel );
    } 
  }
  
} 

