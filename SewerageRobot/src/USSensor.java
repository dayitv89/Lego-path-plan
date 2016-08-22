
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

public class USSensor implements Runnable {

    int range, sleep;
    MotorRotate motorRotate;
//    boolean detect = true;
//    boolean toUSB = false;
    UltrasonicSensor us;

    public USSensor(MotorRotate mr, int r, int s) {
        us = new UltrasonicSensor(SensorPort.S1);
        motorRotate = mr;
        range = r;
        sleep = s;
        
    }
//    public void setDetect(boolean val){
//        detect = val;
//    }

    
    public void run() {
        motorRotate.usSensorObj = this; 
        while (true) {
            int usRange = (int)(us.getRange());
            if (usRange <= range) {
                motorRotate.USDetected(usRange);
                Delay.msDelay(2000);
            }
            Delay.msDelay(sleep);
        }
//        int usRange;
//        while (true) {
//            if (detect) {
//                usRange = (int) (us.getRange());
//                if (usRange <= range) {
//
//                    motorRotate.USDetected(usRange);
//                    detect = false;
//                }
//            }
//            Delay.msDelay(sleep);
//            if (toUSB) {
//                break;
//            }
//        }
    }

    public boolean detectObsticle() {
        int usRange, counter = 0;
        while (counter < 3) {
            usRange = (int) (us.getRange());
            if (usRange <= range) {
                return true;
            }
            counter++;
            Delay.msDelay(sleep);
        }
        return false;
    }
}
