// Modified from: http://stackoverflow.com/questions/10126695/how-to-draw-a-tree-representing-a-graph-of-connected-nodes

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

public class JPanelTest extends JPanel {

    public static void draw() {
        JFrame jFrame = new JFrame();
        jFrame.add(new JPanelTest());
        jFrame.setSize(1000, 600);
        jFrame.setVisible(true);
    }

    public void drawActual(Graphics g, int h) {
        g.drawRect(0, 0, 30, 30);
        g.drawString("M", 10, 20);
        g.setColor(Color.red);
        g.drawLine(30, 30, 60, h);
        g.setColor(Color.black);
    }

    @Override
    public void paintComponent(Graphics g) {
        // Draw Tree Here
        drawActual(g, 30);
    }

    public static void main(String[] args) {
        draw();
    }
}