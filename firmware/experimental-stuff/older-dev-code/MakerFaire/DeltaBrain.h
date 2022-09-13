#ifndef DELTA_BRAIN_H
#define DELTA_BRAIN_H

class DeltaBrain {
   
   public:
     DeltaBrain();
     void begin();
     int getDIP();
     void enableMotors();
     void disableMotors();
     void setLED( int r, int g, int b);
     void flashLED( int count, int r, int g, int b );
     
   private:
     const static int LED_R = 3;
     const static int LED_G = 5;
     const static int LED_B = 6;
     
     const static int MOTOR_EN = 8;
};


#endif
