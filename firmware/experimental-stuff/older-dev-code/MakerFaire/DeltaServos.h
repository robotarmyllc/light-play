/**
 *  Local Servo Variables
 *
 */
#ifndef DELTA_SERVOS_H
#define DELTA_SERVOS_H
//#include "Arduino.h"
#include "Servo.h"
#define INVERT

class DeltaServos {
  public:
    DeltaServos();
    ~DeltaServos();
    void begin();
    void updateServos(float sa, float sb, float sc);
    void updateServos( float * abc );
    void updateServos(int sa, int sb, int sc);
    void refresh();
    void hold();
    void printData();
    Servo get( int servo );
    void initArms();
    void upDown( int cycleTime );
 
   
#ifndef INVERT
    // 
    const static int MIN_SERVO = 140; // Down Position
    const static int MAX_SERVO = 80;  // Up Position
    const static int FLAT_SERVO = 90;  // Servos parallel/level with table for mounting.
#else // INVERT
    // 
    const static int MIN_SERVO = 53; // Down Position
    const static int MAX_SERVO = 141;  // Up Position
    const static int FLAT_SERVO = 108;  // Servos parallel/level with table for mounting.
#endif

  private:
    Servo servoA;  // create servo object to control a servo 
    Servo servoB;  // create servo object to control b servo 
    Servo servoC;  // create servo object to control c servo
  
    //uint8_t testpos;
    
    const static int led_r_dir = 1;
    const static int led_g_dir = 1;
    const static int led_b_dir = 1;
    
    const static int servo_dir =  1;
    const static int servoDelay = 10;
    
    int led_r_val;
    int led_g_val;
    int led_b_val;
    
};

#endif
      
      
         

