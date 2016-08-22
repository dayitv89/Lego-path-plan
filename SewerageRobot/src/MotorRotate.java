
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.NXTConnection;
import lejos.util.Delay;

public class MotorRotate implements Runnable {

    boolean run = true, toUSB = true;
    int range;
    USSensor usSensorObj;

    // data store in pgn variable
//    StringBuffer pgn = new StringBuffer(1024);
    String pgn = "0,0";
    int turn = 0;
    int x = 0, y = 0;
    boolean yDirection = true;
    int totalTurn = 0;

    NXTConnection btConnection;
    DataInputStream dis;
    DataOutputStream dos;

    // start a new thread call from main method.
    public void run() {
        isRotating = true;
        LCD.drawString("Press Orange\nbutton to run", 1, 1);
        Button.waitForAnyPress();
        isRotating = false;
        toUSB = false;
        LCD.clear();
        LCD.drawString("SewerageRobot\nrunning", 1, 1);

        // motor move to forward direction
        while (true) {
            if (run) {
                moveForward();
            }
            if (toUSB) {
                run = false;
                // motor stop and send USB data
                LCD.clear();
                LCD.drawString("waiting for USB", 0, 1);
                LCD.drawString("press button", 0, 4);
                toUSB = true;

                btConnection = USB.waitForConnection();
                LCD.clear();
                LCD.drawString("connected...", 0, 1);
                dis = new DataInputStream(new BufferedInputStream(btConnection.openDataInputStream(), 1024));
                dos = new DataOutputStream(new BufferedOutputStream(btConnection.openDataOutputStream(), 1024));

                sendMsgToServer(pgn + "\n\n\n");

                Delay.msDelay(2000);
                LCD.clear();
                LCD.drawString("Done ", 1, 4);

                Delay.msDelay(2000);
                break;
            }
        }

    }

    // msg right and update
    int direction = 1;
    public void writeMsgAndUpdateDirection(boolean rightTern) {
        switch (direction) {
            case 1: {
                if (rightTern) {
                    direction = 2;
                } else {
                    direction = 4;
                }
                y += turn;
            }
            break;
            case 2: {
                if (rightTern) {
                    direction = 3;
                } else {
                    direction = 1;
                }
                x += turn;
            }
            break;
            case 3: {
                if (rightTern) {
                    direction = 4;
                } else {
                    direction = 2;
                }
                y -= turn;
            }
            break;
            case 4: {
                if (rightTern) {
                    direction = 1;
                } else {
                    direction = 3;
                }
                x -= turn;
            }
            break;
        }// switch case end
        pgn += "," + x + "," + y;
        
        
    }

    // ultra sonic detect
    boolean isRotating = false;
    public void USDetected(int distance) {

        if (!isRotating) {
            isRotating = true;
            range = distance;
            LCD.clear();
            LCD.drawString("Range :" + range, 1, 1);
            LCD.drawString("trun " + turn, 1, 4);

            run = false;
            Motor.B.stop();
            Motor.C.stop();

            boolean rightTern = true;

            // 45 degree right turn in forward = true
            rotate(90, 2, true);
            if (usSensorObj.detectObsticle()) {
                rotate(80, 2, false); // turn right backward
                rotate(90, 1, true);  // turn left forward
                rightTern = false;
                writeMsgAndUpdateDirection(rightTern);
                if (usSensorObj.detectObsticle()) {
                    deadEnd();
                    isRotating = true; // to stop to do this
                    return;
                } else {
                    robotRun();
                }
            } else {
                rightTern = true;
                writeMsgAndUpdateDirection(rightTern);
                robotRun();
            }            
        }
    }

    public void robotRun() {
        turn = 0;
        run = true;  
        isRotating = false;
//        usSensorObj.setDetect(true);
    }

    public void deadEnd() {
        run = false;
        toUSB = true;
//        usSensorObj.toUSB = true;
    }

    public void sendMsgToServer(String msg) {
        try {
            dos.write(msg.getBytes());
            dos.flush();
            dos.close();
            dis.close();
        } catch (IOException ex) {
            LCD.clear();
            LCD.drawString("BT DOS Error", 1, 1);
        }

    }

    // Motor A :: direction left = 1, right = 2 else only motor A will turn
    public void rotate(int angle, int direction, boolean forward) {
//        float r = 11.5f;
////        float require = (float)((r*angle*Math.PI)/180);
//        float require = (float) (angle * 0.20071286f);
//        int rotation = (int) (Math.ceil((360 * require) / 17.28));
        int rotation = 370;

        // left tern
        if (direction == 1) {
            Motor.C.setSpeed(360);
            rotation = 430;
            if (forward) {
                Motor.C.rotate(-rotation);
            } else {
                Motor.C.rotate(360);
            }
        } else if (direction == 2) { // right tern
            Motor.B.setSpeed(360);
            rotation = 335;
            if (forward) {
                Motor.B.rotate(-rotation);
            } else {
                Motor.B.rotate(rotation);
            }
        }
        Delay.msDelay(1000);
        /*int speed = 420;
         if (direction == 1) {
         // move left
         Motor.A.setSpeed(angle);
         Motor.A.rotate(angle);

         if (forward) {
         Motor.C.setSpeed(speed);
         Motor.C.rotate(-speed);
         } else {
         Motor.C.setSpeed(speed);
         Motor.C.rotate(speed);
         }

         Motor.A.setSpeed(angle);
         Motor.A.rotate(-angle);

         } else if (direction == 2) {
         // move right
         Motor.A.setSpeed(angle);
         Motor.A.rotate(-angle);

         if (forward) {
         Motor.B.setSpeed(speed);
         Motor.B.rotate(-speed);
         } else {
         Motor.B.setSpeed(speed);
         Motor.B.rotate(speed);
         }

         Motor.A.setSpeed(angle);
         Motor.A.rotate(angle);
         } else {
         // motor A will rotate
         //            int motorASpeed = angle < 0 ? -angle : angle;
         Motor.A.setSpeed(angle);
         Motor.A.rotate(angle);
         }*/
    }

    public void moveForward() {
        turn++;
//        Motor.B.backward();
//        Motor.C.backward();
        Motor.B.setSpeed(360);
        Motor.C.setSpeed(360);

        Motor.B.rotate(-360, true);
        Motor.C.rotate(-360, true);
//        Delay.msDelay(1000);

    }

    public void moveBackward() {
        Motor.B.forward();
        Motor.C.forward();
    }
}
