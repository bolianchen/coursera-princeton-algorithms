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
                // output first
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

        // initialize char and int arrays that would respectively be used
        // to store characters at first column and the corresponding next index
        // of the sorted suffixes
        char[] firstColumn = new char[sLength];
        int[] next = new int[sLength];
        for (int i = 0; i < sLength; i += 1) {
            firstColumn[i] = lastColumnOfSortedSuffix.charAt(i);
            next[i] = i;
        }
        // use key indexed counting to complete the two arrays
        keyIndexedCounting(firstColumn, next);

        int indexToCheckNext = first;
        for (int k = 0; k < sLength; k += 1) {
            BinaryStdOut.write(firstColumn[indexToCheckNext]);
            indexToCheckNext = next[indexToCheckNext];
        }
        BinaryStdOut.close();
    }

    private static void keyIndexedCounting(char[] a, int[] indices) {
        int n = a.length;
        int R = 256;
        char[] aux = new char[n];
        int[] auxIndices = new int[n];
        int[] count = new int[R + 1];

        // compute frequency counts
        for (int i = 0; i < n; i += 1) {
            count[a[i] + 1] += 1;
        }

        // compute cumulates
        for (int r = 0; r < R; r += 1) {
            count[r + 1] += count[r];
        }

        // move data
        for (int i = 0; i < n; i += 1) {
            aux[count[a[i]]] = a[i];
            auxIndices[count[a[i]]] = indices[i];
            count[a[i]] += 1;
        }
        // copy back
        for (int i = 0; i < n; i += 1) {
            a[i] = aux[i];
            indices[i] = auxIndices[i];
        }
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
