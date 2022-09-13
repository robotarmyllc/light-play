/*  
  I2C Slave

*/

#include <Servo.h>
#include <Wire.h>

Servo myservo_a;  // create servo object to control a servo 
Servo myservo_b;  // create servo object to control a servo 
Servo myservo_c;  // create servo object to control a servo 
                
int led_r = 3;
int led_g = 5;
int led_b = 6;

int led_r_val = 0;
int led_g_val = 0;
int led_b_val = 0;

int led_r_dir = 1;
int led_g_dir = 1;
int led_b_dir = 1;

int min_servo = 175;
int max_servo = 85;
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

 
void setup() 
{ 
  myservo_a.attach(9);  // attaches the servo on pin 9 to the servo object 
  myservo_b.attach(10);  // attaches the servo on pin 9 to the servo object 
  myservo_c.attach(11);  // attaches the servo on pin 9 to the servo object
  
  pinMode(8, OUTPUT);  // Servo enable switch.
  digitalWrite(8, HIGH);
  
  initArms();
  
  //Wire.begin(initAddress());                // join i2c bus with addr
  Wire.begin(4);                // join i2c bus with addr
  
  Wire.onReceive(receiveEvent); // register event
} 
 
 
void loop() 
{ 
 delay(1000);    // Nothing to do here.

} 

// function that executes whenever data is received from master
// this function is registered as an event, see setup()
void receiveEvent(int howMany)
{
  if ( howMany != 6 ) return;  // Packet size is six!
  
  myservo_a.write(Wire.read());
  myservo_b.write(Wire.read());
  myservo_c.write(Wire.read());
  delay(servo_delay);                       // waits 15ms for the servo to reach the position 
  
  
  analogWrite(led_r, Wire.read());
  analogWrite(led_g, Wire.read());
  analogWrite(led_b, Wire.read());

}

int initAddress() {
  int addr = 0;
  pinMode(A0, INPUT);
  digitalWrite(A0, HIGH);  // set pullup on analog pin 0 
  pinMode(A1, INPUT);
  digitalWrite(A1, HIGH);  // set pullup on analog pin 1 
  pinMode(A2, INPUT);
  digitalWrite(A2, HIGH);  // set pullup on analog pin 2 
  pinMode(A3, INPUT);
  digitalWrite(A3, HIGH);  // set pullup on analog pin 3
  
  addr |= !digitalRead(A0);
  addr |= !(digitalRead(A1)<<1);
  addr |= !(digitalRead(A2)<<2);
  addr |= !(digitalRead(A3)<<3);
  
  return addr;
}

void initArms() {
  // zero motors
  pos = min_servo;
  while(pos != max_servo)
  {
    pos = pos + servo_dir;
    led_r_val = led_r_val + led_r_dir;
    led_g_val = led_g_val + led_g_dir;
    led_b_val = led_b_val + led_b_dir;
  
    myservo_a.write(pos);              // tell servo to go to position in variable 'pos' 
    myservo_b.write(pos);              // tell servo to go to position in variable 'pos' 
    myservo_c.write(pos);              // tell servo to go to position in variable 'pos' 
    analogWrite(led_r, led_r_val);
    analogWrite(led_g, led_g_val);
    analogWrite(led_b, led_b_val);
    delay(servo_delay);                       // waits 15ms for the servo to reach the position 
  } 
  while(pos != min_servo)
  {
    pos = pos - servo_dir;
    led_r_val = led_r_val - led_r_dir;
    led_g_val = led_g_val - led_g_dir;
    led_b_val = led_b_val - led_b_dir;
  
    myservo_a.write(pos);              // tell servo to go to position in variable 'pos' 
    myservo_b.write(pos);              // tell servo to go to position in variable 'pos' 
    myservo_c.write(pos);              // tell servo to go to position in variable 'pos' 
    analogWrite(led_r, led_r_val);
    analogWrite(led_g, led_g_val);
    analogWrite(led_b, led_b_val);
    delay(servo_delay);                       // waits 15ms for the servo to reach the position 
  } 

  analogWrite(led_b, 250);
  delay(100);
  analogWrite(led_b, 0);
  delay(500);
  analogWrite(led_b, 250);
  delay(100);
  analogWrite(led_b, 0);
  delay(500);
  analogWrite(led_b, 250);
  delay(100);
  analogWrite(led_b, 0);
  delay(500);
  analogWrite(led_b, 250);
  delay(100);
  analogWrite(led_b, 0);
  delay(500);
  analogWrite(led_b, 250);
  delay(100);
  analogWrite(led_b, 0);
  delay(500);
  analogWrite(led_r, 250);
  delay(100);
  analogWrite(led_r, 0);
  delay(500);
  analogWrite(led_r, 250);
  analogWrite(led_g, 250);
  analogWrite(led_b, 250);
}
