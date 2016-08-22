package robotrun;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RobotRun {

    public static void main(String args[]) {
        RobotRun robotRun = new RobotRun();
        robotRun.readDataFile("data.txt");
        Paint3 paint3 = new Paint3(robotRun.array);
//        robotRun.showMap();
    }

   
    Frame f;
    Graphics g;
    int array[];

    public RobotRun() {
//        f = new Frame("Mouse Paint");
//        f.setBounds(0, 0, 1000, 900);
////        f.setBackground(Color.black);
//
////        f.setVisible(true);
//        f.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });
//         f.setVisible(true);
//        g = f.getGraphics();
//        g.setColor(Color.BLACK);
//        g.drawLine(0, 0, 100, 100);
    }

    public void readDataFile(String name) {
        FileInputStream fis = null;
        try {
            File file = new File(name);
            fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            try {
                fis.read(data);
                String s = new String(data, "UTF-8");
                String dataArray[] = s.split(",");
                array = new int[dataArray.length-1];
                for(int i = 0; i < dataArray.length; i++){
                    if (dataArray[i].equals("#")){
                        break;
                    }
                    array[i] = Integer.parseInt(dataArray[i])+50;
                    
                }
                
                
            } catch (IOException ex) {
                Logger.getLogger(RobotRun.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RobotRun.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(RobotRun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void showMap() {
        g.clearRect(0,0,f.getSize().width,f.getSize().height);
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, 100, 100);
        
        
        int x, y, xEnd, yEnd;
        for(int i = 0; i < array.length ; i+=4 ){
           
           x = array[i];
           y = array[i+1];
           xEnd = array[i+2];
           yEnd = array[i+3]; 
           System.out.println(x+","+y+","+xEnd+","+yEnd);
           g.setColor(Color.red);
           g.drawLine(x, y, xEnd, yEnd);
        }
    }   
}
