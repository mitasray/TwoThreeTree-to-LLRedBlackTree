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

	public static void main(String[] args) {
		testBasicInsertion();
		testBasicConversion();
	}
}