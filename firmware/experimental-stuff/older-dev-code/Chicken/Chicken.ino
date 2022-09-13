/*  Delta hardware test

  Simple test code that runs the delta robots through their hardware tests,
  as well as zeroizes the motors.
  
  Note this code is not doing any inverse kinematics yet for true XYZ
  positioning.   It's just a basic script of motions 
*/

#include <Servo.h>
#include <Wire.h>
#include <Math.h>
 
Servo myservo_a;  // create servo object to control a servo 
Servo myservo_b;  // create servo object to control a servo 
Servo myservo_c;  // create servo object to control a servo 
                // a maximum of eight servo objects can be created 
                
int led_r = 3;
int led_g = 5;
int led_b = 6;

int led_r_val = 0;
int led_g_val = 0;
int led_b_val = 0;

int led_r_dir = 1;
int led_g_dir = 1;
int led_b_dir = 1;

int min_servo = 180;
int max_servo = 80;
int servo_dir = -1;
int servo_delay = 15;
int pos = min_servo;    // variable to store the servo position 
int pos_a = min_servo;    // variable to store the servo position 
int pos_b = min_servo;    // variable to store the servo position 
int pos_c = min_servo;    // variable to store the servo position 
int dir_a = servo_dir;    // variable to store the servo position 
int dir_b = servo_dir;    // variable to store the servo position 
int dir_c = servo_dir;    // variable to store the servo position 

int pos_a_wait = 0;    // variable to store the servo position 
int pos_b_wait = 33;    // variable to store the servo position 
int pos_c_wait = 66;    // variable to store the servo position 

int frame = 0;
int maxframes = (int)(100*2*M_PI);

//int servoLow = 85;
//int servoHigh = 140;

int servoLow = 60;
int servoHigh = 145;

int servoRange = servoHigh-servoLow;
int servoMid = servoLow + (servoRange/2);

 
void setup() 
{ 
  myservo_a.attach(9);  // attaches the servo on pin 9 to the servo object 
  myservo_b.attach(10);  // attaches the servo on pin 9 to the servo object 
  myservo_c.attach(11);  // attaches the servo on pin 9 to the servo object

  pinMode(8, OUTPUT);     // Enable servo power
  digitalWrite(8, HIGH);
  delay(100); // Let digital servo settle out.
  
  //initArms();
  
  //Serial.begin(9600);
} 
 
 
void loop() 
{   
   robotSwirl();
   
   robotRandom();
   robotRandom();
   robotRandom();
   robotRandom();
   robotRandom();
  
   robotChicken();
   robotChicken();
   robotChicken();
   
   robotRandom();
   robotRandom();
   robotRandom();
   robotRandom();
   robotRandom();
   
   robotBob();
   robotBob();
   robotBob();
   
   robotRandom();
   robotRandom();
   robotRandom();
   robotRandom();
   robotRandom();
  
} 


void initArms() {
  // zero motors
  pos = min_servo;
  while(pos != max_servo)
  {
    pos = pos + servo_dir;
    led_r_val = led_r_val + led_r_dir;
    led_g_val = led_g_val + led_r_dir;
    led_b_val = led_b_val + led_r_dir;
  
    robotDo(pos, pos, pos, led_r_val, led_g_val, led_b_val);
  }
  
  while(pos != min_servo)
  {
    pos = pos - servo_dir;
    led_r_val = led_r_val - led_r_dir;
    led_g_val = led_g_val - led_r_dir;
    led_b_val = led_b_val - led_r_dir;
  
    robotDo(pos, pos, pos, led_r_val, led_g_val, led_b_val);
  } 

  analogWrite(led_b, 128);
  delay(100);
  analogWrite(led_b, 0);
  delay(900);
  analogWrite(led_b, 128);
  delay(100);
  analogWrite(led_b, 0);
  delay(900);
  analogWrite(led_b, 128);
  delay(100);
  analogWrite(led_b, 0);
  delay(900);
  analogWrite(led_b, 128);
  delay(100);
  analogWrite(led_b, 0);
  delay(900);
  analogWrite(led_b, 128);
  delay(100);
  analogWrite(led_b, 0);
  delay(900);
  analogWrite(led_r, 128);
  delay(100);
  analogWrite(led_r, 0);
  delay(900);
}

void robotSwirl() {
  frame = 0;
  
  while (frame < maxframes) {
    frame+=2;
    double a = sin(frame/100.0);
    double b = sin((frame+100)/100.0);
    double c = sin((frame+200)/100.0);
   
   robotDo( a,b,c, sin2color(a), sin2color(b), sin2color(c) );
  }
}

void robotChicken() {
  frame = 0;
  
  while (frame < maxframes) {
    frame+=20;
    double a = sin(frame/100.0);
    byte val = sin2color(a);
   
   robotDo( a, a, a, val, val, val );
  }
}

void robotBob() {  
   robotDo( -1.0, 0.0, 0.0, 255, 50, 50 );
   delay(400); 
   robotDo( 1.0, 1.0, 1.0, 0, 0, 0 );
   delay(200); 
   robotDo( 0.0, -1.0, -1.0, 50, 255, 255 );
   delay(400); 
   robotDo( 1.0, 1.0, 1.0, 0, 0, 0 );
   delay(200); 
}

void robotRandom() {
  robotRamp(random(20,150), dRandom(), dRandom(), dRandom(), dRandom(), dRandom(), dRandom() );
}

void robotRamp( int steps, double a1, double b1, double c1, double a2, double b2, double c2 ) {
  int i = 0;
  double ra = (a1 - a2)/steps;
  double rb = (b1 - b2)/steps;
  double rc = (c1 - c2)/steps;
 
   double a = a1;
   double b = b1;
   double c = c1;
  
  for ( i=0; i< steps; i++ ) {
    robotDo( a, b, c, abs(a*128), abs(b*128), abs(c*128));
    //delay(10);
    a+=ra;
    b+=rb;
    c+=rc;
  }
  
}

double dRandom() {
  return random(-1000,1000)/1000.0;
}


void robotDo(double sa, double sb, double sc, byte r, byte g, byte b) {
  myservo_a.write(getServoVal(sa));
  myservo_b.write(getServoVal(sb));
  myservo_c.write(getServoVal(sc));
  delay(servo_delay);                       // waits 15ms for the servo to reach the position 
 
  analogWrite(led_r, r);
  analogWrite(led_g, g);
  analogWrite(led_b, b);
  delay(20);
}

/**
 * in = 0.0 to 1.0
 out = 80-180
 */
int getServoVal( double in ) {
  int val = (servoRange*((in+1.0)/2.0)) + servoLow;
//  Serial.print("In: ");
//  Serial.print( in );
//  
//  Serial.print( " servo: ");
//  Serial.print(val);
//  Serial.println();

  if ( val < servoLow ) {
    val = servoLow;
  }
  
  if ( val > servoHigh ) {
    val = servoHigh;
  }
  
  return val;
}

byte sin2color(double v) 
{
  return (v+1.0)*128; 
}

