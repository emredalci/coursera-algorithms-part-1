package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final String GRID_ERROR_MESSAGE = "Value must be greater than 0. Your value is %d";
    private static final String INDEX_ERROR_MESSAGE = "%s value is out of bounds. It must be lower or equal %d";

    private static final String ROW = "row";
    private static final String COL = "col";
    private final int size;
    private final int topRow;
    private final int bottomRow;
    private final int virtualTop;
    private final int virtualBottom;
    private final boolean[] serializedGrid;
    private final WeightedQuickUnionUF unionFind;
    private final WeightedQuickUnionUF fullUnionFind;
    private int openSite;

    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException(String.format(GRID_ERROR_MESSAGE, n));
        }

        this.size = n;
        this.topRow = 1;
        this.bottomRow = n;
        this.virtualTop = 0;
        this.virtualBottom = n * n + 1;
        this.serializedGrid = new boolean[n * n + 2];
        this.unionFind = new WeightedQuickUnionUF(n * n + 2);
        this.fullUnionFind = new WeightedQuickUnionUF(n * n + 1);
    }

    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) return;
        int currentIndex = findSerializedGridIndex(row, col);
        serializedGrid[currentIndex] = true;
        openSite++;

        unionVirtualTopGrid(row, currentIndex);

        unionVirtualBottomGrid(row, currentIndex);

        unionNeighborGrid(row - 1, col, currentIndex); // Above

        unionNeighborGrid(row + 1, col, currentIndex); // Below

        unionNeighborGrid(row, col - 1, currentIndex); // Left

        unionNeighborGrid(row, col + 1, currentIndex); // Right
    }

    private boolean isOnSerializedGrid(int row, int col) {
        int leftCol = col - 1;
        int aboveRow = row - 1;
        return checkShiftedRowAndCol(leftCol, aboveRow);
    }

    private boolean checkShiftedRowAndCol(int leftCol, int aboveRow) {
        return aboveRow >= 0 && leftCol >= 0 && aboveRow < size && leftCol < size;
    }

    private void unionNeighborGrid(int row, int col, int currentIndex) {
        if (isOnSerializedGrid(row, col) && isOpen(row, col)) {
            unionFind.union(currentIndex, findSerializedGridIndex(row, col));
            fullUnionFind.union(currentIndex, findSerializedGridIndex(row, col));
        }
    }

    private void unionVirtualBottomGrid(int row, int currentIndex) {
        if (row == bottomRow) {
            unionFind.union(virtualBottom, currentIndex);
        }
    }

    private void unionVirtualTopGrid(int row, int currentIndex) {
        if (row == topRow) {
            unionFind.union(virtualTop, currentIndex);
            fullUnionFind.union(virtualTop, currentIndex);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return serializedGrid[findSerializedGridIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (Boolean.FALSE.equals(isOpen(row, col))) {
            return false;
        }
        return fullUnionFind.find(virtualTop) == fullUnionFind.find(findSerializedGridIndex(row, col));
    }

    public int numberOfOpenSites() {
        return openSite;
    }

    public boolean percolates() {
        return unionFind.find(virtualTop) == unionFind.find(virtualBottom);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > size)
            throw new IllegalArgumentException(String.format(INDEX_ERROR_MESSAGE, ROW, row));
        if (col < 1 || col > size)
            throw new IllegalArgumentException(String.format(INDEX_ERROR_MESSAGE, COL, col));
    }

    private int findSerializedGridIndex(int row, int col) {
        return (row - 1) * size + col;
    }

    public static void main(String[] args) {


    }

}
