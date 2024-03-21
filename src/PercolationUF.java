public class PercolationUF implements IPercolate {

    private IUnionFind myFinder;
    private boolean[][] myGrid;
    private final int VTOP, VBOTTOM;
    private int myOpenCount;

    public PercolationUF(IUnionFind finder, int size) {
        myGrid = new boolean[size][size];
        finder.initialize(size * size + 2);
        myFinder = finder;
        VTOP = size * size;
        VBOTTOM = size * size + 1;
        myOpenCount = 0;
    }

    protected boolean inBounds(int row, int col) {
        if (row < 0 || row >= myGrid.length)
            return false;
        if (col < 0 || col >= myGrid[0].length)
            return false;
        return true;
    }

    @Override
    public void open(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }

        if (isOpen(row, col)) {
            return;
        }
        myGrid[row][col] = true;
        if(isFull(row, col)){
            return;
        }
        myOpenCount++;

        int curIndex = row * myGrid.length + col;
        if (row == 0) {
            myFinder.union(curIndex, VTOP);
        }
        if (row == myGrid[0].length - 1)
            myFinder.union(curIndex, VBOTTOM);

        openHelper(row, col, row - 1, col);
        openHelper(row, col, row, col + 1);
        openHelper(row, col, row + 1, col);
        openHelper(row, col, row, col - 1);
    }

    // not part of starter code i wrote the below function myself as a helper for
    // the if statement above
    public void openHelper(int row, int col, int r, int c) {
        if (!inBounds(r, c))
            return;
        int curIndex = row * myGrid.length + col;
        int adjIndex = r * myGrid.length + c;
        if (isOpen(r, c)) {
            myFinder.union(curIndex, adjIndex);
            /*
             * if (row == VTOP) {
             * System.out.println("here");
             * myFinder.union(curIndex, VTOP);
             * }
             * if (row == VBOTTOM) myFinder.union(curIndex, VBOTTOM);
             */
        }
    }

    @Override
    public boolean isOpen(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
        return myGrid[row][col];
    }

    @Override
    public boolean isFull(int row, int col) {
        if (!inBounds(row, col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
        int curIndex = row * myGrid.length + col;
        return myFinder.connected(curIndex, VTOP);
    }

    @Override
    public boolean percolates() {
        return myFinder.connected(VTOP, VBOTTOM);
    }

    @Override
    public int numberOfOpenSites() {
        return myOpenCount;
    }

}
