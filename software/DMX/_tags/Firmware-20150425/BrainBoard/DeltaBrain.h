#ifndef DELTA_BRAIN_H
#define DELTA_BRAIN_H

class DeltaBrain {
   
   public:
     DeltaBrain();
     void begin();
     int getDIP();
     void flashOBLED();
     void setLED( int r, int g, int b);
     void flashLED( int count, int r, int g, int b );
     void flashLED( int count, int r, int g, int b, int onTime, int offTime );
     
   private:
     const static int LED_R = 3;
     const static int LED_G = 5;
     const static int LED_B = 6;
     
     const static int MOTOR_EN = 8;
     const static int LED_ONBOARD = 13;
};


#endif
