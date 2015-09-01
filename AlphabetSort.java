import java.util.Scanner;
/**
*Class that sorts words in a trie according to a passed in alphabet
*@author Ravneet Singh Madaan
*/
public class AlphabetSort {
    /**
    *Main method
    *@param args - file name
    */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) {
            throw new IllegalArgumentException();
        }
        String order = sc.nextLine();
        char c;
        for (int i = 0; i < order.length(); i++) {
            c = order.charAt(i);
            if (order.indexOf(c) != order.lastIndexOf(c)) {
                throw new IllegalArgumentException();
            }
        }
        if (!sc.hasNextLine()) {
            throw new IllegalArgumentException();
        }
        Trie t = new Trie();
        while (sc.hasNextLine()) {
            t.insert(sc.nextLine());
        }
        t.printSorted(order);
    }
}
