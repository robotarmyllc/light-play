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
     void flashD13( int count );
     const static int OBLED = 13;
     
   private:
     const static int LED_R = 3;
#ifndef REV_D
     const static int LED_G = 5;
     const static int LED_B = 6;
#else
     const static int LED_G = 6;
     const static int LED_B = 5;
#endif
     
     const static int MOTOR_EN = 8;
     
};


#endif

