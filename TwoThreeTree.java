import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

/**
 * @author Mitas Ray
 */
public class TwoThreeTree extends JPanel {

	public static int BOX_LENGTH = 30;
	public static double SCALING_FACTOR = 0.75;
	public static int START_X_LENGTH = 200;
	public static int START_Y_LENGTH = 100;

	// If there is only one element in the node, then it will be 
	// represented as little.
	String little;
	String big;
	TwoThreeTree left;
	TwoThreeTree mid;
	TwoThreeTree right;
	LLRedBlackTree llrbt;

	private class LLRedBlackTree {
		String value;
		LLRedBlackTree left;
		LLRedBlackTree right;
		boolean upperLinkColor; // true = black, false = red.

		private LLRedBlackTree(String value, LLRedBlackTree left, LLRedBlackTree right, boolean upperLinkColor) {
			this.value = value;
			this.left = left;
			this.right = right;
			this.upperLinkColor = upperLinkColor;
		}

		private LLRedBlackTree(String value) {
			this.value = value;
			left = null;
			right = null;
			upperLinkColor = true;
		}

		private LLRedBlackTree(String value, boolean upperLinkColor) {
			this.value = value;
			left = null;
			right = null;
			this.upperLinkColor = upperLinkColor;
		}
	}

	public TwoThreeTree() {}

	public TwoThreeTree(String little) {
		this.little = little;
		big = "";
		left = null;
		mid = null;
		right = null;
		llrbt = null;
	}

	public TwoThreeTree(String little, String big) {
		this.little = little;
		this.big = big;
		left = null;
		mid = null;
		right = null;
		llrbt = null;
	}

	private void insertionError(String message) {
		System.out.println("Invalid insertion attempt: " + message + " Nothing was inserted.");
	}

	private void insert(String little, String big, String parent, TwoThreeTree t, String direction) {
		if (!big.equals("") && little.compareTo(big) > 0) {
			insertionError("LITTLE value was larger than BIG.");
		} else if (t == null) {
			insertionError("Could not find PARENT.");
			return;
		} else if (parent.length() > 2 || parent.length() < 1) {
			insertionError("Invalid number of elements in PARENT.");
			return;
		} else if (parent.length() == 1 && direction == "mid") {
			insertionError("Cannot insert into mid with parent having only one element.");
		} else if (parent.equals(t.little + t.big)) {
			if (direction.equals("left")) {
				if (little.compareTo(t.little) > 0) {
					insertionError("Either LITTLE value was larger and/or BIG value was larger than one or more of the parent.");
					return;
				}
				t.left = new TwoThreeTree(little, big);
			} else if (direction.equals("mid")) {
				if (little.compareTo(t.little) < 0 || (!t.big.equals("") && big.compareTo(t.big) > 0)) {
					insertionError("LITTLE value was larger than LITTLE of parent and/or BIG value was larger than BIG of parent.");
					return;
				}
				t.mid = new TwoThreeTree(little, big);
			} else {
				if (little.compareTo(t.big) < 0) {
					insertionError("LITTLE value was larger than LITTLE of parent and/or BIG value was larger than BIG of parent.");
				}
				t.right = new TwoThreeTree(little, big);
			}
		} else {
			if (little.compareTo(t.little) < 0) {
				insert(little, big, parent, t.left, direction);
			} else if (little.compareTo(t.big) < 0) {
				insert(little, big, parent, t.mid, direction);
			} else {
				insert(little, big, parent, t.right, direction);
			}
		}
	}

	public void insert(String little, String big, String parent, String direction) {
		insert(little, big, parent, this, direction);
	}

	public void insert(String little, String parent, String direction) {
		insert(little, "", parent, this, direction);
	}

	private void infix(TwoThreeTree t) {
		if (t.left != null) {
			infix(t.left);
		}
		System.out.print(t.little + " ");
		if (t.mid != null) {
			infix(t.mid);
		}
		System.out.print(t.big + " ");
		if (t.right != null) {
			infix(t.right);
		}
	}

	public void infix() {
		infix(this);
		System.out.println();
	}

	// Destructive.
	private LLRedBlackTree convert(TwoThreeTree t, boolean upperLinkColor) {
		if (t == null) {
			return null;
		} else if (t.big.equals("")) {
			return new LLRedBlackTree(t.little, convert(t.left, true), convert(t.right, true), upperLinkColor);
		} else {
			String big = t.big;
			t.big = "";
			TwoThreeTree rightPointer = t.right;
			t.right = t.mid;
			return new LLRedBlackTree(big, convert(t, false), convert(rightPointer, true), upperLinkColor);
		}
	}

	public void convert() {
		llrbt = convert(this, true);
	}

	private void rbInfix(LLRedBlackTree t) {
		if (t.left != null) {
			rbInfix(t.left);
		}
		System.out.print(t.value);
		if (!t.upperLinkColor) {
			System.out.print("(R)");
		}
		System.out.print(" ");
		if (t.right != null) {
			rbInfix(t.right);
		}
	}

	public void rbInfix() {
		if (llrbt == null) {
			System.out.println("TwoThreeTree hasn't been converted to LLRedBlackTree yet.");
			return;
		} else {
			rbInfix(llrbt);
			System.out.println();
		}
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
        	jFrame.add(new TwoThreeTree());
        	jFrame.setSize(1000, 600);
        	jFrame.setVisible(true);
			// draw(llrbt, 0, 485, 10);
		}
	}

	public static void testBasicInsertion() {
		System.out.println("testBasicInsertion");
		TwoThreeTree t = new TwoThreeTree("M");
		t.insert("E", "J", "M", "left");
		t.insert("A", "C", "EJ", "left");
		t.insert("H", "EJ", "mid");
		t.insert("L", "EJ", "right");
		t.insert("R", "M", "right");
		t.insert("P", "R", "left");
		t.insert("S", "X", "R", "right");
		t.infix();
	}

	public static void testBasicConversion() {
		System.out.println("testBasicConversion");
		TwoThreeTree t = new TwoThreeTree("M");
		t.insert("E", "J", "M", "left");
		t.insert("A", "C", "EJ", "left");
		t.insert("H", "EJ", "mid");
		t.insert("L", "EJ", "right");
		t.insert("R", "M", "right");
		t.insert("P", "R", "left");
		t.insert("S", "X", "R", "right");
		t.convert();
		t.rbInfix();
	}

	public static void testDraw() {
		System.out.println("testDraw");
		TwoThreeTree t = new TwoThreeTree("M");
		t.insert("E", "J", "M", "left");
		t.insert("A", "C", "EJ", "left");
		t.insert("H", "EJ", "mid");
		t.insert("L", "EJ", "right");
		t.insert("R", "M", "right");
		t.insert("P", "R", "left");
		t.insert("S", "X", "R", "right");
		t.convert();
		t.draw();
	}

	public static void main(String[] args) {
		testBasicInsertion();
		testBasicConversion();
		testDraw();
	}
}