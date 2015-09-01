public class Node {
	char c;
	Node left, right, mid;
	Double weight;
	Double max_weight;
	String prefix = "";
	public String toString() {
		String s = "";
		s += c;
		return s + " (" + max_weight.toString() +")";
	}
}

		