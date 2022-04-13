import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ResizingArrayQueue;

/* 
 * Performance requirements: all Board methods should be supported in time proportional to n^2 or better
 */
public class Board {
    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    // runtime ~ n^2
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][];
        // copy all the entries to squares to avoid immutating with the client's input
        for (int row = 0; row < n; row++) {
            this.tiles[row] = tiles[row].clone();
        }
    }
                                           
    // string representation of this board
    // runtime ~ n^2
    public String toString() {
        // ensure the String cancatenation take linear time
        StringBuilder repr = new StringBuilder(Integer.toString(n) + "\n");
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                repr.append(tiles[row][col]);
                if (col != n-1) {
                    repr.append(" ");
                }
            }
            // do not add newline to the last row
            if (row != n-1) {
                repr.append("\n");
            }
        }
        return repr.toString();
    }

    // board dimension n
    // runtime is constant
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    // runtime ~ n^2
    public int hamming() {
        int hamDist = 0;
        for (int i = 0; i < n*n - 1; i++) {
            if (tiles[i/n][i % n] != i + 1) {
                hamDist += 1;
            }
        }
        return hamDist;
    }

    // sum of Manhattan distances between tiles and goal
    // runtime ~ n^2
    public int manhattan() {
        int manhatDist = 0;
        for (int i = 0; i <= n*n - 1; i++) {
            int target = tiles[i/n][i % n];
            if (target != 0) {
                int targetGoalRow = (target-1)/n;
                int targetGoalCol = (target-1) % n;
                manhatDist += Math.abs(targetGoalRow - i/n) + Math.abs(targetGoalCol - i % n);
            }
        }
        return manhatDist;
    }

    // is this board the goal board?
    // runtime ~ n^2
    public boolean isGoal() {
        for (int i = 0; i < n*n - 1; i++) {
            if (tiles[i/n][i % n] != i + 1) {
                return false;
            }
        }
        return true;
    }

    // does this board equal y?
    // runtime ~ n^2
    public boolean equals(Object y) {
        // return false when argument is null
        if (y == null) {
            return false;
        }
        if (this.getClass() == y.getClass()) {
            Board other = (Board) y;
            if (other.dimension() != n) {
                return false;
            }
            if (!Arrays.deepEquals(this.tiles, other.tiles)) {
                return false;
            }
            return true;
        }
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ResizingArrayQueue<Board> neighbors = new ResizingArrayQueue<>();
        int[] blankCoords = findBlankCoords();
        int blankRow = blankCoords[0];
        int blankCol = blankCoords[1];

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((i != j) && (i != -j) && validCoords(blankRow+i, blankCol+j)) {
                    exch(blankRow, blankCol, blankRow+i, blankCol+j);
                    neighbors.enqueue(new Board(tiles));
                    exch(blankRow, blankCol, blankRow+i, blankCol+j);
                }
            }
        }
        return neighbors;
    }

    // find the current coordinates of the blank square in the board
    private int[] findBlankCoords() {
        int[] coords = new int[2];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0) {
                    coords[0] = row;
                    coords[1] = col;
                    return coords;
                }
            }
        }
        return coords;
    }

    // check if the coordinates valid
    private boolean validCoords(int row, int col) {
        return (row >= 0 && row < n && col >= 0 && col < n);
    }

    // swap contents between (row1, col1) and (row2, col2)
    // runtime is constant
    private void exch(int row1, int col1, int row2, int col2) {
        int swap = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = swap;
    }

    // a board that is obtained by exchanging any pair of tiles(the blank square is not a tile)
    // a board is solvable iff its twin is not
    public Board twin() {
        int idx1 = 0;
        int idx2 = 1;
        for (int i = 0; i < n*n -1; i++) {
            idx1 = i;
            idx2 = i+1;
            if (tiles[idx1/n][idx1 % n] != 0 && tiles[idx2/n][idx2 % n] != 0) {
                break;
            }
        }
        exch(idx1/n, idx1 % n, idx2/n, idx2 % n);
        Board twinBoard = new Board(tiles);
        exch(idx1/n, idx1 % n, idx2/n, idx2 % n);
        return twinBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int sz = in.readInt();
        int[] elements = in.readAllInts();
        int[][] tiles = new int[sz][sz];
        for (int i = 0; i < elements.length; i++) {
            tiles[i/sz][i % sz] = elements[i];
        }
        Board b = new Board(tiles);

        // Test the toString method
        System.out.println(b);
        // Test the dimension method
        System.out.println("the board dimension is " + b.dimension());
        // Test the isGoal method
        System.out.println("has the board reached the goal? " + b.isGoal());
        // Test the findBlankCoords method
        System.out.println("(row, col) of the current blank tile = " + Arrays.toString(b.findBlankCoords()));
        // Test the exch method
        b.exch(0, 0, 0, 1);
        System.out.println(b);
        b.exch(0, 0, 0, 1);
        // Test the neighbors method
        
        Board other1 = new Board(tiles);
        Board other2 = new Board(tiles);
        for (Board nb: b.neighbors()) {
            System.out.println("neighbor\n" + nb);
            other2 = nb;
        }
        // Test the hamming method
        System.out.println("hamming distance=" + b.hamming());
        // Test the manhattan method
        System.out.println("manhattan distance=" + b.manhattan());
        // Test the equals method
        System.out.println("Board this=" + b);
        System.out.println("Board other1=" + other1);
        System.out.println("Board other2=" + other2);
        System.out.println(b.equals(other1));
        System.out.println(b.equals(other2));
        // Test the twin method
        System.out.println("Board this=" + b);
        System.out.println("Board twin=" + b.twin());
        System.out.println("Board this=" + b);
    }
}
