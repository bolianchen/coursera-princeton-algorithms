import java.util.TreeSet;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/*
 * A data type represents a set of points in the unit square
 * Performance requirements: 
 *     insert(), contains() in time proportional to the logarithm of the number of points in the set
 *                          in the worst case
 *     nearest() and range() in time proportional to the number of points in the set
 */
public class PointSET {

    private final TreeSet<Point2D> points;
 
    // construct an empty set of points 
    public PointSET() {
        points = new TreeSet<>();
    }
 
    // is the set empty? 
    public boolean isEmpty() {
        return points.isEmpty();
    }
 
    // number of points in the set 
    public int size() {
        return points.size();
    }
 
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }
 
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    // draw all points to standard draw 
    public void draw() {
        //StdDraw.setPenRadius(0.05);
        for (Point2D p: points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        LinkedQueue<Point2D> pointsInside = new LinkedQueue<>();
        for (Point2D p: points) {
            if (rect.contains(p)) {
                pointsInside.enqueue(p);
            }
        }
        return pointsInside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        double shortestDist = Double.POSITIVE_INFINITY;
        Point2D nearestNeighbor = null;
        for (Point2D point: points) {
            if (p.distanceTo(point) < shortestDist) {
                nearestNeighbor = point;
                shortestDist = p.distanceTo(point);
            }
        }
        return nearestNeighbor;
    }

    // unit testing of the methods (optional) 
    public static void main(String[] args) {
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLUE);
        PointSET pts = new PointSET();
        pts.insert(new Point2D(0.3, 0.5));
        pts.insert(new Point2D(0.3, 0.7));
        pts.insert(new Point2D(0.5, 0.7));
        pts.draw();
    }
 }
