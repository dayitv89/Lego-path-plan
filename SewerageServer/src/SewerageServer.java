
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class SewerageServer {

    public static void main(String[] args) throws IOException {
        try {
            System.out.println("SewerageServer start");
            
            NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
            NXTInfo[] nxtInfo = nxtComm.search("NXT");
            for (NXTInfo nxti : nxtInfo) {
                System.out.println("--------------------------device");
                System.out.println(nxti.deviceAddress + "," + nxti.name + "," + nxti.protocol+"," +nxti.connectionState);
            }
            if (nxtInfo.length == 0){
                System.out.println("Cannot find Robot.");
                return;
            }
            nxtComm.open(nxtInfo[0]);
            DataInputStream dis = new DataInputStream(new BufferedInputStream(nxtComm.getInputStream(),1024));
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(nxtComm.getOutputStream(),1024));
            byte [] disData = new byte[1024];
            while (true) {
                try {
                    dis.read(disData);
                    String msgFromRobot = new String(disData).trim();
                    System.out.print("Msg is:"+msgFromRobot);
                    write(msgFromRobot);
//                    System.out.print((char)dis.read());
//                    if (msgFromRobot.endsWith("\n\n\n")){
                        
                        break;
//                    }
                    
                } catch (IOException ex) {
                    System.out.println("dis error");
                    break;
                }
            }
        } catch (NXTCommException ex) {
            System.out.println("BT Server connection error");
        }
    }
    public static boolean write(String str){
        try {
            
            File newTextFile = new File("../../rajesh_map/data.txt");

            FileWriter fw = new FileWriter(newTextFile);
            fw.write(str);
            fw.close();
            

        } catch (IOException iox) {
            //do stuff with exception
            iox.printStackTrace();
            return false;
        }
        return true;
    }

}
