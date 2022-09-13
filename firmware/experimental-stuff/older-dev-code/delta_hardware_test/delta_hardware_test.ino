/*  Delta hardware test

Simple test code that runs the delta robots through their hardware tests,
as well as zeroizes the motors.

Note this code is not doing any inverse kinematics yet for true XYZ positioning.  It's just a
basic script of motions */

#include <Servo.h> 
 
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

 
void setup() 
{ 
  myservo_a.attach(9);  // attaches the servo on pin 9 to the servo object 
  myservo_b.attach(10);  // attaches the servo on pin 9 to the servo object 
  myservo_c.attach(11);  // attaches the servo on pin 9 to the servo object
  
  pinMode(8, OUTPUT);     // Enable servo power
  digitalWrite(8, HIGH);

  testServos();
} 
 
 
void loop() 
{ 
  // test motor 1
  pos = min_servo;
  while(pos != max_servo)
  {
    pos = pos + servo_dir;
    led_r_val = led_r_val + led_r_dir;
    
    myservo_a.write(pos);              // tell servo to go to position in variable 'pos' 
    analogWrite(led_r, led_r_val);
    delay(servo_delay);                       // waits 15ms for the servo to reach the position 
  } 
  while(pos != min_servo)
  {
    pos = pos - servo_dir;
    led_r_val = led_r_val - led_r_dir;
    
    myservo_a.write(pos);              // tell servo to go to position in variable 'pos' 
    analogWrite(led_r, led_r_val);
    delay(servo_delay);                       // waits 15ms for the servo to reach the position 
  } 

  // test motor 2
  pos = min_servo;
  while(pos != max_servo)
  {
    pos = pos + servo_dir;
    led_g_val = led_g_val + led_r_dir;
    
    myservo_b.write(pos);              // tell servo to go to position in variable 'pos' 
    analogWrite(led_g, led_g_val);
    delay(servo_delay);                       // waits 15ms for the servo to reach the position 
  } 
  while(pos != min_servo)
  {
    pos = pos - servo_dir;
    led_g_val = led_g_val - led_r_dir;
    
    myservo_b.write(pos);              // tell servo to go to position in variable 'pos' 
    analogWrite(led_g, led_g_val);
    delay(servo_delay);                       // waits 15ms for the servo to reach the position 
  } 

  // test motor 3
  pos = min_servo;
  while(pos != max_servo)
  {
    pos = pos + servo_dir;
    led_b_val = led_b_val + led_r_dir;
    
    myservo_c.write(pos);              // tell servo to go to position in variable 'pos' 
    analogWrite(led_b, led_b_val);
    delay(servo_delay);                       // waits 15ms for the servo to reach the position 
  } 
  while(pos != min_servo)
  {
    pos = pos - servo_dir;
    led_b_val = led_b_val - led_r_dir;
    
    myservo_c.write(pos);              // tell servo to go to position in variable 'pos' 
    analogWrite(led_b, led_b_val);
    delay(servo_delay);                       // waits 15ms for the servo to reach the position 
  } 


  delay(500);

    for(int i = 0 ; i < 2 ; i++)
    {
      while(pos != max_servo) {
        pos += servo_dir;
        led_r_val += led_r_dir;
        led_g_val += led_g_dir;
        led_b_val += led_b_dir;
    
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
        led_r_val -= led_r_dir;
        led_g_val -= led_g_dir;
        led_b_val -= led_b_dir;
    
        myservo_a.write(pos);              // tell servo to go to position in variable 'pos' 
        myservo_b.write(pos);              // tell servo to go to position in variable 'pos' 
        myservo_c.write(pos);              // tell servo to go to position in variable 'pos' 
        analogWrite(led_r, led_r_val);
        analogWrite(led_g, led_g_val);
        analogWrite(led_b, led_b_val);
        delay(servo_delay);                       // waits 15ms for the servo to reach the position 
      } 
    }

    delay(500);

    led_r_val = 0;
    led_g_val = 0;
    led_b_val = 0;
    led_r_dir = 1;
    led_g_dir = 1;
    led_b_dir = 1;

    pos_a_wait = 0;    // variable to store the servo position 
    pos_b_wait = 33;    // variable to store the servo position 
    pos_c_wait = 66;    // variable to store the servo position 
    dir_a = servo_dir;
    dir_b = servo_dir;
    dir_c = servo_dir;
    pos_a = min_servo + dir_a;
    pos_b = min_servo + dir_b;
    pos_c = min_servo + dir_c;

    for (long i = 0 ; i < 1024; i++)
    {   
      if(pos_a_wait > 0)
      {
        pos_a_wait--;
      }
      else
      {
        // check if at max
        if(pos_a != max_servo && pos_a != min_servo)
        {
          pos_a += dir_a;
          led_r_val += led_r_dir;
        }
        else
        {
          dir_a *= -1;
          led_r_dir *= -1;
          pos_a += dir_a;
          led_r_val += led_r_dir;
        }
      }

      if(pos_b_wait > 0)
      {
        pos_b_wait--;
      }
      else
      {
        // check if at max
        if(pos_b != max_servo && pos_b != min_servo) {
          pos_b += dir_b;
          led_g_val += led_g_dir;
        } else {
          dir_b *= -1;
          led_g_dir *= -1;
          pos_b += dir_b;
          led_g_val += led_g_dir;
        }
      }

      if(pos_c_wait > 0)
      {
        pos_c_wait--;
      }
      else
      {
        // check if at max
        if(pos_c != max_servo && pos_c != min_servo)
        {
          pos_c = pos_c + dir_c;
          led_b_val = led_b_val + led_b_dir;
        }
        else
        {
          dir_c *= -1;
          led_b_dir *= -1;
          pos_c += dir_c;
          led_b_val += led_b_dir;
        }
      }

      myservo_a.write(pos_a);              // tell servo to go to position in variable 'pos' 
      myservo_b.write(pos_b);              // tell servo to go to position in variable 'pos' 
      myservo_c.write(pos_c);              // tell servo to go to position in variable 'pos' 
      delay(servo_delay);                       // waits 15ms for the servo to reach the position 
      analogWrite(led_r, led_r_val);
      analogWrite(led_g, led_g_val);
      analogWrite(led_b, led_b_val);
    }

    myservo_a.write(min_servo);              // tell servo to go to position in variable 'pos' 
    myservo_b.write(min_servo);              // tell servo to go to position in variable 'pos' 
    myservo_c.write(min_servo);              // tell servo to go to position in variable 'pos' 
    delay(500);                       // waits 15ms for the servo to reach the position 
} 

void testServos() {
  // zero motors
    pos = min_servo;
    while(pos != max_servo)
    {
      pos =+ servo_dir;
      led_r_val =+ led_r_dir;
      led_g_val =+ led_g_dir;
      led_b_val =+ led_b_dir;
    
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

    flashLED(5);
}

void flashLED( int n ) {
  int i;
  
  for ( i=0; i<n ; i++ ) {
    analogWrite(led_b, 225);
    delay(100);
    analogWrite(led_b, 0);
    delay(900);
  }
  
  analogWrite(led_r, 225);
  delay(100);
  analogWrite(led_r, 0);
  delay(900);
}
