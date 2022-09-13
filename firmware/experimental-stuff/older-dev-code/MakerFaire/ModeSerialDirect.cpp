/**
 * ModeSerialDirect 
 *   - Mode that responds to Serial commands and programs motors directly
 *
 */
#include "ModeSerialDirect.h"
#include "starter_kit.h"
#include "Serial.h"

ModeSerialDirect::ModeSerialDirect() {}


void ModeSerialDirect::begin() {
  //Serial.begin(BPS);
}

void ModeSerialDirect::serialEvent() {
 
  // Serial: We are looking only for 10 byte packets that end in '\n'
  // Serial Packet format:
  //    <'D'><'D'><CHANNEL><X><Y><Z><R><G><B><'\n'>
  //
  if ( Serial.readBytesUntil(0x0A, serialBuf, 10) > 8 ) {
//        brain.setLED((byte)0,(byte)255,(byte)0);
//        delay(50);
//        brain.setLED((byte)0,(byte)0,(byte)0);
  
    if ( serialBuf[0]==0x44 && serialBuf[1]==0x44 ) {
      servos.updateServos( (byte)serialBuf[3], (byte)serialBuf[4], (byte)serialBuf[5] );      
      brain.setLED((byte)serialBuf[6],(byte)serialBuf[7],(byte)serialBuf[8]);
    }
  }
}

