package robotrun;


import java.awt.*;
import java.awt.event.*;
import java.applet.*;
/*<applet code="Paint" width=1000 height=600></applet>*/

public class Paint3 implements MouseMotionListener, MouseListener {

    Frame f;
    Graphics g;
    int array[];

    public Paint3(int a[]) {
        array = a;
        f = new Frame("Mouse Paint");
        f.setBounds(0, 0, 1000, 600);
        f.setBackground(Color.black);
        f.addMouseMotionListener(this);
        f.addMouseListener(this);
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        g = f.getGraphics();
    }
    //MouseMotionListener

    public void mouseMoved(MouseEvent e) {
        int x, y, xEnd, yEnd;
        int i = 0;
        
        for ( i = 0; i < array.length; i += 4) {

            x = array[i];
            y = array[i + 1];
            xEnd = array[i + 2];
            yEnd = array[i + 3];
            System.out.println(x + "," + y + "," + xEnd + "," + yEnd);
            g.setColor(Color.red);
            g.drawLine(x, -y+400, xEnd, -yEnd+400);
        }
        i = 0;
        x = array[i];
        y = array[i + 1];
        xEnd = array[i + 2];
        yEnd = array[i + 3];
        g.setColor(Color.green);
        g.drawLine(x, -y+400, xEnd, -yEnd+600);
    }

    public void mouseDragged(MouseEvent e) {
    }

    //MouseListener
    public void mouseClicked(MouseEvent e) {
        int button = e.getButton();
        //System.out.println(button);    		
        if (button == 3 && f.getSize().height != 300 && f.getSize().width != 300) {
            f.setSize(300, 300);
        } else if (button == 3) {
            f.setSize(1000, 600);
        } else {
            g.clearRect(0, 0, f.getSize().width, f.getSize().height);
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}