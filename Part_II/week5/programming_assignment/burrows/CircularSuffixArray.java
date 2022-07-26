import java.util.Arrays;
import java.util.ArrayList;
import java.util.TreeMap;
import java.lang.StringBuilder;
import edu.princeton.cs.algs4.In;

public class CircularSuffixArray {
    private int stringLength;
    private int[] indexOfOriginalSuffix;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        stringLength = s.length();
        String suffix;
        TreeMap<String, ArrayList<Integer>> suffixToIndex = new TreeMap<>();
        for (int i = 0; i < stringLength; i += 1) {
            suffix = new StringBuilder(s.substring(i, stringLength)).append(s.substring(0, i)).toString();
            if (!suffixToIndex.containsKey(suffix)) {
                suffixToIndex.put(suffix, new ArrayList<Integer>());
            }
            suffixToIndex.get(suffix).add(i);
        }

        indexOfOriginalSuffix = new int[stringLength];
        String key;
        ArrayList<Integer> values;
        int i = 0;
        while (suffixToIndex.size() != 0) {
            key = suffixToIndex.firstKey();
            values = suffixToIndex.remove(key);
            for (Integer v: values) {
                indexOfOriginalSuffix[i] = v;
                i += 1;
            }
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
        return indexOfOriginalSuffix[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        //String s = "ABRACADABRA!";
        String s = in.readAll();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        for (int i = 0; i < s.length(); i += 1) {
            System.out.println(csa.index(i));
        }
    }

}
