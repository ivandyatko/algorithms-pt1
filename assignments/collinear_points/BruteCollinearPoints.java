import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<>();


    /**
     * Finds all line segments containing 4 points.
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException("null argument passed");
        }

        Point[] pts = new Point[points.length];
        System.arraycopy(points, 0, pts, 0, pts.length);

        Arrays.sort(pts);
        int idx = 0;
        final int n = pts.length;
        while (idx < n - 1) {
            if (pts[idx] == null) {
                throw new NullPointerException("null point found.");
            } else if (pts[idx].compareTo(pts[++idx]) == 0) {
                throw new IllegalArgumentException("duplicate point found");
            }
        }

        for (int p = 0; p < n; p++) {
            for (int q = p + 1; q < n; q++) {
                for (int r = q + 1; r < n; r++) {
                    for (int s = r + 1; s < n; s++) {
                        if (pts[p].slopeTo(pts[q]) == pts[p].slopeTo(pts[r])
                            && pts[p].slopeTo(pts[q]) == pts[p].slopeTo(pts[s])) {
                            segments.add(new LineSegment(pts[p], pts[s]));
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
        //        In in =
        // new In("C:\\devbox\\princeton algorithms pt1\\algorithms-pt1\\assignments\\collinear_points\\input6.txt");
        In in = new In(args[0]);
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