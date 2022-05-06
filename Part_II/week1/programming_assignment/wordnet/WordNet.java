import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {

    //BST-based symbol table to store noun - synsetIds pairs
    TreeMap<String, List<Integer>> nounToIds;
    // An array to store all the synsets
    ArrayList<String> synsetsList;
    // a Digraph representing the hypernym relationships
    Digraph synsetIdsGraph;
    // a SAP object to find the shortest common ancestor
    // and the length of the shortest ancestral path
    SAP sapOfWordNet;
    // number of synsets
    int synsetCount;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){

        // initialize the data structures as empty
        nounToIds = new TreeMap<>();
        synsetsList = new ArrayList<>();

        // build nounToIds and synsetsList
        In synsetsFileHandler = new In(synsets);
        String[] linesOfSynsets = synsetsFileHandler.readAllLines();
        synsetCount = linesOfSynsets.length;

        for (int i=0; i<synsetCount; i+=1) {
            String[] synsetInfo = linesOfSynsets[i].split(",");
            int id = Integer.parseInt(synsetInfo[0]);
            String synset = synsetInfo[1];
            synsetsList.add(synset);
            String[] nouns = synset.split(" ");
            for (int j=0; j<nouns.length; j+=1) {
                if (!nounToIds.containsKey(nouns[j])) {
                    nounToIds.put(nouns[j], new ArrayList<Integer>());
                }
                nounToIds.get(nouns[j]).add(i);
            }
        }

        // build synsetIdsGraph
        In hypernymsFileHandler = new In(hypernyms);
        String[] linesOfHypernyms = hypernymsFileHandler.readAllLines();
        synsetIdsGraph = new Digraph(synsetCount);
        for (int i=0; i<linesOfHypernyms.length; i+=1) {
            String[] idsOfHypernyms = linesOfHypernyms[i].split(",");
            for (int j=1; j<idsOfHypernyms.length; j+=1) {
                int hyponymId = Integer.parseInt(idsOfHypernyms[0]);
                int hypernymId = Integer.parseInt(idsOfHypernyms[j]);
                synsetIdsGraph.addEdge(hyponymId, hypernymId);
            }
        }

        if (!isRootedDAG(synsetIdsGraph)) {
            throw new IllegalArgumentException(
                    "input files do not correspond to a rooted DAG");
        }
        
        sapOfWordNet = new SAP(synsetIdsGraph);
    }

    private boolean isRootedDAG(Digraph graph) {
        DirectedCycle cyclesFinder = new DirectedCycle(graph);
        if (cyclesFinder.hasCycle()) {
            return false;
        }

        boolean iIsRoot;
        for (int i=0; i<graph.V(); i+=1) {
            // only do further check a vertex having 0 outdegree
            if (graph.outdegree(i) == 0) {
                iIsRoot = true;
                Digraph reversedGraph = graph.reverse();
                BreadthFirstDirectedPaths bfsReversedGraph =
                    new BreadthFirstDirectedPaths(reversedGraph, i);
                // vertex i is not a root if it has no paths to any verteices
                for (int j=0; j<reversedGraph.V(); j+=1) {
                    if (!bfsReversedGraph.hasPathTo(j)) {
                        iIsRoot = false;
                        break;
                    }
                }
                if (iIsRoot) {
                    return true;
                }
            }
        }
        return false;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word==null) {
            throw new IllegalArgumentException("word argument can not be null");
        }
        return nounToIds.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA==null || nounB==null) {
            throw new IllegalArgumentException(
                    "nounA or nounB argument can not be null");
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException(
                    "nounA or nounB is not a WordNet noun");
        }
        List<Integer> idsOfNounA = nounToIds.get(nounA);
        List<Integer> idsOfNounB = nounToIds.get(nounB);
        return sapOfWordNet.length(idsOfNounA, idsOfNounB);
    }


    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA==null || nounB==null) {
            throw new IllegalArgumentException(
                    "the nounA or nounB argument can not be null");
        }
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException(
                    "nounA or nounB is not a WordNet noun");
        }
        List<Integer> idsOfNounA = nounToIds.get(nounA);
        List<Integer> idsOfNounB = nounToIds.get(nounB);
        int ancestorId = sapOfWordNet.ancestor(idsOfNounA, idsOfNounB);
        return synsetsList.get(ancestorId);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wt = new WordNet(args[0], args[1]);
        int dist = wt.distance("miracle", "event");
        System.out.println(dist);
        String ancestor = wt.sap("miracle", "event");
        System.out.println(ancestor);
    }
}
