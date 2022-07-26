import java.util.Arrays;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int sLength = csa.length();
        //int first;
        for (int i = 0; i < sLength; i += 1) {
            if (csa.index(i) == 0) {
                //first = i;
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
        String input = BinaryStdIn.readString();
        int sLength = input.length();
        char[] t = input.toCharArray();
        char[] f = Arrays.copyOf(t, sLength);
        Arrays.sort(f);
        int[] next = new int[sLength];
        for (int i = 0; i < sLength; i += 1) {
            for (int j = 0; j < sLength; j += 1) {
                if (f[i] == t[j]) {
                    next[i] = j;
                    if (i + 1 < sLength && f[i] == f[i+1]) {
                        i += 1;
                    }
                }
            }
        }
        int i = first;
        while (true) {
            BinaryStdOut.write(f[i]);
            i = next[i];
            if (i == first) {
                break;
            }
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
