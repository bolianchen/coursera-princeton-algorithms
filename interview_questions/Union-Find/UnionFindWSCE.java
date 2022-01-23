/*
 * Union-find data type with specific canonical element
 */

import java.util.*;
import java.lang.*;
import edu.princeton.cs.algs4.StdIn;

public class UnionFindWSCE {

    int[] parent;
    int[] size;
    // for each root, store the largest element in the connected component
    int[] largest;

    // constructor
    // runtime Theta(n)
    public UnionFindWSCE (int n) {
        parent = new int[n];
        size = new int[n];
        largest = new int[n];

        for (int i=0; i<n; i=i+1) {
            parent[i] = i;
            size[i] = 1;
            largest[i] = i;
        }
    }

    // return the root of the element i
    public int root(int i) {
        while (i != this.parent[i]) {
            //TODO: Add path compression
            i = parent[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);
        if (rootP == rootQ) {
            return;
        }
        if (size[rootP] > size[rootQ]) {
            this.parent[rootQ] = rootP;
            this.size[rootP] = this.size[rootP] + this.size[rootQ];
            this.largest[rootP] = Math.max(this.largest[rootP], this.largest[rootQ]);
        } else {
            this.parent[rootP] = rootQ;
            this.size[rootQ] = this.size[rootP] + this.size[rootQ];
            this.largest[rootQ] = Math.max(this.largest[rootP], this.largest[rootQ]);
        }
    }

    // find the largest element in the connected component containing i
    public int find(int i) {
        return i;
    }

    // helper functions
    public void printParentArr() {
        System.out.println("Parent: " + Arrays.toString(this.parent));
    }
    public void printSizeArr() {
        System.out.println("Size: " + Arrays.toString(this.size));
    }
    public void printLargestArr() {
        System.out.println("Largest " + Arrays.toString(this.largest));
    }

    public static void main(String[] args) {

        int n = Integer.valueOf(StdIn.readString());
        UnionFindWSCE uf = new UnionFindWSCE(n);

        while (!StdIn.isEmpty()) {
            int d = Integer.valueOf(StdIn.readString());
            uf.union(d, d+1);
            uf.printParentArr();
            uf.printSizeArr();
            uf.printLargestArr();
        }
    }
}
