/**
 *  Local Servo Variables
 *
 */
#ifndef DELTA_SERVOS_H
#define DELTA_SERVOS_H
#include <Servo.h>

class DeltaServos {
  public:
    DeltaServos();
    ~DeltaServos();
    void begin();
    void updateServos(float sa, float sb, float sc);
    void updateServos( float * abc );
    void updateServos(int sa, int sb, int sc);
    void refresh();
    Servo get( int servo );
    void upDown( int cycleTime ); 
    
    //  PowerHD 3001HD Servos  --  Tune these values at your own risk! You could damage your robot if not careful.
    const static int MIN_SERVO = 44; // Down Position  (Range 0-179)
    const static int MAX_SERVO = 140;  // Up Position    (Range 0-179)  150
    const static int FLAT_SERVO = 120;  // Servos parallel/level with table for mounting. (Range 0-255)

  private:
    Servo servoA;  // create servo object to control a servo 
    Servo servoB;  // create servo object to control b servo 
    Servo servoC;  // create servo object to control c servo
      
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
      
      
         

