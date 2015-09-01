import java.util.Map;
import java.util.HashMap;
/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author Ravneet Singh Madaan
 */
public class Trie {
    Node root = new Node();
    /**
    * Node class which stores child nodes and a boolean variable to mark the termination of a word.
    */
    private class Node {
        boolean exists;
        Map<Character, Node> m;
        /**
        *  Node constructor.
        */
        public Node() {
            exists = false;
            m = new HashMap<Character, Node>();
        }
        /**
        * helper method to insert a string into a trie
        *@param s - string to isnert
        *@param i - character to insert
        */
        public void insert(String s, int i) {
            if (m.containsKey(s.charAt(i))) {
                if (i == s.length() - 1) {
                    m.get(s.charAt(i)).exists = true;
                    return;
                }
                m.get(s.charAt(i)).insert(s, i + 1);
            } else {
                if (i == s.length() - 1) {
                    m.put(s.charAt(i), new Node());
                    m.get(s.charAt(i)).exists = true;
                } else {
                    Node n = new Node();
                    n.insert(s, i + 1);
                    m.put(s.charAt(i), n);
                }
            }
            
        }
        /**
        * helper method to find string in a trie
        *@return whether the word exists in the trie or not
        *@param s - string to serach for
        *@param isFullWord - indicates the word we need to search for
        *@param i - letter the method is currently examining
        */
        public boolean find(String s, boolean isFullWord, int i) {
            if (m.containsKey(s.charAt(i))) {
                if (i == s.length() - 1) {
                    if (!isFullWord || m.get(s.charAt(i)).exists) {
                        return true;
                    }
                    return false;
                }
                return m.get(s.charAt(i)).find(s, isFullWord, i + 1);
            }
            return false;
        }
        /** 
        *Prints all the strings starting with the prefix in sorted order
        *@param order - the alphabet passed in
        *@param prefix - prefix for the sorted words
        */ 
        public void printSorted(String order, String prefix) {
            char c;
            for (int i = 0; i < order.length(); i++) {
                c = order.charAt(i);
                if (m.containsKey(c)) {
                    if (m.get(c).exists) {
                        System.out.println(prefix + c);
                    }
                    m.get(c).printSorted(order, prefix + c);
                }
            }
        }
    }
    /**
    *calls the prinSortedHelper function
    *@param order - alphabet passed in
    */
    public void printSorted(String order) {
        root.printSorted(order, "");
    }

    /**
    * method to find string in a trie
    *@return whether the string is contained in the try
    *@param s - string to find
    *@param isFullWord 
    */
    public boolean find(String s, boolean isFullWord) {
        return root.find(s, isFullWord, 0);
    }
    /**
    * method to insert a string into a trie
    *@param s - string to insert
    */
    public void insert(String s) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        root.insert(s, 0);
    }
    /**
    * main method
    *@param args - user input
    */
    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("hello");
        t.insert("hey");
        t.insert("goodbye");
        System.out.println(t.find("hell", false));
        System.out.println(t.find("hello", true));
        System.out.println(t.find("good", false));
        System.out.println(t.find("bye", false));
        System.out.println(t.find("heyy", false));
        System.out.println(t.find("hell", true)); 
        t.printSorted("abcdefghijklmnopqrstuvwxyz");  

    }
}
