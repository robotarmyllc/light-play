


#include "CuddleServo.h"


void setup() {
  pinMode(CuddleServo::MOTOR_EN, OUTPUT);  // Servo enable switch.  
  digitalWrite(CuddleServo::MOTOR_EN, HIGH);
  delay(500);    // Wait a little.


  servos.begin();                       //Initiate timers and misc. 
}

void loop() {
  //    DemoDance1();
  //    DemoDance2();
  //    DemoDance3();
  //    DemoDance1();
  //    DemoDance4();
  //    DemoDance5();
  //    DemoDance6();

  DemoDance7();
}

void DemoDance6() {
  long MinPulse = 8320;
  long MaxPulse = 35200;  
  int i = 0;
  int i2 = 0;
  for(i2 = 0; i2 < 5 ; i2++)           // All servos go to random position. Then go back to middle in same time.
  {
    for(i = 0; i < 20 ; i++)
    {
      long a = random(MaxPulse-MinPulse)+MinPulse;        
      servos.move(i, a, 0, 100);
    }
    delay(1500);
    for(i = 0; i < 20 ; i++)
    {
      servos.move(i, 24000, 0, 3000);
    }
    delay(4000);
  }
}

void DemoDance5() {
  long MinPulse = 8320;
  long MaxPulse = 35200;  
  int i = 0;
  int i2 = 0;
  i = 0;                                         // All servos moving randomly
  for(i2 = 0; i2 < 1000 ; i2++)
  {
    long a = random(MaxPulse-MinPulse)+MinPulse;        
    int DemoTime = random(1000)+300;
    servos.move(i, a, 0, DemoTime);
    delay(DemoTime/25);
    i++;
    if(i > 2) i = 0;
  }
}

void DemoDance4() {
  long MinPulse = 8320;
  long MaxPulse = 35200;  
  int i = 0;
  int i2 = 0;
  float i4 = 0;
  float pi = 3.14159265359;

  for(i2 = 10; i2 > 0 ; i2--)                // Faster bounce
  {
    for(i4 = 0 ; i4 < pi ; i4 += 0.03)
    {
      for(i = 0; i < 3 ; i++) servos.pulseW[i] = sin(i4)*((MaxPulse-MinPulse)/2) + (MinPulse);
      delay(10);
    }
  }
}

void DemoDance3() {
  long MinPulse = 8320;
  long MaxPulse = 35200;  
  int i = 0;
  int i2 = 0;
  float i4 = 0;
  float pi = 3.14159265359;  // Use M_PI

  for(i2 = 10; i2 > 0 ; i2--)                // Slow bounce
  {
    for(i4 = 0 ; i4 < pi ; i4 += 0.005+(0.001*i2))
    {
      for(i = 0; i < 20 ; i++) servos.pulseW[i] = sin(i4)*(MaxPulse-MinPulse) + MinPulse;
      delay(10);
    }
  }
}

void DemoDance2() {
  long MinPulse = 8320;
  long MaxPulse = 35200;
  int i = 0;
  int i2 = 0;

  for(i2 = 0; i2 < 10 ; i2++)              //Sweep servos, every other way, from fast to slow
  {
    for(i = 0; i < 3 ; i+=2) 
    {
      servos.move(i, MinPulse, 0, 1000+i2*100);
      servos.move(i+1, MaxPulse, 0, 1000+i2*100);
    }
    delay(1000+i2*100);  
    for(i = 0; i < 20 ; i+=2) 
    {
      servos.move(i, MaxPulse, 0, 1000+i2*100);
      servos.move(i+1, MinPulse, 0, 1000+i2*100);
    }
    delay(1000+i2*100);
  }
}

void DemoDance1()
{
  long MinPulse = 8000;
  long MaxPulse = 36000;
  int i = 0;
  int i2 = 0;
  int i3 = 1;

  for(i = 0; i < 100 ; i++)                // Wave move circular
  {
    servos.move(i2, MinPulse, 0, 600);
    servos.move(i3, MaxPulse, 0, 600);
    delay(300);
    i2++;
    i3++;
    if(i2>2) i2=0;
    if(i3>2) i3=0;
  }
}

void DemoDance7() {
  long MinPulse = 8000;
  long MaxPulse = 30000;
  int nSteps = 1000;

  long pulseStep = (MaxPulse-MinPulse)/nSteps;

  for(int i = 0; i < nSteps ; i++) {
    servos.move(1, MinPulse + pulseStep*i, 0, 600); 
    delay(200);   
  }
  for(int i = nSteps; i > 0 ; i--) {
    servos.move(1, MinPulse + pulseStep*i, 0, 600); 
    delay(200);   
  }

  //delay(300);
}


