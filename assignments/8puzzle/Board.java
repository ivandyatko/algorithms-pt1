public class Board {

    /**
     * Construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j).
     */
    public Board(int[][] blocks) {

    }


    /**
     * Board dimension n.
     */
    public int dimension() {
        return 0;
    }


    /**
     * Number of blocks out of place.
     */
    public int hamming() {
        return 0;
    }


    /**
     * Sum of Manhattan distances between blocks and goal.
     */
    public int manhattan() {
        return 0;
    }


    /**
     * Is this board the goal board?
     */
    public boolean isGoal() {
        return false;
    }


    /**
     * A board that is obtained by exchanging any pair of blocks.
     */
    public Board twin() {
        return new Board(null);
    }


    /**
     * Does this board equal y?
     */
    public boolean equals(Object y) {
        return false;
    }


    /**
     * All neighboring boards
     */
    public Iterable<Board> neighbors() {
        return null;
    }


    /**
     * String representation of this board (in the output format specified below).
     */
    public String toString() {

        return "";
    }


    public static void main(String[] args) {
    }
}