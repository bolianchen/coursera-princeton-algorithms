import java.util.Arrays;
import java.util.HashMap;

public class CircularSuffixArray {
    private int stringLength;
    private int[] indexOfOriginalSuffix;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        stringLength = s.length();
        String[] circularSuffix = new String[stringLength];
        HashMap<String, Integer> suffixToIndex = new HashMap<>();
        for (int i = 0; i < stringLength; i += 1) {
            circularSuffix[i] = s.substring(i, stringLength) + s.substring(0, i);
            suffixToIndex.put(circularSuffix[i], i);
        }

        String[] sortedCircularSuffix = Arrays.copyOf(circularSuffix, stringLength);
        Arrays.sort(sortedCircularSuffix);

        indexOfOriginalSuffix = new int[stringLength];
        for (int i = 0; i < stringLength; i += 1) {
            indexOfOriginalSuffix[i] = suffixToIndex.get(sortedCircularSuffix[i]);
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
        String s = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(s);
        System.out.println(csa.index(0));
    }

}
