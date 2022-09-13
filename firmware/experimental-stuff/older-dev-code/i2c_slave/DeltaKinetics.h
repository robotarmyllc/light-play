/**
 *  Delta Kinetics
 *
 */
#ifndef DELTA_KINETICS_H
#define DELTA_KINETICS_H
//#include <WProgram.h>
//#include <Math.h>

class DeltaKinetics {
   
   public:
     DeltaKinetics();
     ~DeltaKinetics();
     void begin();
     void compute(float x, float y, float z);
     float * get();
     void printData();
     
   private:
    static const float a = 3; // length of hip, flat piece
    static const float b = 8; // length of ankle, tube
    static const float r0 = 1.5; // radius of base
    static const float R0 = 1; // radius of effector
    
    static const float zMax = 8.10;  // Z max input (when x,y=0)
    static const float zMin = 5.20;  // Z min input (when x,y=0)
    
    static const float incrementx = 0.10;
    static const float incrementy = 0.10;
    static const float incrementz = 0.10;
    
//    //static const float theta[]={0, 120*3.14159/180, -120*3.14159/180};
    float theta[3];
    
    float xg; //-incrementx; // position of effector
    float yg; //-incrementy;
    float zg;
    
//    //float r[3][3]={{0,0,0},{0,0,0}, {0, 0, 0}};
    float r[3][3];
//    //float R[3][3]={{0,0,0},{0,0,0}, {0, 0, 0}};
    float R[3][3];
    
    float phi_prev[3];
    float phiA[3];
    float phi[3];
    float diff[3];
};
 
#endif
