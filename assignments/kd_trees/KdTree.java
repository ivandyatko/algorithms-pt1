import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;

public class KdTree {

    private Node root;
    private int size;


    /**
     * Construct an empty set of points.
     */
    public KdTree() { }


    /**
     * Is the set empty?
     */
    public boolean isEmpty() {
        return size <= 0;
    }


    /**
     * Number of points in the set.
     */
    public int size() {
        return size;
    }


    /**
     *
     * Add the point to the set (if it is not already in the set).
     */
    public void insert(Point2D p) {
        checkNullArgument(p);
        if (!isEmpty()) {
            RectHV rect;
            Node forInsert = traceNode(p, root);
            boolean toLeft;
            if (forInsert == null) {
                return;
            } else if (forInsert.vertical) {
                if (Point2D.X_ORDER.compare(forInsert.point, p) >= 0) {
                    rect = new RectHV(forInsert.rect.xmin(), forInsert.rect.ymin(), forInsert.point.x(), forInsert.rect.ymax());
                    toLeft = true;
                } else {
                    rect = new RectHV(forInsert.point.x(), forInsert.rect.ymin(), forInsert.rect.xmax(), forInsert.rect.ymax());
                    toLeft = false;
                }
            } else {
                if (Point2D.Y_ORDER.compare(forInsert.point, p) >= 0) {
                    rect = new RectHV(forInsert.rect.xmin(), forInsert.rect.ymin(), forInsert.rect.xmax(), forInsert.point.y());
                    toLeft = true;
                } else {
                    rect = new RectHV(forInsert.rect.xmin(), forInsert.point.y(), forInsert.rect.xmax(), forInsert.rect.ymax());
                    toLeft = false;
                }
            }
            if (toLeft) {
                forInsert.lb = new Node(p, forInsert.level + 1, rect);
            } else {
                forInsert.rb = new Node(p, forInsert.level + 1, rect);
            }
        } else {
            root = new Node(p, 0, new RectHV(0, 0, 1, 1));
        }
        size++;
    }


    private Node traceNode(Point2D p, Node scent) {
        Node prev;
        do {
            if (scent.point.equals(p)) {
                return null;
            }
            prev = scent;
            if (scent.vertical) {
                if (Point2D.X_ORDER.compare(scent.point, p) >= 0) {
                    scent = scent.lb;
                } else {
                    scent = scent.rb;
                }
            } else {
                if (Point2D.Y_ORDER.compare(scent.point, p) >= 0) {
                    scent = scent.lb;
                } else {
                    scent = scent.rb;
                }
            }
        } while (scent != null);

        return prev;
    }


    /**
     * Does the set contain point p?
     */
    public boolean contains(Point2D p) {
        checkNullArgument(p);
        Node result = traceNode(p, root);
        return result == null;
    }


    /**
     * Draw all points to standard draw.
     */
    public void draw() {
        StdDraw.enableDoubleBuffering();
        drawNode(root);
        StdDraw.show();
    }


    private void drawNode(Node n) {
        if (n != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.point.draw();
            StdDraw.setPenRadius();
            if (n.vertical) {
                StdDraw.setPenColor(Color.RED);
                StdDraw.line(n.point.x(), n.rect.ymin(), n.point.x(), n.rect.ymax());
            } else {
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.line(n.rect.xmin(), n.point.y(), n.rect.xmax(), n.point.y());
            }
            drawNode(n.lb);
            drawNode(n.rb);
        }
    }


    /**
     * All points that are inside the rectangle.
     */
    public Iterable<Point2D> range(RectHV rect) {
        checkNullArgument(rect);
        ArrayList<Point2D> points = new ArrayList<>();

        checkRange(root, rect, points);

        return points;
    }


    private void checkRange(Node n, RectHV rect, ArrayList<Point2D> list) {
        if (n == null) {
            return;
        }
        if (rect.contains(n.point)) {
            list.add(n.point);
        }
        if (n.rb != null && rect.intersects(n.rb.rect)) {
            checkRange(n.rb, rect, list);
        }
        if (n.lb != null && rect.intersects(n.lb.rect)) {
            checkRange(n.lb, rect, list);
        }
    }


    /**
     * A nearest neighbor in the set to point p; null if the set is empty.
     */
    public Point2D nearest(Point2D p) {
        checkNullArgument(p);
        if (isEmpty()) {
            return null;
        } else {
            Double nearestDist = Double.POSITIVE_INFINITY;
            Point2D nearest = null;
            Node current = root;
            do {
                if (current.point.equals(p)) {
                    return p;
                } else if (p.distanceTo(current.point) < nearestDist) {
                    nearestDist = p.distanceTo(current.point);
                    nearest = current.point;
                }
                if (current.vertical) {
                    if (Point2D.X_ORDER.compare(current.point, p) >= 0) {
                        current = current.lb;
                    } else {
                        current = current.rb;
                    }
                } else {
                    if (Point2D.Y_ORDER.compare(current.point, p) >= 0) {
                        current = current.lb;
                    } else {
                        current = current.rb;
                    }
                }
            } while (current != null);


            return nearest;
        }
    }


    private void checkNullArgument(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Passed argument is null");
        }
    }


    private final class Node {
        private Point2D point;
        private Node lb;
        private Node rb;
        private RectHV rect;
        private boolean vertical;
        private int level;


        Node(Point2D point, int level, RectHV rect) {
            this.point = point;
            this.vertical = level % 2 == 0;
            this.level = level;
            this.rect = rect;
        }
    }


    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.1, 0.1));
        tree.insert(new Point2D(0.2, 0.2));
        tree.insert(new Point2D(0.3, 0.3));
        tree.insert(new Point2D(0.4, 0.4));
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.6, 0.6));
        final Iterable<Point2D> range = tree.range(new RectHV(0, 0, 1, 1));
        tree.draw();
        // unit testing of the methods (optional)
    }
}