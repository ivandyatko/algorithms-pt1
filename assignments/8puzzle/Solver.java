import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private final boolean isSolvable;

    private Node current;


    /**
     * Find a solution to the initial board (using the A* algorithm).
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("null is passed");
        }
        MinPQ<Node> boardsQueue = new MinPQ<>(getComparator());
        MinPQ<Node> boardsTwinQueue = new MinPQ<>(getComparator());

        boardsQueue.insert(new Node(initial, null, 0));
        boardsTwinQueue.insert(new Node(initial.twin(), null, 0));

        do {
            current = boardsQueue.delMin();
            Node currentTwin = boardsTwinQueue.delMin();
            if (currentTwin.board.isGoal()) {
                isSolvable = false;
                break;
            } else if (current.board.isGoal()) {
                isSolvable = true;
                break;
            } else {
                addDistinct(boardsQueue, current);
                addDistinct(boardsTwinQueue, currentTwin);
            }
        } while (true);
    }


    private void addDistinct(MinPQ<Node> boardsQueue, Node node) {
        final Board board = node.board;
        final Board prev = node.prev == null ? null : node.prev.board;
        for (Board b : board.neighbors()) {
            if (prev == null || !prev.equals(b)) {
                boardsQueue.insert(new Node(b, node, node.moves + 1));
            }
        }
    }


    private Comparator<Node> getComparator() {
        return new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                final int compare = Integer.compare(o1.board.manhattan() + o1.moves, o2.board.manhattan() + o2.moves);
                if (compare != 0) {
                    return compare;
                } else {
                    return Integer.compare(o1.board.hamming() + o1.moves, o2.board.hamming() + o2.moves);
                }
            }
        };
    }


    /**
     * Is the initial board solvable?
     */
    public boolean isSolvable() {
        return isSolvable;
    }


    /**
     * Min number of moves to solve initial board; -1 if unsolvable.
     */
    public int moves() {
        return isSolvable() ? current.moves : -1;
    }


    /**
     * Sequence of boards in a shortest solution; null if unsolvable.
     */
    public Iterable<Board> solution() {
        return isSolvable() ? getIterable() : null;
    }


    private Iterable<Board> getIterable() {
        Stack<Board> boards = new Stack<>();
        Node current = this.current;

        do {
            boards.push(current.board);
            current = current.prev;
        }
        while (current != null);

        return boards;
    }


    private final class Node {
        private final Board board;
        private final Node prev;
        private final int moves;


        Node(Board board, Node prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
        }
    }


    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}