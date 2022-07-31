import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
        private final int gridSize;
        private int numOfOpened;
        private boolean[][] opened, connectedToTop, connectedToBottom;
        private boolean isPercolating;
        private final WeightedQuickUnionUF gridManager;
        private final int[][] shiftsOfAdjCoordinates = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
        
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        gridSize = n;
        numOfOpened = 0;
        opened = new boolean[gridSize][gridSize];
        connectedToTop = new boolean[gridSize][gridSize];
        connectedToBottom = new boolean[gridSize][gridSize];
        isPercolating = false;
        gridManager = new WeightedQuickUnionUF((gridSize) * (gridSize));
    }

    private void checkCornerCoordinates(int row, int col) {
        if ((row < 1 || row > gridSize) || (col < 1 || col > gridSize)) {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkCornerCoordinates(row, col);

        if (!isOpen(row, col)) {
            opened[row - 1][col - 1] = true;
            if (row == 1) {
                connectedToTop[row - 1][col - 1] = true;
            }
            if (row == gridSize) {
                connectedToBottom[row - 1][col - 1] = true;
            }
            if (connectedToTop[row - 1][col - 1] && connectedToBottom[row - 1][col - 1]) {
                isPercolating = true;
            }

            // connected with its adjacent sites
            int otherRow, otherCol, rootId, otherRootId;
            int[] rootCoordinates, otherRootCoordinates;
            int rootRow, rootCol, otherRootRow, otherRootCol;
            // there might be no valid shifts
            for (int[] shift: shiftsOfAdjCoordinates) {
                // root of tree#1
                rootId = gridManager.find(rowColToUFId(row, col));
                rootCoordinates = ufIdToRowCol(rootId);
                rootRow = rootCoordinates[0];
                rootCol = rootCoordinates[1];
                otherRow = row + shift[0];
                otherCol = col + shift[1];
                if (isValidCoordinates(otherRow, otherCol) && isOpen(otherRow, otherCol)) {
                    // root of tree#2
                    otherRootId = gridManager.find(rowColToUFId(otherRow, otherCol));
                    otherRootCoordinates = ufIdToRowCol(otherRootId);
                    otherRootRow = otherRootCoordinates[0];
                    otherRootCol = otherRootCoordinates[1];

                    // new tree formed after union
                    // in Test 17 and 21, the root could be randomly reset to be neither 
                    // the roots of the two child trees
                    gridManager.union(rootId, otherRootId);
                    rootId = gridManager.find(rootId);
                    rootCoordinates = ufIdToRowCol(rootId);

                    if (connectedToTop[rootRow - 1][rootCol - 1] || connectedToTop[otherRootRow - 1][otherRootCol - 1]) {
                        connectedToTop[rootCoordinates[0] - 1][rootCoordinates[1] - 1] = true;

                    }
                    if (connectedToBottom[rootRow - 1][rootCol - 1] || connectedToBottom[otherRootRow - 1][otherRootCol - 1]) {
                        connectedToBottom[rootCoordinates[0] - 1][rootCoordinates[1] - 1] = true;
                    }

                    if (connectedToTop[rootCoordinates[0] - 1][rootCoordinates[1] - 1] && connectedToBottom[rootCoordinates[0] - 1][rootCoordinates[1] - 1]) {
                        isPercolating = true;
                    }
                }
            }
            numOfOpened += 1;
        }
    }

    private boolean isValidCoordinates(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            return false;
        }
        return true;
    }

    private int rowColToUFId(int row, int col) {
        return gridSize * (row - 1) + (col - 1);
    }

    private int[] ufIdToRowCol(int id) {
        int[] rcCoordinates = new int[2];
        rcCoordinates[0] = id / gridSize + 1;
        rcCoordinates[1] = id % gridSize + 1;
        return rcCoordinates;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkCornerCoordinates(row, col);
        return opened[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkCornerCoordinates(row, col);
        int id = rowColToUFId(row, col);
        int rootId = gridManager.find(id);
        int[] rootCoordinates = ufIdToRowCol(rootId);
        return connectedToTop[rootCoordinates[0] - 1][rootCoordinates[1] - 1];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpened;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolating;
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
