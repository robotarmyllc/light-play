/* --------------------------------------------------------------------------
 * Serial Test
 * ----------------------------------------------------------------------------
 */

import java.util.Map;
import java.util.Iterator;
import processing.serial.*;

Serial myPort;  // Create object from Serial class

int pos = 0;
int ch = 0;
byte buf[] = new byte[10];

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
      buf[0] = 'D';
      buf[1] = 'D';
      buf[2] = (byte)(ch&0xFF);
      buf[3] = (byte)(127&0xFF);
      buf[4] = (byte)(127&0xFF);
      buf[5] = (byte)(pos&0xFF);
      buf[6] = (byte)(pos==0?0xFF:pos&0xFF);
      buf[7] = (byte)(pos&0xFF);
      buf[8] = (byte)(pos&0xFF);
      buf[9] = 0x0A;
      
      myPort.write(buf);
      
      if (pos == 0) delay(1000);
      
  pos+=2;
  //if ( pos > 255 ) pos = 128;
  if ( pos > 255 ) {
    pos = 0;
    ch++;
    if (ch>7) ch=0;
  }
  
  delay(10);
}

int getPos( int ch, int pos ) {
  int val = (ch*25) + pos;
  
  while ( val > 255 ) {
    val -= 255;
  }
  
  return val;
}
