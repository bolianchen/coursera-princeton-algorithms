import java.util.Arrays;
import java.util.ArrayList;
import java.util.TreeMap;
import java.lang.StringBuilder;
import edu.princeton.cs.algs4.In;

public class CircularSuffixArray {
    private int stringLength;
    private int[] indexAtOriginalSuffix;
    private String originalString;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        originalString = s;
        stringLength = s.length();
        CircularSuffix suffix;
        ArrayList<Integer> indices;
        TreeMap<CircularSuffix, ArrayList<Integer>> suffixToIndex = new TreeMap<>();
        for (int i = 0; i < stringLength; i += 1) {
            suffix = new CircularSuffix(i);
            if (!suffixToIndex.containsKey(suffix)) {
                indices = new ArrayList<Integer>();
                indices.add(i);
                suffixToIndex.put(suffix, indices);
            } else {
                suffixToIndex.get(suffix).add(i);
            }
        }

        indexAtOriginalSuffix = new int[stringLength];
        int i = 0;
        while (suffixToIndex.size() != 0) {
            suffix = suffixToIndex.firstKey();
            indices = suffixToIndex.remove(suffix);
            for (Integer index: indices) {
                indexAtOriginalSuffix[i] = index;
                i += 1;
            }
        }
    }

    /*
     * Inner class for sorting suffixes without duplicate strings
     */
    private class CircularSuffix implements Comparable<CircularSuffix> {
        private String s;
        private int sLength;
        private int firstIndex;

        public CircularSuffix(int i) {
            s = originalString;
            sLength = s.length();
            firstIndex = i;
        }
        public int compareTo(CircularSuffix cs) {
            int otherFirstIndex = cs.firstIndex;
            for (int i = 0; i < sLength; i += 1) {
                if (charAt(i) > cs.charAt(i)) {
                    return 1;
                } else if (charAt(i) < cs.charAt(i)) {
                    return -1;
                }
            }
            return 0;
        }
        public char charAt(int i) {
            return s.charAt((firstIndex + i) % sLength);
        }
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
