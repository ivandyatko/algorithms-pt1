import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> set;


    /**
     * Construct an empty set of points.
     */
    public PointSET() {
        set = new TreeSet<>();
    }


    /**
     * Is the set empty?
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }


    /**
     * Number of points in the set.
     */
    public int size() {
        return set.size();
    }


    /**
     *
     * Add the point to the set (if it is not already in the set).
     */
    public void insert(Point2D p) {
        checkNullArgument(p);
        set.add(p);
    }


    /**
     * Does the set contain point p?
     */
    public boolean contains(Point2D p) {
        checkNullArgument(p);
        return set.contains(p);
    }


    /**
     * Draw all points to standard draw.
     */
    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : set) {
            p.draw();
        }
        StdDraw.show();
    }


    /**
     * All points that are inside the rectangle.
     */
    public Iterable<Point2D> range(RectHV rect) {
        checkNullArgument(rect);
        ArrayList<Point2D> points = new ArrayList<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                points.add(p);
            }
        }
        return points;
    }


    /**
     * A nearest neighbor in the set to point p; null if the set is empty.
     */
    public Point2D nearest(Point2D p) {
        checkNullArgument(p);
        if (isEmpty()) {
            return null;
        } else {
            Point2D nearest = null;
            double nearestDistance = Double.POSITIVE_INFINITY;
            for (Point2D point : set) {
                if (p.distanceTo(point) < nearestDistance) {
                    nearest = point;
                    nearestDistance = p.distanceTo(point);
                }
            }
            return nearest;
        }
    }


    private void checkNullArgument(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Passed argument is null");
        }
    }


    public static void main(String[] args) {
        // unit testing of the methods (optional)
    }
}