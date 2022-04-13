import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedMaxPQ<Key> implements Iterable<Key> {
    private Key[] pq;
    private int n;

    public RandomizedMaxPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("priority queue underflow");
        }
        return pq[1];
    }

    public void insert(Key x) {
        assert n < pq.length-1;
        pq[++n] = x;
        swim(n);
    }

    public Key delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("priority queue underflow");
        }
        Key max = pq[1];
        exch(1, n--);
        sink(1);
        pq[n+1] = null;
        return max;
    }

    public Key sample() {
        int s = StdRandom.uniform(1, n+1);
        return pq[s];
    }

    public Key delSample() {
        if (isEmpty()) {
            throw new NoSuchElementException("priority queue underflow");
        }
        int s = StdRandom.uniform(1, n+1);
        Key sKey = pq[s];
        exch(s, n--);
        swim(s);
        sink(s);
        pq[n+1] = null;
        return sKey;
    }

    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {
        private RandomizedMaxPQ<Key> copy;
        public HeapIterator() {
            copy = new RandomizedMaxPQ<Key>(size());
            for (int i = 1; i <= n; i++) {
                copy.insert(pq[i]);
            }
        }

        public boolean hasNext() {
            return !copy.isEmpty();
        }

        public Key next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy.delMax();
        }
    }

    /* *************************
     * Helper functions
     * *************************/
    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(j, j+1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    public void printPQ() {
        Iterator<Key> iter = iterator();
        while (iter.hasNext()) {
            System.out.print(iter.next() + " ");
        }
        System.out.println("");
    }

    // type "-" to delete max; "--" to delete a random element
    public static void main(String[] args) {
        RandomizedMaxPQ<String> pq = new RandomizedMaxPQ<String>(10);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!(item.equals("-") || item.equals("--"))) {
                pq.insert(item);
                System.out.println("Added " + item);
                System.out.print("Elements in the current priority queue:");
                pq.printPQ();
            } else if (!pq.isEmpty() && item.equals("-")) {
                System.out.print("Deleted max:");
                System.out.println(pq.delMax() + " ");
                System.out.print("Elements in the current priority queue:");
                pq.printPQ();
            } else if (!pq.isEmpty() && item.equals("--")) {
                System.out.print("Deleted random:");
                System.out.println(pq.delSample() + " ");
                System.out.print("Elements in the current priority queue:");
                pq.printPQ();
            }
        }
        StdOut.println("(" + pq.size() + " left on pq)");
    }
}
