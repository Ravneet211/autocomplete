import java.util.Comparator;
public class compareMaxWeights implements Comparator<Node> {
	public int compare(Node n1, Node n2) {
		if(n2.max_weight > n1.max_weight) {
			return 1;
		}
		else if(n1.max_weight == n2.max_weight) {
			return 0;
		}
		else {
			return -1;
		}
		
	}
	public boolean equal(Node n1, Node n2) {
		return n1.max_weight == n2.max_weight;
	}
}