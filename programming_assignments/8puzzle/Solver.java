import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.ResizingArrayStack;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    // the final node after running the Solver constructor
    private SearchNode node;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<SearchNode> searchPool = new MinPQ<>();
        node = new SearchNode(initial, 0);
        searchPool.insert(node);

        Board twin = initial.twin();
        MinPQ<SearchNode> twinSearchPool = new MinPQ<>();
        SearchNode twinNode = new SearchNode(twin, 0);
        twinSearchPool.insert(twinNode);

        // run the A* algorithm on the two puzzles, initial and twin, in lockstep
        while (!node.curBoard.isGoal() && !twinNode.curBoard.isGoal()) {
            node = search(searchPool);
            twinNode = search(twinSearchPool);
        }
    }

    // Do not enqueue a neighbor if its board is the same as the board of the previous search node
    private SearchNode search(MinPQ<SearchNode> pool) {
        SearchNode minNode = pool.delMin();
        for (Board neighbor: minNode.curBoard.neighbors()) {
            if (minNode.prevNode == null || !minNode.prevNode.curBoard.equals(neighbor)) {
                pool.insert(new SearchNode(neighbor, minNode, minNode.numMoves+1));
            }
        }
        return minNode;
    }

    // Caching the Hamming and Manhattan priorities
    private class SearchNode implements Comparable<SearchNode> {
        Board curBoard;
        SearchNode prevNode;
        int numMoves;
        int dist;

        public SearchNode(Board cur, SearchNode prev, int moves) {
            curBoard = cur;
            prevNode = prev;
            numMoves = moves;
            // use Manhattan distance, can be replaced with cur.hamming();
            dist = cur.manhattan();
        }

        public SearchNode(Board cur, int moves) {
            curBoard = cur;
            prevNode = null;
            numMoves = moves;
            // use Manhattan distance, can be replaced with cur.hamming();
            dist = cur.manhattan();
        }

        public int compareTo(SearchNode other) {
            return Integer.compare(priority(), other.priority());
        }

        private int priority() {
            return dist + numMoves;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return node.curBoard.isGoal();
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return node.numMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        SearchNode iterNode = node;
        ResizingArrayStack<Board> solMoves = new ResizingArrayStack<>();
        solMoves.push(iterNode.curBoard);
        while (iterNode.prevNode != null) {
            iterNode = iterNode.prevNode;
            solMoves.push(iterNode.curBoard);
        }
        return solMoves;
    }

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
