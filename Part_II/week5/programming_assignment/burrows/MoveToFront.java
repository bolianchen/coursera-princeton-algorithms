import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static int R = 256;

    private static char[] initializeAlphabet() {
        char[] alphabet = new char[R];
        for (int i = 0; i < R; i += 1) {
            alphabet[i] = (char) i;
        }
        return alphabet;
    }

    private static void updateAlphabet(char[] alphabet, char c, int orderToFront) {
        for (int i = orderToFront - 1; i >= 0; i -= 1) {
            alphabet[i+1] = alphabet[i];
        }
        alphabet[0] = c;
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] alphabet = initializeAlphabet();
        char c;
        int orderToFront = -1;
        while (!BinaryStdIn.isEmpty()) {
            c = BinaryStdIn.readChar();
            for (int i = 0; i < R; i += 1) {
                if (alphabet[i] == c) {
                    orderToFront = i;
                    break;
                }
            }
            updateAlphabet(alphabet, c, orderToFront);
            BinaryStdOut.write(orderToFront, 8);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] alphabet = initializeAlphabet();
        int orderToFront;
        char c;
        while (!BinaryStdIn.isEmpty()) {
            orderToFront = BinaryStdIn.readInt(8);
            c = alphabet[orderToFront];
            BinaryStdOut.write(c);
            updateAlphabet(alphabet, c, orderToFront);
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
