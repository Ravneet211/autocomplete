public class TST {
	Node root;
	public boolean contains (String term) {
		if(get(term) == null || get(term).weight == null) {
			return false;
		}
		return true;
	}
	public void put(String term , double weight) {
		root = put(root, term, weight, 0);
	}
	private Node put(Node x, String term, double weight, int i) {
		char c = term.charAt(i);
		if(x == null) {
			x = new Node();
			x.c = c;
		}
		if(x.max_weight == null || x.max_weight < weight) {
			x.max_weight = weight;
		}
		if(c < x.c) {
			x.left = put(x.left, term, weight, i);
		}
		else if(c > x.c) {
			x.right = put(x.right, term, weight, i);
		}
		else if(i < term.length() - 1 ) {
			x.mid = put(x.mid, term , weight, i + 1);
		}
		else {
			x.weight = weight;
		}
		return x;
	}
	public double findWeight(String term) {
		if(term == null) {
			throw new NullPointerException();
		}
		Node x = get(root, term , 0);
		if (x == null || x.weight == null) {
			return 0.0;
		}
		else {
			return x.weight;
		}
	}
	private Node get(Node x, String term, int i) {
		if (term == null) {
			throw new NullPointerException();
		}
		if (term.length() == 0) {
			return root;
		}
		if(x == null) {
			return null;
		}
		char c = term.charAt(i);
		if (c < x.c) {
			return get(x.left, term, i);
		}
		else if (c > x.c) {
			return get(x.right, term, i);
		}
		else if(i < term.length() - 1) {
			return get(x.mid, term, i+1);
		}
		else {
			return x;
		}
	}
	public Node get(String prefix) {
		return get(root, prefix, 0);
	}
	
	public static void main (String[] args) {
		TST t = new TST();
		t.put("smog", 5);
		t.put("spit", 15);
		t.put("spite", 20);
		t.put("spy", 7);
		t.put("buck", 10);
		System.out.println(t.findWeight("smog"));
		System.out.println(t.findWeight("spit"));
		System.out.println(t.findWeight("spite"));
		System.out.println(t.findWeight("spy"));
		System.out.println(t.findWeight("buck"));
	}
}