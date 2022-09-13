/**
 *  Local Servo Variables
 *
 */

#ifndef DELTA_SERVOS_H
#define DELTA_SERVOS_H
//#include <WProgram.h>
//#include <Servo.h>
//#define byte uint8_t

class DeltaServos {
  public:
    DeltaServos();
    ~DeltaServos();
    void begin();
    void updateServos(float sa, float sb, float sc);
    void updateServos( float * abc );
    void updateServos(int sa, int sb, int sc);
    void refresh();
    void demo();
    void hold();
    void printData();
    Servo get( int servo );
    void initArms();

  private:
    Servo servoA;  // create servo object to control a servo 
    Servo servoB;  // create servo object to control b servo 
    Servo servoC;  // create servo object to control c servo
  
    int phi[3];
    int phi_prev[3];
    int diff[3];

    uint8_t testpos;

    // Most other servos
//    const static int min_servo = 145; // Down Position
//    const static int max_servo = 85;  // Up Position

    // Power HD 3001HB
    const static int min_servo = 60; // Down Position
    const static int max_servo = 145;  // Up Position
    
    const static int led_r_dir = 1;
    const static int led_g_dir = 1;
    const static int led_b_dir = 1;
    
    const static int servo_dir = -1;
    const static int servo_delay = 5;
    
    int led_r_val;
    int led_g_val;
    int led_b_val;
    
//    int pos_a;    // variable to store the servo position 
//    int pos_b;    // variable to store the servo position 
//    int pos_c;    // variable to store the servo position 

};

#endif
                

