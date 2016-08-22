public class SewerageRobot {

    public static void main(String[] args) {
        MotorRotate motorRotate = new MotorRotate();
        new Thread(motorRotate).start();
        
        USSensor usSensor = new USSensor(motorRotate, 50, 100);
        new Thread(usSensor).start();
    }    
}
