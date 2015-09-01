import java.util.*;
public class Tester {
	 public static IntList reverseNonDestructive (IntList lst) {
	 	if(lst == null) {
	 		return lst;
	 	}
	 	IntList newTail = reverseNonDestructive(lst.tail);
	 	IntList temp = new IntList (lst.head, null);
	 	newTail.tail = temp;
	 	return newTail;
	 }
	public static void main(String[] args) {

	}
}