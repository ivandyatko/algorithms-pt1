import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<>();


    /**
     * Finds all line segments containing 4 or more points.
     */
    public FastCollinearPoints(Point[] points) {
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
                throw new NullPointerException("null point found");
            } else if (pts[idx].compareTo(pts[++idx]) == 0) {
                throw new IllegalArgumentException("duplicate point found");
            }
        }

        Point[] search = new Point[pts.length];
        System.arraycopy(pts, 0, search, 0, pts.length);

        for (Point point : pts) {
            // Sort by slope order for every point in natural ascending order.
            Arrays.sort(search, point.slopeOrder());

            // current segment length counter
            int segmentLength = 0;

            // segments second end, the first end is point variable.
            Point pair = point;
            // subsequent repetition flag
            boolean repeated = false;
            double currSlope, prevSlope = Double.NEGATIVE_INFINITY;
            for (int i = 1; i < search.length; i++) {
                final Point currPoint = search[i];
                currSlope = point.slopeTo(currPoint);
                // equal slopes? - maybe a collinear segment of 3+ points?
                if (currSlope == prevSlope) {
                    // Points are ordered in natural way so if current point is less than outer point -
                    // this means we starting to iterate over same segment that already !should! exist.
                    if (point.compareTo(currPoint) >= 0 || point.compareTo(search[i - 1]) >= 0) {
                        repeated = true;
                    }
                    if (!repeated) {
                        // we should take bigger second point to build a segment so other points are in between
                        if (currPoint.compareTo(pair) > 0 || search[i - 1].compareTo(pair) > 0) {
                            if (currPoint.compareTo(search[i - 1]) > 0) {
                                pair = currPoint;
                            } else {
                                pair = search[i - 1];
                            }
                        }
                        segmentLength++;
                    }
                    // got 2+ matches? - definitely a segment.
                } else if (segmentLength >= 2) {
                    if (!repeated) {
                        segments.add(new LineSegment(point, pair));
                    }
                    // resetting stuff coz there might be more segments with this point.
                    segmentLength = 0;
                    repeated = false;
                    pair = point;
                } else {
                    segmentLength = 0;
                    pair = point;
                    repeated = false;
                }
                prevSlope = currSlope;
            }

            if (segmentLength >= 2 && !repeated) {
                segments.add(new LineSegment(point, pair));
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
        return segments.toArray(new LineSegment[segments.size()]);
    }


    public static void main(String[] args) {
        // read the n points from a file
        In in =
            new In("C:\\devbox\\princeton algorithms pt1\\algorithms-pt1\\assignments\\collinear_points\\collinear-testing\\collinear\\input10.txt");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}