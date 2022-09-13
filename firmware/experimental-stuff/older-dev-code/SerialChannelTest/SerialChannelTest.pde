/* 
 * Robot Army Start Kit -- http://robot-army.com
 * Serial Channel Test
 *
 *
 *  This is some simple code to test the serial connection between
 *  the delta robot and your computer.  You can use this code as a 
 *  starting point for your own code.

=======================================================================================
Copyright (c) 2014, Robot Army LLC     All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, 
   this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, 
   this list of conditions and the following disclaimer in the documentation 
   and/or other materials provided with the distribution.

THIS  SOFTWARE IS PROVIDED  BY THE  COPYRIGHT  HOLDERS  AND CONTRIBUTORS  "AS IS"  AND
ANY  EXPRESS  OR  IMPLIED WARRANTIES,  INCLUDING,  BUT  NOT  LIMITED  TO,  THE IMPLIED 
WARRANTIES  OF MERCHANTABILITY  AND FITNESS FOR A  PARTICULAR PURPOSE  ARE DISCLAIMED. 
IN  NO  EVENT SHALL THE  COPYRIGHT HOLDER OR  CONTRIBUTORS  BE LIABLE  FOR ANY DIRECT, 
INDIRECT, INCIDENTAL,  SPECIAL,  EXEMPLARY,  OR CONSEQUENTIAL DAMAGES  (INCLUDING, BUT
NOT LIMITED TO,  PROCUREMENT OF SUBSTITUTE  GOODS OR SERVICES;  LOSS OF USE,  DATA, OR
PROFITS;  OR BUSINESS  INTERRUPTION)  HOWEVER CAUSED AND ON  ANY THEORY OF  LIABILITY, 
WHETHER IN CONTRACT,  STRICT LIABILITY,  OR  TORT  (INCLUDING NEGLIGENCE OR OTHERWISE) 
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY 
OF SUCH DAMAGE. 
=======================================================================================
*/

import java.util.Map;
import java.util.Iterator;
import processing.serial.*;

Serial myPort;  // Create object from Serial class

int pos = 0;

void setup() {  
  //println(Serial.list());
  String portName = "";
  String ports[] = Serial.list();
  for ( String p : ports) {
    // This code is for Mac.  It will find a FTDI serial adapter if you have
    // one plugged in.
    if ( p.startsWith("/dev/tty.usbserial") ) {
      portName = p;
      println("Serial:  " + p);
    }
    
    // Windows.  hardcoded to COM4 change it to whatever yours comes up as.
    if ( p.equals("COM4") ) {
      portName = p;
      println("Serial:  " + p);
    }
  }

  if ( portName != "" ) {
    myPort = new Serial(this, portName, 57600);
  } else {
    println("No serial port found!!");
    exit();
  }
}

void draw() {
    byte buf[] = new byte[10];
    
    
    for ( int ch = 0; ch<8; ch++) {
      buf[0] = 'D';
      buf[1] = 'D';
      buf[2] = (byte)(ch&0xFF);  // Channel
      buf[3] = (byte)(127&0xFF); // X direction
      buf[4] = (byte)(127&0xFF); // Y direction
      buf[5] = (byte)(getPos(ch, pos)&0xFF);  // Z direction
      buf[6] = (byte)(0&0xFF);
      buf[7] = (byte)(0&0xFF);
      buf[8] = (byte)(pos&0xFF);
      buf[9] = 0x0A;
      
      myPort.write(buf);
    }
      
  pos+=3;
  //if ( pos > 255 ) pos = 128;
  if ( pos > 255 ) {
    pos = 0;
    delay(300);
  }
  
  delay(100);
}

int getPos( int ch, int pos ) {
  int val = (ch*25) + pos;
  
  while ( val > 255 ) {
    val -= 255;
  }
  
  return val;
}
