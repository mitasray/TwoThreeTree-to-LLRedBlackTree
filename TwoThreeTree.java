// package TwoThreeTree_to_LLRedBlackTree;

import java.util.Scanner;

/**
 * @author Mitas Ray
 */
public class TwoThreeTree {

	// If there is only one element in the node, then it will be 
	// represented as little.
	String little;
	String big;
	TwoThreeTree left;
	TwoThreeTree mid;
	TwoThreeTree right;
	LLRedBlackTree llrbt;

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

	public static void userInput() {
		Scanner s = new Scanner(System.in);
		String head = "needs to be overriden by one character string";
		String input = "";
		while (head.length() != 1 && head.length() != 2) {
			System.out.print("head of 2-3 tree? ");
			head = s.next();
		}
		TwoThreeTree t;
		if (head.length() == 1) {
			t = new TwoThreeTree(head);
		} else {
			t = new TwoThreeTree(Character.toString(head.charAt(0)), Character.toString(head.charAt(1)));
		}
		while (!input.equals("q") && !input.equals("d")) {
			System.out.print("(i)nsert, (d)raw, (q)uit? ");
			input = s.next();
			if (input.equals("i")) {
				String value = "needs to be overriden by value";
				String parent = "needs to be overriden by parent";
				String direction = "needs to be overriden by direction";
				while (value.length() != 1 && value.length() != 2) {
					System.out.print("\tvalue? ");
					value = s.next();
				}
				while (parent.length() != 1 && parent.length() != 2) {
					System.out.print("\tparent? ");
					parent = s.next();
				}
				while (!direction.equals("l") && !direction.equals("m") && !direction.equals("r")) {
					System.out.print("\tdirection: (l)eft, (m)id, (r)ight? ");
					direction = s.next();
				}
				if (direction.equals("l")) direction = "left";
				if (direction.equals("m")) direction = "mid";
				if (direction.equals("r")) direction = "right";
				if (value.length() == 1) {
					t.insert(value, parent, direction);
				} else {
					t.insert(Character.toString(value.charAt(0)), Character.toString(value.charAt(1)), parent, direction);
				}
			} else if (input.equals("d")) {
				System.out.print("\tWARNING: draw will terminate the program after drawing. Proceed: (y)es, (n)o? ");
				String yesOrNo = "yesOrNo";
				yesOrNo = s.next();
				if (yesOrNo.equals("y")) {
					t.convert();
					TreeDrawer td = new TreeDrawer(t.llrbt);
					td.draw();
				}
			}
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
		TreeDrawer td = new TreeDrawer(t.llrbt);
		td.draw();
	}

	public static void main(String[] args) {
		// testBasicInsertion();
		// testBasicConversion();
		// testDraw();
		userInput();
	}
}