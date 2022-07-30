import edu.princeton.cs.algs4.In;

public class CircularSuffixArray {
    private final int R = 256;
    private final String originalString;
    private final int stringLength;
    private final int[] indexAtOriginalSuffix;
    private final int CUTOFF = 15;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        originalString = s;
        stringLength = s.length();

        int[] suffixes = new int[stringLength];
        for (int i = 0; i < stringLength; i += 1) {
            suffixes[i] = i;
        }

        // sorting suffixes
        sortSuffixes(suffixes);

        indexAtOriginalSuffix = new int[stringLength];
        for (int i = 0; i < stringLength; i += 1) {
            indexAtOriginalSuffix[i] = suffixes[i];
        }
    }

    // MSD radix sort
    private void sortSuffixes(int[] suffixes) {
        int[] aux = new int[stringLength];
        sortSuffixesHelper(suffixes, 0, stringLength - 1, 0, aux);
    }

    private void sortSuffixesHelper(int[] suffixes, int lo,
            int hi, int d, int[] aux) {

        // switch to insertion sort when the subarray to sort is short
        if (hi <= lo + CUTOFF) {
            insertionSort(suffixes, lo, hi, d);
            return;
        }

        // compute frequency counts
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i += 1) {
            count[charAt(suffixes[i], d) + 2] += 1;
        }

        // transform counts to indices
        for (int r = 0; r < R + 1; r += 1) {
            count[r + 1] += count[r];
        }

        // distribute
        for (int i = lo; i <= hi; i += 1) {
            aux[count[charAt(suffixes[i], d) + 1]++] = suffixes[i];
        }

        // copy back
        for (int i = lo; i <= hi; i += 1) {
            suffixes[i] = aux[i - lo];
        }

        // recursively sort for each character
        for (int r = 0; r < R; r += 1) {
            sortSuffixesHelper(suffixes, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
        }
    }

    private void insertionSort(int[] suffixes, int lo, int hi, int d) {
        if (hi <= lo || d == stringLength) {
            return;
        }
        for (int i = lo; i <= hi; i += 1) {
            for (int j = i; j > lo && less(suffixes[j], suffixes[j-1], d); j -= 1) {
                exch(suffixes, j, j - 1);
            }
        }
    }

    private boolean less(int suffix1, int suffix2, int d) {
        for (int w = d; w < stringLength; w += 1) {
            if (charAt(suffix1, w) < charAt(suffix2, w)) {
                return true;
            }
            if (charAt(suffix1, w) > charAt(suffix2, w)) {
                return false;
            }
        }
        return false;
    }

    private void exch(int[] suffixes, int i, int j) {
        int temp = suffixes[i];
        suffixes[i] = suffixes[j];
        suffixes[j] = temp;
    }

    private int charAt(int suffix, int d) {
        if (d == stringLength) {
            return -1;
        }
        int index = (suffix + d) % stringLength;
        return originalString.charAt(index);
    }

    // length of s
    public int length() {
        return stringLength;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i > stringLength - 1) {
            throw new IllegalArgumentException();
        }
        return indexAtOriginalSuffix[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        String s = in.readAll();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        for (int i = 0; i < s.length(); i += 1) {
            System.out.println(csa.index(i));
        }
    }

}
