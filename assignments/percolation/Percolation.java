import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int a, arrSize;

    private final boolean[] state;

    private final WeightedQuickUnionUF uf;


    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid grid size passed");
        }

        a = n;
        arrSize = n * n + 2;
        uf = new WeightedQuickUnionUF(arrSize);
        state = new boolean[arrSize];

        for (int i = 1; i < a; i++) {
            uf.union(0, i);
        }

        for (int i = arrSize - n - 1; i < arrSize - 1; i++) {
            uf.union(arrSize - 1, i);
        }
    }


    public void open(int i, int j) {
        if (i < 1 || j < 1 || i > a || j > a) {
            throw new IndexOutOfBoundsException("Invalid grid arguments passed");
        }

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


    public boolean isOpen(int i, int j) {
        if (i < 1 || j < 1 || i > a || j > a) {
            throw new IndexOutOfBoundsException("Invalid grid arguments passed");
        }
        // is site (row i, column j) open?
        return state[findElem(i, j)];
    }


    public boolean isFull(int i, int j) {
        if (i < 1 || j < 1 || i > a || j > a) {
            throw new IndexOutOfBoundsException("Invalid grid arguments passed");
        }
        // is site (row i, column j) full?
        return uf.connected(0, findElem(i, j));
    }


    public boolean percolates() {
        return uf.connected(0, arrSize - 1);
    }


    private int findElem(final int i, final int j) {
        return (i - 1) * a + j;
    }
}