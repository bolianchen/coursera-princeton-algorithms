
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private MinPQ<SearchNode> game;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)

    // Don't enqueue a neighbor if its board is the same as the board of the previous search node
    // Caching the Hamming and Manhattan priorities
    private class SearchNode {
        Board curBoard;
        int numMoves;
        Board prevBoard;

        public SearchNode(Board cur, int moves, Board prev) {
            curBoard = cur;
            numMoves = moves;
            prevBoard = prev;
        }

        public SearchNode(Board cur, int moves) {
            curBoard = cur;
            numMoves = moves;
            prevBoard = null;
        }
    }

    // is the initial board solvable? (see below)
    // in the constructor, use the initial board and its twin to explore search nodes in lockstep for both
    // if the initial board leads to the goal board, it's solvable;
    // if its twin leads to the goal board, not solvable
    public boolean isSolvable()

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()

    // test client (see below) 
    public static void main(String[] args) {
	// create initial board from file
	In in = new In(args[0]);
	int n = in.readInt();
	int[][] tiles = new int[n][n];
	for (int i = 0; i < n; i++)
	    for (int j = 0; j < n; j++)
		tiles[i][j] = in.readInt();
	Board initial = new Board(tiles);

	// solve the puzzle
	Solver solver = new Solver(initial);

	// print solution to standard output
	if (!solver.isSolvable())
	    StdOut.println("No solution possible");
	else {
	    StdOut.println("Minimum number of moves = " + solver.moves());
	    for (Board board : solver.solution())
		StdOut.println(board);
	}
    }
}
