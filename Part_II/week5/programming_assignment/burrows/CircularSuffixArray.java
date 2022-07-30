import java.util.Arrays;
import java.util.ArrayList;
import java.util.TreeMap;
import java.lang.StringBuilder;
import edu.princeton.cs.algs4.In;

public class CircularSuffixArray {
    private int R;
    private String originalString;
    private int stringLength;
    private int[] indexAtOriginalSuffix;
    private int CUTOFF = 0;

    // Inner class for sorting suffixes without duplicate strings
    private class CircularSuffix {
        private String s = originalString;
        private int firstIndex;

        public CircularSuffix(int i) {
            firstIndex = i;
        }

        public int charAt(int i) {
            return s.charAt((firstIndex + i) % s.length());
        }
    }

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        R = 256;
        originalString = s;
        stringLength = s.length();

        CircularSuffix[] suffixes = new CircularSuffix[stringLength];
        for (int i = 0; i < stringLength; i += 1) {
            suffixes[i] = new CircularSuffix(i);
        }

        // sorting suffixes
        sortCircularSuffixes(suffixes);

        indexAtOriginalSuffix = new int[stringLength];
        for (int i = 0; i < stringLength; i += 1) {
            indexAtOriginalSuffix[i] = suffixes[i].firstIndex;
        }
    }

    private void sortCircularSuffixes(CircularSuffix[] css) {
        CircularSuffix[] aux = new CircularSuffix[stringLength];
        sortCircularSuffixesHelper(css, 0, stringLength - 1, 0, aux);
    }

    private void sortCircularSuffixesHelper(CircularSuffix[] css, int lo,
            int hi, int d, CircularSuffix[] auxCss) {
        // CUTOFF

        if (hi <= lo + CUTOFF) {
            //insertionSort(css, lo, hi, d);
            return;
        }

        // compute frequency counts
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i += 1) {
            count[charAt(css[i], d) + 2] += 1;
        }

        // transform counts to indices
        for (int r = 0; r < R + 1 ; r += 1) {
            count[r + 1] += count[r];
        }

        // distribute
        for (int i = lo; i <= hi; i += 1) {
            auxCss[count[charAt(css[i], d) + 1]++] = css[i];
        }

        // copy back
        for (int i = lo; i <= hi; i += 1) {
            css[i] = auxCss[i - lo];
        }

        // recursively sort for each character
        for (int r = 0; r < R; r += 1) {
            sortCircularSuffixesHelper(css, lo + count[r], lo + count[r + 1] - 1, d + 1, auxCss);
        }
    }

    //private void insertionSort(CircularSuffix[] css, int lo, int hi, int d) {
    //    if (hi <= lo || d == stringLength) {
    //        return;
    //    }
    //    int j;
    //    CircularSuffix cs;
    //    for (int i = lo; i <= hi; i += 1) {
    //        for (int j = i; j > lo 
    //        
    //    }
    //}

    private int charAt(CircularSuffix cs, int d) {
        if (d == stringLength) {
            return -1;
        }
        return cs.charAt(d);
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
