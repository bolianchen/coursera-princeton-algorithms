import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static int R = 256;

    private static int[] initializeAlphabetToOrder() {
        int[] alphabetToOrder = new int[R];
        for (int i = 0; i < R; i += 1) {
            alphabetToOrder[i] = i;
        }
        return alphabetToOrder;
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int[] alphabetToOrder = initializeAlphabetToOrder();
        char c;
        int orderToFront;
        while (!BinaryStdIn.isEmpty()) {
            c = BinaryStdIn.readChar();
            orderToFront = alphabetToOrder[c];
            for (int j = 0; j < R; j += 1) {
                if (alphabetToOrder[j] < orderToFront) {
                    alphabetToOrder[j] += 1;
                } else if (alphabetToOrder[j] == orderToFront) {
                    alphabetToOrder[j] = 0;
                }
            }
            BinaryStdOut.write(orderToFront, 8);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] alphabetToOrder = initializeAlphabetToOrder();
        while (!BinaryStdIn.isEmpty()) {
            int orderToFront = BinaryStdIn.readInt(8);
            for (int j = 0; j < R; j += 1) {
                if (alphabetToOrder[j] < orderToFront) {
                    alphabetToOrder[j] += 1;
                } else if (alphabetToOrder[j] == orderToFront) {
                    BinaryStdOut.write((char) j);
                    alphabetToOrder[j] = 0;
                }
            }
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        }
        if (args[0].equals("+")) {
            decode();
        }
    }
}
