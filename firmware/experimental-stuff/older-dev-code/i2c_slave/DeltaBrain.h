#ifndef DELTA_BRAIN_H
#define DELTA_BRAIN_H

class DeltaBrain {
   
   public:
     DeltaBrain();
     void begin();
     int getAddress();
     void enableMotors();
     void disableMotors();
     void setLED( int r, int g, int b);
     
   private:
     const static int led_r = 3;
     const static int led_g = 5;
     const static int led_b = 6;
};

#endif
