/******************************************************************************
 *  Remarks
 *  -----------
 *  Add the support to the follows:
 *    - The + operator
 *    - Multiway or
 *    - (.) wildcard
 *
 *  The following features are not supported:
 *    - Metacharacters in the text
 *    - Character classes.
 *
 ******************************************************************************/

import java.util.ArrayList;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;


public class ExtendedNFA { 

    private Digraph graph;     // digraph of epsilon transitions
    private String regexp;     // regular expression
    private final int m;       // number of characters in regular expression

    /**
     * Initializes the NFA from the specified regular expression.
     *
     * @param  regexp the regular expression
     */
    public ExtendedNFA(String regexp) {
        this.regexp = regexp;
        m = regexp.length();
        Stack<Integer> ops = new Stack<Integer>(); 
        graph = new Digraph(m+1); 
        ArrayList<Integer> indicesAfterOr;
        for (int i = 0; i < m; i++) { 
            int lp = i; 
            if (regexp.charAt(i) == '(' || regexp.charAt(i) == '|') 
                ops.push(i); 
            else if (regexp.charAt(i) == ')') {
                int op = ops.pop(); 
                indicesAfterOr = new ArrayList<>();

                // multi-way or operator
                while (regexp.charAt(op) == '|') {
                    int or = op;
                    indicesAfterOr.add(or+1);
                    graph.addEdge(or, i);
                    op = ops.pop();
                }

                if (regexp.charAt(op) == '(') {
                    lp = op;
                } else {
                    assert false;
                }

                for (Integer indexAfterOr: indicesAfterOr) {
                    graph.addEdge(lp, indexAfterOr);
                }
            } 

            // closure operator (uses 1-character lookahead)
            if (i < m-1 && regexp.charAt(i+1) == '*') { 
                graph.addEdge(lp, i+1); 
                graph.addEdge(i+1, lp); 
            } 
            if (i < m-1 && regexp.charAt(i+1) == '+') { 
                graph.addEdge(i+1, lp); 
            } 
            if (regexp.charAt(i) == '(' || regexp.charAt(i) == '*' || regexp.charAt(i) == ')' || regexp.charAt(i) == '+') 
                graph.addEdge(i, i+1);
        }
        if (ops.size() != 0)
            throw new IllegalArgumentException("Invalid regular expression");
    } 

    /**
     * Returns true if the text is matched by the regular expression.
     * 
     * @param  txt the text
     * @return {@code true} if the text is matched by the regular expression,
     *         {@code false} otherwise
     */
    public boolean recognizes(String txt) {
        DirectedDFS dfs = new DirectedDFS(graph, 0);
        Bag<Integer> pc = new Bag<Integer>();
        for (int v = 0; v < graph.V(); v++)
            if (dfs.marked(v)) pc.add(v);

        // Compute possible NFA states for txt[i+1]
        for (int i = 0; i < txt.length(); i++) {
            if (txt.charAt(i) == '*' || txt.charAt(i) == '|' || txt.charAt(i) == '(' || txt.charAt(i) == ')')
                throw new IllegalArgumentException("text contains the metacharacter '" + txt.charAt(i) + "'");

            Bag<Integer> match = new Bag<Integer>();
            for (int v : pc) {
                if (v == m) continue;
                if ((regexp.charAt(v) == txt.charAt(i)) || regexp.charAt(v) == '.')
                    match.add(v+1); 
            }

            // no valid states after matching txt.charAt(i)
            if (match.isEmpty()) return false;

            dfs = new DirectedDFS(graph, match); 
            pc = new Bag<Integer>();
            for (int v = 0; v < graph.V(); v++)
                if (dfs.marked(v)) pc.add(v);


            // optimization if no states reachable
            if (pc.size() == 0) return false;
        }

        // check for accept state
        for (int v : pc)
            if (v == m) return true;
        return false;
    }

    /**
     * Unit tests the {@code NFA} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String regexp = "(" + args[0] + ")";
        String txt = args[1];
        ExtendedNFA nfa = new ExtendedNFA(regexp);
        StdOut.println(nfa.recognizes(txt));
    }

} 
