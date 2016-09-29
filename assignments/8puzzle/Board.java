import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {

    private int[] flatArray;
    private int n;


    /**
     * Construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j).
     */
    public Board(int[][] blocks) {
        if (blocks == null || blocks.length < 2) {
            throw new IllegalArgumentException("invalid input board");
        }
        n = blocks.length;
        flatArray = new int[n * n];

        int b = 0;
        for (int[] block : blocks) {
            System.arraycopy(block, 0, flatArray, b * block.length, block.length);
            b++;
        }
    }


    private int getByXY(final int x, final int y) {
        return flatArray[y * n + x];
    }


    private int getX(final int val) {
        if (val < 1) {
            throw new IllegalArgumentException("invalid value passed");
        }
        return (val - 1) % n;
    }


    private int getY(final int val) {
        if (val < 1) {
            throw new IllegalArgumentException("invalid value passed");
        }
        return (val - 1) / n;
    }


    /**
     * Board dimension n.
     */
    public int dimension() {
        return n;
    }


    /**
     * Number of blocks out of place.
     */
    public int hamming() {
        int result = 0;
        for (int i = 0; i < flatArray.length; i++) {
            if (flatArray[i] != 0 && flatArray[i] != i + 1) {
                result++;
            }
        }
        return result;
    }


    /**
     * Sum of Manhattan distances between blocks and goal.
     */
    public int manhattan() {
        int result = 0;
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                final int coord = getByXY(x, y);
                if (coord > 0) {
                    result += (Math.abs(getX(coord) - x) + Math.abs(getY(coord) - y));
                }
            }
        }
        return result;
    }


    /**
     * Is this board the goal board?
     */
    public boolean isGoal() {
        for (int i = 0; i < flatArray.length; i++) {
            if (flatArray[i] != 0 && flatArray[i] != i + 1) {
                return false;
            }
        }
        return true;
    }


    /**
     * A board that is obtained by exchanging any pair of blocks.
     */
    public Board twin() {
        int first, second;
        do {
            first = StdRandom.uniform(0, flatArray.length - 1);
        } while (first != 0);

        do {
            second = StdRandom.uniform(0, flatArray.length - 1);
        } while (second != 0 && second != first);

        return this.copyAndSwap(first, second);
    }


    private Board copyAndSwap(final int firstElem, final int secondElem) {
        Board res = new Board(new int[n][n]);
        System.arraycopy(this.flatArray, 0, res.flatArray, 0, this.flatArray.length);

        res.flatArray[firstElem] = res.flatArray[secondElem];
        res.flatArray[secondElem] = this.flatArray[firstElem];

        return res;
    }


    /**
     * Does this board equal y?
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board = (Board) o;
        return n == board.n && Arrays.equals(flatArray, board.flatArray);
    }


    /**
     * All neighboring boards
     */
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int blankIdx = 0, blankX = 0, blankY = 0;
        for (int i = 0; i < flatArray.length; i++) {
            if (flatArray[i] == 0) {
                blankIdx = i;
                blankX = getX(blankIdx + 1);
                blankY = getY(blankIdx + 1);
                break;
            }
        }
        if (blankX > 0) {
            neighbors.push(this.copyAndSwap(blankIdx, blankIdx - 1));
        }
        if (blankX < n - 1) {
            neighbors.push(this.copyAndSwap(blankIdx, blankIdx + 1));
        }
        if (blankY > 0) {
            neighbors.push(this.copyAndSwap(blankIdx, blankIdx - n));
        }
        if (blankY < n - 1) {
            neighbors.push(this.copyAndSwap(blankIdx, blankIdx + n));
        }

        return neighbors;
    }


    /**
     * String representation of this board (in the output format specified below).
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                s.append(String.format("%2d ", getByXY(x, y)));
            }
            s.append("\n");
        }
        return s.toString();
    }


    public static void main(String[] args) {
        // goal board construct
        int[][] arr = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        Board b = new Board(arr);
        System.out.println(b.toString());
        System.out.println(b.isGoal());
        System.out.println();
        System.out.println(b.neighbors());
        System.out.println();
        System.out.println("manhattan 0? = " + b.manhattan());
        System.out.println("hamming 0? = " + b.hamming());

        // goal board construct
        int[][] arr2 = { { 1, 2, 6 }, { 3, 5, 4 }, { 7, 0, 8 } };
        Board b2 = new Board(arr2);
        System.out.println(b2.toString());
        System.out.println("false? = " + b2.isGoal());
        System.out.println();
        System.out.println(b2.neighbors());
        System.out.println();
        System.out.println("manhattan 7? = " + b2.manhattan());
        System.out.println("hamming 4? = " + b2.hamming());
    }
}