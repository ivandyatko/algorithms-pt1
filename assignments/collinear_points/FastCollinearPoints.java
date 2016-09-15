import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segments;


    /**
     * Finds all line segments containing 4 or more points.
     */
    public FastCollinearPoints(Point[] points) {
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

        for (Point p : points) {
            Arrays.sort(points, p.slopeOrder());
        }
    }


    /**
     * The number of line segments.
     */
    public int numberOfSegments() { return 0;}


    /**
     * The line segments.
     */
    public LineSegment[] segments() {return null;}
}