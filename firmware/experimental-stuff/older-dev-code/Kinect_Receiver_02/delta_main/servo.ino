
//#include <Servo.h> 

//Servo myservo1;  // create servo object to control servo 1
//Servo myservo2;  // create servo object to control servo 2
//Servo myservo3;  // create servo object to control servo 3
// 
//int posi=0; 
//int pos[] = {q1, q2, q3};    // goal angles for the servos (q1 q2 q3)

void Servosetup() 
{ 
  // Serial.begin(9600);
  servoA.attach(9); // attaches servo 1 to pin 9  
  servoB.attach(10); // attaches servo 2 to pin 10 
  servoC.attach(11); // attaches servo 3 to pin 11
}


void Servoloop() 
{
  /* Servo go to phi 0 to 180*/
  //Serial.print(phi[0]);
  servoA.write(phi[0]);              // tell servo to go to position in variable 'phi' 
  servoB.write(phi[1]);
  servoC.write(phi[2]);
  delay(10); 
}

void Servodemo()
{  
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
    Servo_printdata();
  Servoloop();
  testpos++;
  if (testpos>3) {
    testpos=0;
  }
}

void Hold(){  
  /* Holds the servo at 90 degrees to attach delta robot
   Attach horn parallel to servo */
  for (int i=0; i<3; i++){
    phi[i]=90;
  }
  Servoloop();
}

void Servo_printdata(){
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





