/*  Brain Board Tester

  Boards to be tested (Machine Under Test [MUT]) are plugged into the
  test board and checked for proper operation.
  
*/

#define RST_MUT  7
#define RST_DET  A2
#define LED_PASS A1
#define LED_FAIL A0
#define LED_YEL  13
#define SV3      9
#define SV2      10
#define SV1      11

int state = 0;   // Testng:0, Pass:1, Fail:2

void setup() {
  pinMode(LED_PASS, OUTPUT);
  pinMode(LED_FAIL, OUTPUT);
  pinMode(LED_YEL,  OUTPUT);
  
  pinMode(SV1, INPUT);
  pinMode(SV2, INPUT);
  pinMode(SV3, INPUT);
  //digitalWrite(SV1, HIGH);  // Pullup
  
  digitalWrite(LED_PASS, LOW);
  digitalWrite(LED_FAIL, LOW);
  digitalWrite(LED_YEL,  LOW);
  
  // Reset the MUT
  digitalWrite(RST_MUT, LOW);
  digitalWrite(RST_MUT, HIGH);
  
} 
 
 
void loop() {
  digitalWrite(LED_YEL,  digitalRead(SV1));
  digitalWrite(LED_PASS,  digitalRead(SV2));
  digitalWrite(LED_FAIL,  digitalRead(SV3));
    
  // Set Pass/Fail
  switch (state) {
    case 0:  // TESTING
      digitalWrite(LED_PASS, LOW);
      digitalWrite(LED_FAIL, LOW);
      //digitalWrite(LED_YEL,  HIGH);
      break;
    case 1:  // PASS
      digitalWrite(LED_PASS, HIGH);
      digitalWrite(LED_FAIL, LOW);
      //digitalWrite(LED_YEL,  LOW);
      break;
    case 2:  // FAIL
      digitalWrite(LED_PASS, LOW);
      digitalWrite(LED_FAIL, HIGH);
      //digitalWrite(LED_YEL,  LOW);
      break;
  }
} 

