public class compareWeights implements Comparator<Node> {
	public int compare(Node n1, Node n2) {
		return n2.weight - n1.weight;
	}
	public boolean equals(Node n1, Node n2) {
		return n1.weight == n2.weight;
	}
}