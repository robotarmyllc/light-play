/**
 * AirShapes
 * 
 * Draws with light for long exposure photography.
 * 
 */
 
import java.io.*;
import processing.serial.*;
 

Serial serialPort;  // Create object from Serial class

void setup() {
  size(200, 200);
  background(50);
  fill(200);
    
  initSerial();
  
}

void draw() {
  drawSquare();
}

void drawSquare() {
  writeSerialPacket( 128, 128, 100, 200, 200, 200 );
  delay(50);
  writeSerialPacket( 128, 128, 200, 200, 200, 200 );
  delay(50);
  writeSerialPacket( 128, 128, 100, 200, 200, 200 );
  delay(50);
  writeSerialPacket( 128, 128, 200, 200, 200, 200 );
  delay(50);
}


void initSerial() {
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
    serialPort = new Serial(this, portName, 57600);
  } else {
    println("No serial port found!!");
    exit();
  }
}


void writeSerialPacket( int x, int y, int z, int r, int g, int b ) {
  byte buf[] = new byte[10];
  
  buf[0] = 'D';
  buf[1] = 'D';
  buf[2] = (byte)0xFF;  // not used
  buf[3] = (byte)(x&0xFF);
  buf[4] = (byte)(z&0xFF);
  buf[5] = (byte)(y&0xFF);
  buf[6] = (byte)(r&0xFF);
  buf[7] = (byte)(g&0xFF);
  buf[8] = (byte)(b&0xFF);
  buf[9] = 0x0A;
  
  serialPort.write(buf);
}
