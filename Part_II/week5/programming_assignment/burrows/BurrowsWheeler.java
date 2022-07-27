import java.util.Arrays;
import java.util.ArrayList;
import java.util.TreeMap;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int sLength = csa.length();
        for (int i = 0; i < sLength; i += 1) {
            if (csa.index(i) == 0) {
                //output first
                BinaryStdOut.write(i);
                break;
            }
        }
        for (int i = 0; i < sLength; i += 1) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(s.charAt(sLength - 1));
            } else {
                BinaryStdOut.write(s.charAt(csa.index(i) - 1));
            }
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String lastColumnOfSortedSuffix = BinaryStdIn.readString();
        int sLength = lastColumnOfSortedSuffix.length();
        char[] lastColumnArray = lastColumnOfSortedSuffix.toCharArray();
        TreeMap<Character, ArrayList<Integer>> tToIndex = new TreeMap<>();
        for (int i = 0; i < sLength; i += 1) {
            if (!tToIndex.containsKey(lastColumnArray[i])) {
                tToIndex.put(lastColumnArray[i], new ArrayList<Integer>());
            }
            tToIndex.get(lastColumnArray[i]).add(i);
        }

        char[] firstColumnArray = new char[sLength];
        int[] next = new int[sLength];
        char key;
        ArrayList<Integer> values;

        int j = 0;
        while (tToIndex.size() != 0) {
            key = tToIndex.firstKey();
            values = tToIndex.remove(key);
            for (Integer v: values) {
                firstColumnArray[j] = key;
                next[j] = v;
                j += 1;
            }
        }

        int indexToCheckNext = first;
        for (int k = 0; k < sLength; k += 1) {
            BinaryStdOut.write(firstColumnArray[indexToCheckNext]);
            indexToCheckNext = next[indexToCheckNext];
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        }
        if (args[0].equals("+")) {
            inverseTransform();
        }
    }
}
