import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segments;


    /**
     * Finds all line segments containing 4 points.
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("null argument passed");
        }
        Arrays.sort(points);
        int idx = 0;
        final int n = points.length;
        while (idx < n - 1) {
            if (points[idx] == null) {
                throw new NullPointerException("null point found.");
            } else if (points[idx].compareTo(points[++idx]) == 0) {
                throw new IllegalArgumentException("duplicate point found");
            }
        }
        segments = new ArrayList<>();

        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                for (int r = q + 1; r < n; r++) {
                    for (int s = r + 1; s < n; s++) {
                        if (points[p].slopeTo(points[q]) == points[p].slopeTo(points[r])
                            && points[p].slopeTo(points[q]) == points[p].slopeTo(points[s])) {
                            segments.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
    }


    /**
     * The number of line segments.
     */
    public int numberOfSegments() {
        return segments.size();
    }


    /**
     * The line segments.
     */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[numberOfSegments()]);
    }


    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("C:\\devbox\\princeton algorithms pt1\\algorithms-pt1\\assignments\\collinear_points\\input8.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}