import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.TreeMap;
/**
 * Implements autocomplete on prefixes for a given dictionary of terms and weights.
 *@author Ravneet Singh Madaan
 */
public class Autocomplete {
    /**
     * Initializes required data structures from parallel arrays.
     * @param terms Array of terms.
     * @param weights Array of weights.
     */
    TST t;
    /**
     * Constructor
     * @param terms - list of terms to insert into the trie
     * @param weights - list of weights to insert inot the trie
     */
    public Autocomplete(String[] terms, double[] weights) {
        t = new TST();
        if (terms.length != weights.length) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < terms.length; i++) {
            if (t.contains(terms[i])) {
                throw new IllegalArgumentException();
            }
            if (weights[i] < 0) { 
                throw new IllegalArgumentException();
            }

            t.put(terms[i], weights[i]);
        }
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return 0.0
     * @param term - term whose weight needs to be found
     * @return - weight of the term
     */
    public double weightOf(String term) {
        return t.findWeight(term);
    }

    /**
     * Return the top match for given prefix, or null if there is no matching term.
     * @param prefix Input prefix to match against.
     * @return Best (highest weight) matching string in the dictionary.
     */
    public String topMatch(String prefix) {
        Iterable<String> i = topMatches(prefix, 1);
        String topMatch =  "";
        for (String s : i) {
            topMatch = s;
        }
        return topMatch;
    }

    /**
     * Returns the top k matching terms (in descending order of weight) as an iterable.
     * If there are less than k matches, return all the matching terms.
     * @param k - maximum size of the list of words
     * @param fringe - Priority Queue containing subtries which need to be explored
     * @param weightedWords - Map from weights to words
     * @param firstNode - tells us whether we're dealing with the start of the prefix
     * @param size - number of words in the treemap containing words
     * @return LinkedList of top k matches
     */
    public Iterable<String> topMatchesHelper(int k, PriorityQueue<Node> fringe, 
        TreeMap<Double, TreeSet<String>> weightedWords, boolean firstNode, int size) {
        if (fringe.size() == 0 
            || (size >= k && fringe.peek().max_weight <= weightedWords.firstKey())) {
            LinkedList<String> a = new LinkedList<String>();
            for (Double weight : weightedWords.descendingKeySet()) {
                a.addAll(weightedWords.get(weight));
            }
            return a;
        }
        Node currentNode = fringe.remove();
        if (currentNode.weight != null) {
            String s = currentNode.prefix + currentNode.c;
            if (size < k || (size >= k && currentNode.weight > weightedWords.firstKey())) {
                if (weightedWords.containsKey(currentNode.weight)) {
                    weightedWords.get(currentNode.weight).add(s);
                } else {
                    TreeSet<String> a = new TreeSet<String>();
                    a.add(s);
                    weightedWords.put(currentNode.weight, a);
                }
                size++;
            }
            if (size > k && currentNode.weight > weightedWords.firstKey()) {
                weightedWords.get(weightedWords.firstKey()).pollLast();
                if (weightedWords.get(weightedWords.firstKey()).size() == 0) {
                    weightedWords.remove(weightedWords.firstKey());
                }
                size--;
            }
        }
        if (!firstNode) {
            if (currentNode.left != null) {
                fringe.add(currentNode.left);
                currentNode.left.prefix = currentNode.prefix;
            }
            if (currentNode.right != null) {
                fringe.add(currentNode.right);
                currentNode.right.prefix = currentNode.prefix;
            }
        }
        if (currentNode.mid != null) {
            fringe.add(currentNode.mid);
            currentNode.mid.prefix = currentNode.prefix + currentNode.c;
        }
        return topMatchesHelper(k, fringe, weightedWords, false, size);
    }
    /**
     * Return the top match for given prefix, or null if there is no matching term.
     * @param prefix 
     * @param k - number of top matches 
     * @return Iterable of Strings containing the top k matches
     */
    public Iterable<String> topMatches(String prefix, int k) {
        if (k < 0) {
            throw new IllegalArgumentException();
        }
        Node x = t.get(prefix);
        if (x == null) {
            return new TreeSet<String>();
        }
        PriorityQueue<Node> fringe = new PriorityQueue<Node>(k, new compareMaxWeights());
        fringe.add(x);
        TreeMap<Double, TreeSet<String>> weightedWords = new TreeMap<Double, TreeSet<String>>();
        boolean firstNode = true;
        if (prefix.length() == 0) {
            firstNode = false;
        }
        if (firstNode) {
            x.prefix = prefix.substring(0, prefix.length() - 1);
        }
        return topMatchesHelper(k, fringe, weightedWords, firstNode, 0);
    }

    /**
     * Returns the highest weighted matches within k edit distance of the word.
     * If the word is in the dictionary, then return an empty list.
     * @param word The word to spell-check
     * @param dist Maximum edit distance to search
     * @param k    Number of results to return 
     * @return Iterable in descending weight order of the matches
     */
    public Iterable<String> spellCheck(String word, int dist, int k) {
        LinkedList<String> results = new LinkedList<String>();  
        /* YOUR CODE HERE; LEAVE BLANK IF NOT PURSUING BONUS */
        return results;
    }
    /**
     * Test client. Reads the data from the file, 
     * then repeatedly reads autocomplete queries from standard input and prints out 
     * the top k matching terms.
     * @param args takes the name of an input file and an integer k as command-line arguments
     */
    public static void main(String[] args) {
        // initialize autocomplete data structure
       /*
        String[] terms = {"Lilyannah","Lilyan","Sap", "lol"};

        double[] weights = {5, 10, 15, 34};
        Autocomplete a = new Autocomplete(terms, weights);
        Iterable<String> topMatches = a.topMatches("",32);
        System.out.println(topMatches);
        for (String s : topMatches) {
            System.out.println(s);
        }
        */
        
        In in = new In(args[0]);
        int N = in.readInt();
        String[] terms = new String[N];
        double[] weights = new double[N];
        for (int i = 0; i < N; i++) {
            weights[i] = in.readDouble();   // read the next weight
            in.readChar();                  // scan past the tab
            terms[i] = in.readLine();       // read the next term
        }
        Autocomplete autocomplete = new Autocomplete(terms, weights);
        // process queries from standard input
        int k = Integer.parseInt(args[1]);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            for (String term : autocomplete.topMatches(prefix, k)) {
                StdOut.printf("%14.1f  %s\n", autocomplete.weightOf(term), term);
            }
        }
    }
}
