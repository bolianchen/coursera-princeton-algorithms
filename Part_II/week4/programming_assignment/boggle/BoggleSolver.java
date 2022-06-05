import java.util.HashSet;
import java.util.HashMap;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.Queue;
public class BoggleSolver {

    private final CapitalLettersTrieSET dict;
    
    // Initializes the data structure using the given array of strings as the dictionary.
    // Assume each word in the dictionary contains only the uppercase letters A through Z.
    public BoggleSolver(String[] dictionary) {
        dict = new CapitalLettersTrieSET();
        for (String word: dictionary) {
            dict.add(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        HashSet<String> validWords = new HashSet<>();
        Node prefixNode = null;
        int numRows = board.rows();
        int numCols = board.cols();
        boolean[][] marked = new boolean[numRows][numCols];
        for (int row = 0; row < numRows; row += 1) {
            for (int col = 0; col < numCols; col += 1) {
                marked[row][col] = true;
                saveValidWords(row, col, "", board, marked, validWords,
                        prefixNode);
                marked[row][col] = false;
            }
        }
        return validWords;
    }

    private void saveValidWords(int row, int col, String pre,
            BoggleBoard board, boolean[][] marked, HashSet<String> validWords, 
            Node prefixNode) {

        int numRows = board.rows();
        int numCols = board.cols();

        char c = board.getLetter(row, col);
        if (c == "Q".charAt(0)) {
            pre = pre + "QU";
        } else {
            pre = pre + c;
        }

        if (pre.length() >= 3 && dict.contains(pre)) {
            validWords.add(pre);
        }

        if (prefixNode == null) {
            prefixNode = dict.keysWithPrefix(pre);
        } else {
            if (c == "Q".charAt(0)) {
                prefixNode = prefixNode.getNext("Q".charAt(0));
                if (prefixNode != null) {
                    prefixNode = prefixNode.getNext("U".charAt(0));
                }
            } else {
                prefixNode = prefixNode.getNext(c);
            }
        }

        if (prefixNode == null) {
            return;
        }
        
        // do search by radiating from the current tile
        for (int i = -1; i <= 1; i += 1) {
            for (int j = -1; j <= 1; j += 1) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (!areCoordinateValid(row + i, col + j, numRows, numCols)) {
                    continue;
                }
                if (marked[row + i][col + j]) {
                    continue;
                }
                marked[row + i][col + j] = true;
                saveValidWords(row + i, col + j, pre, board, marked,
                        validWords, prefixNode);
                marked[row + i][col + j] = false;
            }
        }
    }

    private boolean areCoordinateValid(int row, int col, int numRows, int numCols) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
            return false;
        }
        return true;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int wordLength = word.length();
        if (!dict.contains(word) || wordLength < 3) {
            return 0;
        } else if (wordLength <= 4) {
            return 1;
        } else if (wordLength == 5) {
            return 2;
        } else if (wordLength == 6) {
            return 3;
        } else if (wordLength == 7) {
            return 5;
        } else {
            return 11;
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        //
        // BoggleBoard board = new BoggleBoard(args[1]);
        // int score = 0;
        // for (String word : solver.getAllValidWords(board)) {
        //     StdOut.println(word);
        //     score += solver.scoreOf(word);
        // }
        // StdOut.println("Score = " + score);
        //

        Stopwatch timer = new Stopwatch();
        int count = 0;
        while (timer.elapsedTime() < 5.0) {
            BoggleBoard board = new BoggleBoard(4, 4);
            int score = 0;
            for (String word : solver.getAllValidWords(board)) {
                score += solver.scoreOf(word);
            }
            count += 1;
        }
        StdOut.println("Solved = " + count);
    }
}
