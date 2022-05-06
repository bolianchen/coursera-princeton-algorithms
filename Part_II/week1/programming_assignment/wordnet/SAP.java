import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {
    // the data type should use space proportional to E + V
    private Digraph diGraph;
    private int vertexCount;

    // All methods (and the constructor) should take time
    // at most proportional to E + V in the worst case
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G==null) {
            throw new IllegalArgumentException("input directed graph is null");
        }
        diGraph = G;
        vertexCount = G.V();
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return sapHelper(v, w)[0]; 
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return sapHelper(v, w)[1];
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return sapHelper(v, w)[0]; 
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return sapHelper(v, w)[1];
    }

    private int[] sapHelper(int v, int w) {
        if ((v<0 || v >= diGraph.V()) || (w<0 || w >= diGraph.V())) {
            throw new IllegalArgumentException("v or w argument is out of range");
        }
        // construct two bfs instances
        BreadthFirstDirectedPaths bfsV
            = new BreadthFirstDirectedPaths(diGraph, v);
        BreadthFirstDirectedPaths bfsW
            = new BreadthFirstDirectedPaths(diGraph, w);

        return computeSapByBFSs(bfsV, bfsW);
    }

    private int[] sapHelper(Iterable<Integer> v, Iterable<Integer> w) {
        if (v==null || w==null) {
            throw new IllegalArgumentException(
                    "v or w argument is null");
        }

        for (Integer i:v) {
            if (i<0 || i>= diGraph.V()) {
                throw new IllegalArgumentException(
                        "a integer in v is out of range");
            }
            if (i==null){
                throw new IllegalArgumentException(
                        "v contains a null item");
            }
        }
        for (Integer i:w) {
            if (i<0 || i>= diGraph.V()) {
                throw new IllegalArgumentException(
                        "a integer in w is out of range");
            }
            if (i==null){
                throw new IllegalArgumentException(
                        "w contains a null item");
            }
        }

        // construct two bfs instances
        BreadthFirstDirectedPaths bfsV
            = new BreadthFirstDirectedPaths(diGraph, v);
        BreadthFirstDirectedPaths bfsW
            = new BreadthFirstDirectedPaths(diGraph, w);

        return computeSapByBFSs(bfsV, bfsW);
    }

    private int[] computeSapByBFSs(BreadthFirstDirectedPaths bfs1,
            BreadthFirstDirectedPaths bfs2) {

        int minDistBetweenVertices = -1;
        int sumDistToAncestor;
        int ancestorId = -1;
        for (int i=0; i<this.vertexCount; i+=1) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                sumDistToAncestor = bfs1.distTo(i) + bfs2.distTo(i);
                if (minDistBetweenVertices == -1
                        || sumDistToAncestor < minDistBetweenVertices) {
                    minDistBetweenVertices = sumDistToAncestor;
                    ancestorId = i;
                }
            }
        }
        return new int[]{minDistBetweenVertices, ancestorId};
    }

    // do unit testing of this class
    public static void main(String[] args) {
        System.out.println("Unit Testing for SAP");
        In in = new In(args[0]);
        Digraph g = new Digraph(in);
        SAP sap = new SAP(g);
        // digraph25.txt
        //Integer[] v = {13, 23, 24};
        //Integer[] w = {6, 16, 17};
        // toy_graph1.txt
        //int v = 3;
        //int w = 10;
        // toy_graph1.txt
        int v = 1;
        int w = 5;

        System.out.println("length of the shortest ancestral path = "
                + sap.length(Arrays.asList(v), Arrays.asList(w)));
        System.out.println("shortest common ancestor = "
                + sap.ancestor(Arrays.asList(v), Arrays.asList(w)));
    }
}
