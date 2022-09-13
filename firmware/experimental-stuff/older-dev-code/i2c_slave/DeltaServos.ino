/**
 * DeltaServos - Class to handle the three Delta Servos
 *
 */
#include "DeltaServos.h"

DeltaServos::DeltaServos() {   
  phi_prev[0] = 0;
  phi_prev[1] = 0;
  phi_prev[2] = 0;
  
  //byte testpos=0;
  
  led_r_val = 0;
  led_g_val = 0;
  led_b_val = 0;
  
  int servo_dir = -1;
  int servo_delay = 15;
  
  testpos = 0;
}

DeltaServos::~DeltaServos() { }

Servo DeltaServos::get( int servo ) {
  switch ( servo ) {
    case 1: return servoA;
    case 2: return servoB;
    case 3: return servoC;
    default: return servoA;
  }
}

void DeltaServos::begin() { // TODO: Pass the three pins here?
  servoA.attach(9); // attaches servo A to pin 9  
  servoB.attach(10); // attaches servo B to pin 10 
  servoC.attach(11); // attaches servo C to pin 11
}

void DeltaServos::updateServos( float sa, float sb, float sc ) {
  // Servo range to phi 0 to 180
  updateServos( (int)sa, (int)sb, (int)sc );
}

void DeltaServos::updateServos( float * abc ) {
  updateServos(abc[0], abc[1], abc[2]);
}

void DeltaServos::updateServos( int sa, int sb, int sc ) {
  phi[0] = map(sa,0,255, min_servo, max_servo);
  phi[1] = map(sb,0,255, min_servo, max_servo);
  phi[2] = map(sc,0,255, min_servo, max_servo);
  
  refresh();
}

void DeltaServos::refresh() {
  servoA.write(phi[0]);   // tell servo to go to positions in variable 'phi' 
  servoB.write(phi[1]);
  servoC.write(phi[2]);
  delay(servo_delay); 
}

void DeltaServos::demo() {  
  switch (testpos){
    case 0:
      for (int i=0; i<3; i++){
        phi[i]=45;
      }
      break;
    case 1:
      for (int i=0; i<3; i++){
        phi[i]=90;
      }
      break;
    case 2:
      for (int i=0; i<3; i++){
        phi[i]=180;
      }
      break;
    case 3:
      for (int i=0; i<3; i++){
        phi[i]=0;
      }
    default:
      break;
  }
  
  printData();
  loop();
  
  testpos++;
  if (testpos>3) {
    testpos=0;
  }
}

void DeltaServos::hold() {  
  /* Holds the servo at 90 degrees to attach delta robot
   Attach horn parallel to servo */
  for (int i=0; i<3; i++){
    phi[i]=90;
  }
  refresh();
}

void DeltaServos::printData() {
  Serial.print("angle1current:  ");
  Serial.print(phi[0]);
  Serial.print("  angle1prev:   "); 
  Serial.print(phi_prev[0]);
  for (int i = 0; i<3; i++){
    diff[i]=phi[i] - phi_prev[i];
    phi_prev[i]=phi[i];
  }
  Serial.print("  diff1:  ");
  Serial.println(diff[0]);
}

void DeltaServos::initArms() {
  int pos = min_servo;
  updateServos(pos,pos,pos);
  delay(800);  // Wait to get all the way there.
  
  while(pos != 0) {
    pos += servo_dir;
    led_r_val += led_r_dir;
    led_g_val += led_g_dir;
    led_b_val += led_b_dir;
    
    int r = map(led_r_val, 0, max_servo, 0, 179);
    int g = map(led_g_val, 0, max_servo, 0, 179);
    int b = map(led_b_val, 0, max_servo, 0, 179);

    updateServos(pos,pos,pos);
    brain.setLED(r, g, b);
  }
  delay(500);
  while(pos != 255) {
    pos -= servo_dir;
    led_r_val -= led_r_dir;
    led_g_val -= led_g_dir;
    led_b_val -= led_b_dir;
  
    int r = map(led_r_val, 0, max_servo, 0, 179);
    int g = map(led_g_val, 0, max_servo, 0, 179);
    int b = map(led_b_val, 0, max_servo, 0, 179);

    updateServos(pos,pos,pos);
    brain.setLED(r, g, b);
  } 

  delay(100);
  brain.setLED(0, 0, 250);
  delay(100);
  brain.setLED(0, 0, 0);
  delay(900);
  brain.setLED(0, 0, 250);
  delay(100);
  brain.setLED(0, 0, 0);
  delay(900);
  brain.setLED(0, 0, 250);
  delay(100);
  brain.setLED(0, 0, 0);
  delay(900);
  brain.setLED(0, 0, 250);
  delay(100);
  brain.setLED(0, 0, 0);
  delay(900);

  brain.setLED(250,250,250);
}

