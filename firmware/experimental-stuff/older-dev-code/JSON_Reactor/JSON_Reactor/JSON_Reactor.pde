/**
 * JSON Reactor
 * 
 * Retreives JSON data from Kickstarter and tells the delta to
 * react whenever the backers count increases.
 */
 

import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;
 
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import processing.serial.*;
 
String JSON_URL = "https://www.kickstarter.com/projects/search.json?search=&term=ROBOT+ARMY+STARTER";
URL    url;

int backerCount = 0;

int x,y,z;
byte dir = 1;

Serial serialPort;  // Create object from Serial class

void setup() {
  size(200, 200);
  background(50);
  fill(200);
  
  x = y = z = 0;
  
  
  try { 
   url = new URL(JSON_URL);
 
   //dump all the content
   //print_content(con);
 
  } catch (MalformedURLException e) {
   e.printStackTrace();
  }
 
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
  
  chicken(1);
}

void draw() {
  if ( updateBackerCount() ) {
    chicken(5);
  }
  
  delay( 60000 );
}

int getBackersCount(HttpsURLConnection con) {
  if(con!=null){
    try { 
       BufferedReader br = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
   
       String input;
       String bcStr = "backers_count";
   
       while ((input = br.readLine()) != null){
         if ( input.contains(bcStr) ) {
           String cS = input.substring(input.indexOf(bcStr), input.indexOf(bcStr)+25);
           cS = cS.substring(cS.indexOf(':')+1, cS.indexOf(',') );
           
           try {
             int num =  Integer.parseInt(cS);
             br.close();
             return num;
           } catch ( NumberFormatException e) {
           }
             //System.out.println(cS);
         }
       }
       br.close();
   
    } catch (IOException e) {
       e.printStackTrace();
    }
   
  }
  
  return 0;
}


void print_content(HttpsURLConnection con){
  if(con!=null){
 
    try {
   
       System.out.println("****** Content of the URL ********");      
       BufferedReader br = 
      new BufferedReader(
        new InputStreamReader(con.getInputStream()));
   
       String input;
   
       while ((input = br.readLine()) != null){
          System.out.println(input);
       }
       br.close();
   
    } catch (IOException e) {
       e.printStackTrace();
    }
   
  }
}

void chicken( int num) {
  for ( int i=0; i<num; i++) {
    writeSerialPacket( 200,200,200,255,0,0);
    delay(100);
    writeSerialPacket( 100, 100, 100, 0, 0, 255 );
    delay(200);
  }
  
}

boolean updateBackerCount() {
  try { 
   HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
 
   //dump all the content
   //print_content(con);
   
   int count = getBackersCount(con);
   System.out.println( "Count: " + count );
   if ( count > backerCount ) {
     backerCount = count;
     return true;
   }
 
  } catch (IOException e) {
   e.printStackTrace();
  }
 
 
 return false;
}

void writeSerialPacket( int x, int y, int z, int r, int g, int b ) {
  byte buf[] = new byte[10];
  
  buf[0] = 'D';
  buf[1] = 'D';
  buf[2] = (byte)0xFF;  // not used
  buf[3] = (byte)(x&0xFF);
  buf[4] = (byte)(z&0xFF);
  buf[5] = (byte)(y&0xFF);
  buf[6] = (byte)(x&0xFF);
  buf[7] = (byte)(z&0xFF);
  buf[8] = (byte)(y&0xFF);
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
