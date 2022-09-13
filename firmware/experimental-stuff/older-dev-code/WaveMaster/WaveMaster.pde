/* --------------------------------------------------------------------------
 * Serial Test
 * ----------------------------------------------------------------------------
 */


/*

  Really Jerky!!!     Needs work.  Should be flowing!
  
  
  */


import java.util.Map;
import java.util.Iterator;
import processing.serial.*;

Serial myPort;  // Create object from Serial class

int pos[] = new int[]{ 
        0x0000, 0x002F, 0x004F, 0x006F, 
        0x008F, 0x00AF, 0x00CF, 0x00EF, 
      };
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
    for ( int ch = 0; ch<8; ch++) {  
     double angle = (double)pos[ch] * 1.412; 
      buf[0] = 'D';
      buf[1] = 'D';
      buf[2] = (byte)(ch&0xFF);
      buf[3] = (byte)((int)(Math.sin(angle)*255)&0xFF);
      buf[4] = (byte)((int)(Math.cos(angle)*255)&0xFF);
      buf[5] = (byte)(pos[ch]);
      buf[6] = (byte)((pos[ch]+0x006F)&0xFF);
      buf[7] = (byte)((pos[ch]+0x00CF)&0xFF);
      buf[8] = (byte)(pos[ch]&0xFF);
      buf[9] = 0x0A;
      
      myPort.write(buf);
      pos[ch] += 3;
      if ( pos[ch] > 255 ) {
        pos[ch] = 0;
        //delay(300);
      }     
  }
  
  delay(800);
}

