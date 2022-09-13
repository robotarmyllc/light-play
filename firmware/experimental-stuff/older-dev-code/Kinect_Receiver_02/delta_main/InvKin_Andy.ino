//float a = 3.14159; // length of hip
//float b = 5*sqrt(2); // length of ankle
//float r0 = 2; // radius of base
//float R0 = 1; // radius of effector
//float xg = 1; // position of effector
//float yg = 2;
//float zg = 4;
//float theta[]={0, 120*3.14159/180, -120*3.14159/180};
//
//float r[3][3]={{0,0,0},{0,0,0}, {0, 0, 0}};
//float R[3][3]={{0,0,0},{0,0,0}, {0, 0, 0}};
//
//float phi[3] = {0,0,0};
//void setup(){
//}

void InvKinA(){
  for (int i=0 ; i<3; i++){
    r[i][1] = r0*cos(theta[i]);
    r[i][2] = r0*sin(theta[i]);
    r[i][3] = 0;
    R[i][1] = xg + R0*cos(theta[i]);
    R[i][2] = yg + R0*sin(theta[i]);
    R[i][3] = zg + 0; 
  }
  for (int i=0; i<3; i++){
    float Cr = (R[i][1]-r[i][1])*cos(theta[i]) + (R[i][2]-r[i][2])*sin(theta[i]);
    float Ct = -(R[i][1]-r[i][1])*sin(theta[i]) + (R[i][2]-r[i][2])*cos(theta[i]);
    float Cz = zg;
    float A = a;
    float B = sqrt(b*b - Ct*Ct);
    float C = sqrt(Cr*Cr + Cz*Cz);
    float alpha = atan2(Cz,Cr)*180/3.14159;
    if (abs((A*A+C*C-B*B)/(2*A*C))<=1){   
      float beta = acos((A*A+C*C-B*B)/(2*A*C))*180/3.14159;
      phiA[i] = -(alpha-beta);
      phiA[i] = constrain(phiA[i],-90,90);
      phi[i]=map(phiA[i],-90,90,0,180);
    } else {
      phi[i] = phi_prev[i];
      Serial.println("Warning: Position Out of Range");
    }
    phi_prev[i] = phi[i];
  }
  //InvKinA_printdata();
  //delay(500);
}

void InvKinA_printdata(){
  for (int i=0; i<3; i++){
    Serial.print("anglecurrent:  ");
    Serial.print(phiA[i]);
    Serial.print("\t anglemapped: "); 
    Serial.println(phi[i]);
  }
}




