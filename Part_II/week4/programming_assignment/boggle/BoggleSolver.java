import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
public class BoggleSolver {

    private final CapitalLettersTrieSET dict;
    
	// Initializes the data structure using the given array of strings as the dictionary.
	// (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
	public BoggleSolver(String[] dictionary) {
            dict = new CapitalLettersTrieSET();
            for (String word: dictionary) {
                dict.add(word);
            }
        }

	// Returns the set of all valid words in the given Boggle board, as an Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
            HashSet<String> validWords = new HashSet<>();
            int numRows = board.rows();
            int numCols = board.cols();
            boolean[][] marked = new boolean[numRows][numCols];
            for (int row = 0; row < board.rows(); row += 1) {
                for (int col = 0; col < board.cols(); col += 1) {
                    saveValidWords(row, col, "", board, marked, validWords);
                }
            }
            return validWords;
        }

        private void saveValidWords(int row, int col, String pre,
                BoggleBoard board, boolean[][] marked,
                HashSet<String> validWords) {
            // check the validity of the coordinates
            if (!areCoordinateValid(row, col, board)) {
                return;
            }
            // check if the current tile marked
            if (marked[row][col]) {
                return;
            }

            char c = board.getLetter(row, col);
            if (c == "Q".charAt(0)) {
                pre = pre + "QU";
            } else {
                pre = pre + c;
            }

            if (pre.length() < 3) {
            } else if (dict.contains(pre)) {
                validWords.add(pre);
            } else {
                // check if there are words in the dictionary with pre as a prefix
                Iterable<String> wordsWithPreAsPrefix = dict.keysWithPrefix(pre);
                int countCandidWords = 0;
                for (String word: wordsWithPreAsPrefix) {
                    countCandidWords += 1;
                }
                if (countCandidWords == 0) {
                    return;
                }
            }

            // copy marked to make it immutable and mark the current tile
            boolean[][] copyMarked = new boolean[marked.length][];
            for (int i = 0; i < marked.length; i++) {
                copyMarked[i] = marked[i].clone();
            }
            copyMarked[row][col] = true;

            // do search by radiating from the current tile
            for (int i = -1; i <= 1; i += 1) {
                for (int j = -1; j <= 1; j += 1) {
                    // ignore (i, j) == (0, 0)
                    saveValidWords(row + i, col + j, pre, board, copyMarked,
                            validWords);
                }
            }
        }

        private boolean areCoordinateValid(int row, int col, BoggleBoard board) {
            if (row < 0 || row >= board.rows() || col < 0 || col >= board.cols()) {
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
            //BoggleBoard board = new BoggleBoard(args[1]);
            Stopwatch timer = new Stopwatch();
            String s = "abc";
            char c = s.charAt(0);
            System.out.println(c + 10);
            int count = 0;
            for (int i = 0; i < 100; i += 1) {
                BoggleBoard board = new BoggleBoard(4, 4);
                int score = 0;
                for (String word : solver.getAllValidWords(board)) {
                    StdOut.println(word);
                    score += solver.scoreOf(word);
                }
                StdOut.println("Score = " + score);
                count += 1;
                if (timer.elapsedTime() > 1.0) {
                    break;
                }
            }
            StdOut.println("Solved = " + count);
        }
}
