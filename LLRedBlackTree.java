// package TwoThreeTree_to_LLRedBlackTree;

/**
 * @author Mitas Ray
 */
public class LLRedBlackTree {

	String value;
	LLRedBlackTree left;
	LLRedBlackTree right;
	boolean upperLinkColor; // true = black, false = red.

	public LLRedBlackTree(String value, LLRedBlackTree left, LLRedBlackTree right, boolean upperLinkColor) {
		this.value = value;
		this.left = left;
		this.right = right;
		this.upperLinkColor = upperLinkColor;
	}

	public LLRedBlackTree(String value) {
		this.value = value;
		left = null;
		right = null;
		upperLinkColor = true;
	}

	public LLRedBlackTree(String value, boolean upperLinkColor) {
		this.value = value;
		left = null;
		right = null;
		this.upperLinkColor = upperLinkColor;
	}

	public static void main(String[] args) {
		
	}
}