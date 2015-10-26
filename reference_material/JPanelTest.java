// Modified from: http://stackoverflow.com/questions/10126695/how-to-draw-a-tree-representing-a-graph-of-connected-nodes

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JPanelTest extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        // Draw Tree Here
        g.drawRect(0, 0, 30, 30);
        g.drawString("M", 10, 20);
        g.drawLine(30, 30, 60, 60);
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.add(new JPanelTest());
        jFrame.setSize(1000, 500);
        jFrame.setVisible(true);
    }
}