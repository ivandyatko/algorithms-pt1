import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int a, arrSize;

    private final boolean[] state;

    private final WeightedQuickUnionUF uf;


    /**
     * Percolation init constructor.
     * @param n side of the n-by-n grid
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid grid size passed");
        }

        a = n;
        arrSize = n * n + 2;
        uf = new WeightedQuickUnionUF(arrSize);
        state = new boolean[arrSize];

        for (int i = 1; i <= a; i++) {
            uf.union(0, i);
        }

        for (int i = arrSize - n - 1; i < arrSize - 1; i++) {
            uf.union(arrSize - 1, i);
        }
    }


    /**
     * Method to open particular grid node.
     * @param i - row idx.
     * @param j - column idx.
     */
    public void open(int i, int j) {
        checkIdx(i, j);
        // open site (row i, column j) if it is not open already

        if (!isOpen(i, j)) {
            int elem = findElem(i, j);

            if (j > 1 && state[elem - 1]) {
                uf.union(elem, elem - 1);
            }

            if (j < a && state[elem + 1]) {
                uf.union(elem, elem + 1);
            }

            if (i > 1 && state[elem - a]) {
                uf.union(elem, elem - a);
            }

            if (i < a && state[elem + a]) {
                uf.union(elem, elem + a);
            }

            state[elem] = true;
        }
    }


    /**
     * Check whether particular grid node is open.
     * @param i - row idx.
     * @param j - column idx.
     */
    public boolean isOpen(int i, int j) {
        checkIdx(i, j);
        return state[findElem(i, j)];
    }


    /**
     * Check if current node is full i.e. connected to the top element.
     * @param i - row idx.
     * @param j - column idx.
     */
    public boolean isFull(int i, int j) {
        checkIdx(i, j);
        return isOpen(i, j) && uf.connected(0, findElem(i, j));
    }


    /**
     * Check if top and bottom virtual nodes are in the same component.
     */
    public boolean percolates() {
        // if n = 1
        if (a == 1) {
            return state[1];
        }
        return uf.connected(0, arrSize - 1);
    }


    /**
     * Method to find absolute index in the array mapped by i and j indexes.
     */
    private int findElem(final int i, final int j) {
        return (i - 1) * a + j;
    }


    private void checkIdx(int i, int j) {
        if (i < 1 || j < 1 || i > a || j > a) {
            throw new IndexOutOfBoundsException("Invalid grid arguments passed");
        }
    }


    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        StdOut.println(p.isFull(1, 1));
        StdOut.println(p.isOpen(1, 1));

        StdOut.println(p.isFull(5, 5));
        StdOut.println(p.isOpen(5, 5));

        p.open(5, 5);
        StdOut.println(p.isOpen(5, 5));

        p.open(2, 2);
        p.open(2, 3);
        StdOut.println(p.uf.connected(p.findElem(2, 2), p.findElem(2, 3)));

        p.open(2, 1);
        p.open(1, 1);
        StdOut.println(p.uf.connected(p.findElem(1, 1), p.findElem(2, 3)));

        StdOut.println(p.isFull(2, 3));

        Percolation p2 = new Percolation(6);
        p2.open(1, 6);
        StdOut.println();
        StdOut.println(p2.isFull(1, 6));

        Percolation p3 = new Percolation(1);
        StdOut.println();
        StdOut.println(p3.percolates());
    }
}