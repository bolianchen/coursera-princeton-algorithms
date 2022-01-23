import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF grid;
    private final WeightedQuickUnionUF grid2;
    private final int size;
    private final boolean[][] opened;
    private int numOpenSites;
        
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        // add two respectively as the virtual top and bottom sites
        size = n;
        grid = new WeightedQuickUnionUF(n*n + 2);
        grid2 = new WeightedQuickUnionUF(n*n + 2);
        numOpenSites = 0;
        opened = new boolean[n][n];
    }

    // helper methods to check if row and column indices are between 1 and n
    // applied in the open, isOpen and isFull methods
    private void checkIndices(int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) {
            throw new IllegalArgumentException();
        }
    }
    private boolean isIndicesValid(int row, int col) {
        if ((row < 1 || row > size) || (col < 1 || col > size)) {
            return false;
        }
        return true;
    }
    
    // a helper function to connect the newly opened site to its adjacent open sites
    // (row-1, col), (row+1, col), (row, col-1), (row, col+1)
    // if row==1, connect to top; if row=n, connect to bottom
    private void connectAdjs(int row, int col) {
        if (row == 1) {
            grid.union(0, indices2UFid(row, col)); 
            grid2.union(0, indices2UFid(row, col)); 
        }
        if (row == size) {
            grid.union(indices2UFid(row, col), size*size + 1); 
        }

        for (int r = row - 1; r <= row + 1; r = r + 1) {
            if (isIndicesValid(r, col) && isOpen(r, col)) {
                grid.union(indices2UFid(r, col), indices2UFid(row, col));
                grid2.union(indices2UFid(r, col), indices2UFid(row, col));
            }
        }

        for (int c = col - 1; c <= col + 1; c = c + 1) {
            if (isIndicesValid(row, c) && isOpen(row, c)) {
                grid.union(indices2UFid(row, c), indices2UFid(row, col));
                grid2.union(indices2UFid(row, c), indices2UFid(row, col));
            }
        }
    }

    // a helper method to convert (row, col) to the corresponding union-find id
    private int indices2UFid(int row, int col) {
        return 1 + (row-1) * size + (col-1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkIndices(row, col);
        if (isOpen(row, col)) {
            return;
        }
        opened[row-1][col-1] = true;
        connectAdjs(row, col);
        numOpenSites += 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndices(row, col);
        return opened[row-1][col-1];
    }

    // is the site (row, col) full?
    // a full site is an open site can be connected to an open site in the top row
    // via a chain of neighboring open sites
    public boolean isFull(int row, int col) {
        checkIndices(row, col);
        return grid2.find(0) == grid2.find(indices2UFid(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.find(0) == grid.find(size*size+1);
    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.print("Please enter the size of 2d grid:");
        int n = Integer.parseInt(StdIn.readString());
        Percolation p = new Percolation(n);

        System.out.print("Please enter a grid's row col to open:");
        while (!StdIn.isEmpty()) {
            int row = Integer.parseInt(StdIn.readString());
            int col = Integer.parseInt(StdIn.readString());
            p.open(row, col);
            System.out.println("does the system percolate?" + p.percolates());
            System.out.println("is 3 1 Full?" + p.isFull(3, 1));
        }
    }
}
