// package TwoThreeTree_to_LLRedBlackTree;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

/**
 * @author Mitas Ray
 */
public class TreeDrawer extends JPanel {

	public static int BOX_LENGTH = 30;
	public static double SCALING_FACTOR = 0.75;
	public static int START_X_LENGTH = 200;
	public static int START_Y_LENGTH = 100;

	LLRedBlackTree llrbt;

	public TreeDrawer(LLRedBlackTree t) {
		llrbt = t;
	} 

	@Override
	public void paintComponent(Graphics g) {
		draw(g, llrbt, 0, 485, 10);
	}

	private void drawBox(Graphics g, String value, int x, int y) {
		g.setColor(Color.black);
		g.drawRect(x, y, BOX_LENGTH, BOX_LENGTH);
		g.drawString(value, x + (BOX_LENGTH / 3), y + (BOX_LENGTH * 2 / 3));
	}

	// true = left, false = right
	// true = black, false = red
	private int[] drawLine(Graphics g, int x, int y, int depth, boolean isLeft, boolean isBlack) {
		int xLength = START_X_LENGTH * ((int) Math.pow(SCALING_FACTOR, depth));
		int yLength = START_Y_LENGTH * ((int) Math.pow(SCALING_FACTOR, depth));
		if (isBlack) {
			g.setColor(Color.black);
		} else {
			g.setColor(Color.red);
		}
		if (isLeft) {
			xLength *= -1;
		} else {
			x += BOX_LENGTH;
		}
		y += BOX_LENGTH;
		g.drawLine(x, y, x + xLength, y + yLength);
		return new int[]{x + xLength, y + yLength};
	}

	private void draw(Graphics g, LLRedBlackTree t, int depth, int x, int y) {
		if (t == null) {
			return;
		} else {
			boolean linkColor;
			drawBox(g, t.value, x, y);
			if (t.left != null) {
				linkColor = t.left.upperLinkColor;
				int[] coords = drawLine(g, x, y + BOX_LENGTH, depth, true, linkColor);
				draw(g, t.left, depth + 1, coords[0] - BOX_LENGTH, coords[1]);
			}
			if (t.right != null) {
				linkColor = true;
				int[] coords = drawLine(g, x + BOX_LENGTH, y + BOX_LENGTH, depth, false, linkColor);
				draw(g, t.right, depth + 1, coords[0], coords[1]);
			}
		}
	}

	public void draw() {
		if (llrbt == null) {
			System.out.println("TwoThreeTree hasn't been converted to LLRedBlackTree yet.");
			return;
		} else {
			JFrame jFrame = new JFrame();
        	jFrame.add(new TreeDrawer(llrbt));
        	jFrame.setSize(1000, 600);
        	jFrame.setVisible(true);
			// draw(llrbt, 0, 485, 10);
		}
	}

	public static void main(String[] args) {
		
	}
}