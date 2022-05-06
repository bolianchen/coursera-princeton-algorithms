import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int nounCount = nouns.length;
        int maxDist = 0;
        int curDist = 0;
        int indexOfOutcast = -1;
        for (int i=0; i<nounCount; i+=1) {
            for (int j=0; j<nounCount; j+=1) {
                curDist += wordNet.distance(nouns[i], nouns[j]); 
            }
            if (curDist > maxDist) {
                maxDist = curDist;
                indexOfOutcast = i;
            }
            // reset the current distance
            curDist = 0;
        }
        return nouns[indexOfOutcast];
    }

    // see test client below
    public static void main(String[] args) {
	WordNet wordnet = new WordNet(args[0], args[1]);
	Outcast outcast = new Outcast(wordnet);
	for (int t = 2; t < args.length; t++) {
	    In in = new In(args[t]);
	    String[] nouns = in.readAllStrings();
	    StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	}
    }
}
