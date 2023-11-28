package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private final int size;
    private int openSiteNum;
    private final boolean[][] sites;
    private final WeightedQuickUnionUF sitesTracker;
    private final int[] openBottomColumn;
    private boolean isPercolation = false;
    private int openBottomSize = 0;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N should not smaller than 1!");
        }
        size = N;
        openSiteNum = 0;

        openBottomColumn = new int[N];
        // init all sites blocked
        sites = new boolean[N][N];
        for (int i = 0; i < sites.length; i++) {
            for (int j = 0; j < sites[i].length; j++) {
                sites[i][j] = false;
            }
        }
        // using disjoint set to track connection between each site
        // the last two sites represent the all upmost sites and all lowest sites
        sitesTracker = new WeightedQuickUnionUF(N * N + 1);
        // int j = xyTo1D(N - 1, 0);
        for (int i = 0; i < N; i++) {
            sitesTracker.union(N * N, i);
            // sitesTracker.union(N * N + 1, j);
            // j++;
        }
    }

    /**
     * convert an (x,y) position to a disjoint set site bearing
     * @param row the row coordinate from 0 ~ N-1
     * @param col the col coordinate from 0 ~ N-1
     * @return the index of the corresponding site in the disjoint set
     */
    private int xyTo1D(int row, int col) {
        if (row >= size || col >= size) {
            throw new java.lang.IndexOutOfBoundsException("Index outbounded!");
        }
        return row * size + col;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row >= size || col >= size) {
            throw new java.lang.IndexOutOfBoundsException("Index outbounded!");
        }
        // multiple clicks on the same site only increase openSiteNum once
        if (!isOpen(row, col)) {
            openSiteNum++;
            sites[row][col] = true;
            // connect it with neighbor open sites
            if (row > 0 && isOpen(row - 1, col)) {
                sitesTracker.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (row < size - 1 && isOpen(row + 1, col)) {
                sitesTracker.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            if (col > 0 && isOpen(row, col - 1)) {
                sitesTracker.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (col < size - 1 && isOpen(row, col + 1)) {
                sitesTracker.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }

            // record it if a bottom site is open
            if (row == size - 1) {
                openBottomColumn[openBottomSize] = col;
                openBottomSize++;
            }

            // if it percolated
            if (!isPercolation) {
                for (int i = 0; i < openBottomSize; i++) {
                    if (
                            sitesTracker.connected(
                                    xyTo1D(size - 1, openBottomColumn[i]),
                                    size * size
                            )
                    ) {
                        isPercolation = true;
                    }
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row >= size || col >= size) {
            throw new java.lang.IndexOutOfBoundsException("Index outbounded!");
        }
        return sites[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row >= size || col >= size) {
            throw new java.lang.IndexOutOfBoundsException("Index outbounded!");
        }
        return sitesTracker.connected(xyTo1D(row, col), size * size) && this.isOpen(row, col);
    }


    // number of open sites
    public int numberOfOpenSites() {
        return openSiteNum;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolation;
    }

    public static void main(String[] args) {
        Percolation test = new Percolation(4);
        System.out.println(test.percolates());
        System.out.println(test.numberOfOpenSites());
        test.open(3, 1);
        test.open(2, 2);
        test.open(1, 1);
        test.open(0, 3);
        test.open(1, 3);
        System.out.println(test.isFull(1, 3));
        System.out.println(test.percolates());
        System.out.println(test.numberOfOpenSites());
        test.open(2, 1);
        test.open(1, 2);
        System.out.println(test.percolates());
        System.out.println(test.numberOfOpenSites());
    }
}
