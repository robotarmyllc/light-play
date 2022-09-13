/* --------------------------------------------------------------------------
 * Serial Test
 * ----------------------------------------------------------------------------
 */

import java.util.Map;
import java.util.Iterator;
import processing.serial.*;

Serial myPort;  // Create object from Serial class

int pos = 0;
int pos2 = 127;
int ch = 0;
int ch2 = 4;
 
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
      buf[6] = (byte)(pos>100?0xFF:pos&0xFF);
      buf[7] = (byte)(0x90);
      buf[8] = (byte)(pos&0xFF);
      buf[9] = 0x0A;
      
      myPort.write(buf);
      
      buf[0] = 'D';
      buf[1] = 'D';
      buf[2] = (byte)(ch2&0xFF);
      buf[3] = (byte)(127&0xFF);
      buf[4] = (byte)(127&0xFF);
      buf[5] = (byte)(pos2&0xFF);
      buf[6] = (byte)(pos2>100?0xFF:pos2&0xFF);
      buf[7] = (byte)(pos2&0xFF);
      buf[8] = (byte)(0x6F);
      buf[9] = 0x0A;
      
      myPort.write(buf);
      
      //if (pos0 == 0) delay(1000);
      //delay(10);
      
  pos+=2;
  //if ( pos > 255 ) pos = 128;
  if ( pos > 255 ) {
    pos = 0;
    ch++;
    if (ch>3) ch=0;
  }

  pos2+=3;
  if ( pos2 > 255 ) {
    pos2 = 0;
    ch2++;
    if (ch2>7) ch2=4;
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
