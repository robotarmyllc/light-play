/**
 * Stock Simulator
 * 
 */
 
import java.util.Random;
import java.io.*;
 
import processing.serial.*;
 

int backerCount = 0;

int current = 128;
byte dir = 1;
int dly = 50;

Serial serialPort;  // Create object from Serial class

void setup() {
  size(200, 200);
  background(50);
  fill(200);
  
  //x = y = z = 0;
  
  
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
  
  //chicken(1);
}

void draw() {
  Random rand = new Random();
  goTo( rand.nextInt(255));
  delay( 1000 );
}


void goTo( int val ) {
  
  if ( current < val ) {
    while ( current < val ) {
      current++;
      if ( current > 255 ) { current = 255; }
      int[] col = val2color(current);
      writeSerialPacket( current, current, current, col[0], col[1], col[2] );
      delay(dly);  
    }
  } else {
    while ( current >= val ) {
      current--;
      if ( current < 0 ) { current = 0; }
      int[] col = val2color(current);
      writeSerialPacket( current, current, current, col[0], col[1], col[2] );
      delay(dly);  
    }
  }
}

int[] val2color( int val ) {
  // val:  0    = red       ( 255, 0  , 0   )
  // val:  128   = white    ( 255, 255, 255 )
  // val:  255  = green     ( 0,   255, 0   )
  int[] col = {0,0,0};
  int r,g,b;
  if ( val < 128 ) {
    r = 255;
    g = b = val*2 - (128-val);
    if ( g < 0 ) {
      g = b = 0;
    }
  } else {
    g = 255;
    r = b = 255-((val-128)*2)-val;
    if ( r < 0 ) {
      r = b = 0;
    }
  }
  col[0] = r;
  col[1] = g;
  col[2] = b;
  
  return col;
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
  
  if ( x > 254 ) {
    x = y = z = 255;
    dir = -1;
  }
  if ( x < 1 ) {
    x = y = z = 0;
    dir = 1;
  }
  
  x+=dir;
  y+=dir;
  z+=dir;
  
  //delay(50);    
}
