/* --------------------------------------------------------------------------
 * Serial Test
 * ----------------------------------------------------------------------------
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
    if ( p.startsWith("/dev/tty.usbserial") ) {
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
      
    for (int ch=0; ch<8; ch++) {
      buf[0] = 'D';
      buf[1] = 'D';
      buf[2] = (byte)(ch&0xff);
      buf[3] = (byte)(127&0xFF);
      buf[4] = (byte)(127&0xFF);
// Use these for direct mode and comment out the for-loop
//      buf[2] = (byte)(0&0xff);
//      buf[3] = (byte)(pos&0xff);
//      buf[4] = (byte)(pos&0xff);
      buf[5] = (byte)(pos&0xFF);
      buf[6] = (byte)(pos&0xFF);
      buf[7] = (byte)(pos&0xFF);
      buf[8] = (byte)(pos&0xFF);
      buf[9] = 0x0A;
      
      myPort.write(buf);
    }
    
    if ( pos == 0 ) delay(700);
//      // Delta 2
//      buf[2] = 0x02;
//      myPort.write(buf);
//      // Delta 3
//      buf[2] = 0x03;
//      myPort.write(buf);
//      // Delta 4
//      buf[2] = 0x04;
//      myPort.write(buf);
//      // Delta 5
//      buf[2] = 0x05;
//      myPort.write(buf);
//      // Delta 6
//      buf[2] = 0x06;
//      myPort.write(buf);
//      // Delta 7
//      buf[2] = 0x07;
//      myPort.write(buf);
//      // Delta 8
//      buf[2] = 0x08;
//      myPort.write(buf);
//      // Delta 9
//      buf[2] = 0x09;
//      myPort.write(buf);
      
//      while (myPort.available() > 0) {
//        //int inByte = myPort.read();
//        print(myPort.readString());
//        //print(inByte);
//        //print(':');
//        println();
//      }
    
  pos++;
  if ( pos > 255 ) pos = 0;
  
  //delay(0);
}

