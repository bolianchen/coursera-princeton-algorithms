import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final double lineThickness = 0.005;
    private static final double pointThickness = 0.02;
    private int size;
    private Node root;

    private class Node {
        private final Point2D key;
        private Node right;
        private Node left;
        private final RectHV rect;
        private final boolean compareX;
        public Node(Point2D p, double xmin, double ymin, double xmax, double ymax, boolean cX) {
            key = p;
            rect = new RectHV(xmin, ymin, xmax, ymax);
            compareX = cX;
        }
        public boolean isKeyEqual(Point2D p) {
            return key.equals(p);
        }
        public boolean isSmallerThanKey(Point2D p) {
            if (compareX) {
                return p.x() < key.x();
            } else {
                return p.y() < key.y();
            }
        }
    }

    // construct an empty kdtree of points 
    public KdTree() {
        size = 0;
    }

    // is the kdtree empty?
    public boolean isEmpty() {
        return size == 0;
    }
    // number of points in the tree
    public int size() { 
        return size;
    }

    // add the point to the kdtree (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        root = insertHelper(root, p, 0.0, 0.0, 1.0, 1.0, true);
    }

    // a helper method for insert
    private Node insertHelper(Node tree, Point2D p, double xmin, double ymin, double xmax, double ymax,
            boolean cX) {
        if (tree == null) {
            size += 1;
            return new Node(p, xmin, ymin, xmax, ymax, cX);
        }

        if (!tree.isKeyEqual(p)) {
            if (tree.isSmallerThanKey(p)) {
                if (cX) {
                    tree.left = insertHelper(tree.left, p, xmin, ymin, tree.key.x(), ymax, !cX);
                } else {
                    tree.left = insertHelper(tree.left, p, xmin, ymin, xmax, tree.key.y(), !cX);
                }
            } else {
                if (cX) {
                    tree.right = insertHelper(tree.right, p, tree.key.x(), ymin, xmax, ymax, !cX);
                } else {
                    tree.right = insertHelper(tree.right, p, xmin, tree.key.y(), xmax, ymax, !cX);
                }
            }
        }
        return tree;
    }

    // does the kdtree contain point p?
    // return false if the set is empty
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Node tree = root;
        while (tree != null) {
            if (tree.isKeyEqual(p)) {
                return true;
            }
            if (tree.isSmallerThanKey(p)) {
                tree = tree.left;
            } else {
                tree = tree.right;
            }
        }
        return false;
    }

    // draw all points to standard draw in black and
    // the subdivisions in red (vertical) and blue(horizontal)
    public void draw() {
        drawHelper(root);
    }
    
    // a helper method for draw
    private void drawHelper(Node tree) {
        if (tree == null) {
            return;
        }
        StdDraw.setPenRadius(lineThickness);
        if (tree.compareX) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(tree.key.x(), tree.rect.ymin(), tree.key.x(), tree.rect.ymax());
            drawHelper(tree.left);
            drawHelper(tree.right);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(tree.rect.xmin(), tree.key.y(), tree.rect.xmax(), tree.key.y());
            drawHelper(tree.left);
            drawHelper(tree.right);
        }
        StdDraw.setPenRadius(pointThickness);
        StdDraw.setPenColor(StdDraw.BLACK);
        tree.key.draw();
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        LinkedQueue<Point2D> pointsInRange = new LinkedQueue<>();
        if (root != null) {
            findPointsInRange(root, rect, pointsInRange);
        }
        return pointsInRange;
    }

    // a helper method for range
    private void findPointsInRange(Node tree, RectHV rect, LinkedQueue<Point2D> pts) {
        if (rect.intersects(tree.rect)) {
            if (rect.contains(tree.key)) {
                pts.enqueue(tree.key);
            }
            if (tree.left != null) {
                findPointsInRange(tree.left, rect, pts);
            }
            if (tree.right != null) {
                findPointsInRange(tree.right, rect, pts);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return null;
        }
        Point2D nearestP = findNearest(root, null, p);
        return nearestP;
    }

    private Point2D findNearest(Node tree, Point2D nearestP, Point2D p) {
        if (tree.isKeyEqual(p)) {
            return tree.key;
        }
        if (nearestP == null || p.distanceSquaredTo(nearestP) > p.distanceSquaredTo(tree.key)) {
            nearestP = tree.key;
        }

        if (tree.isSmallerThanKey(p)) {
            if (tree.left != null && tree.left.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearestP)) {
                nearestP = findNearest(tree.left, nearestP, p);
            }
            if (tree.right != null && tree.right.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearestP)) {
                nearestP = findNearest(tree.right, nearestP, p);
            }
        } else {
            if (tree.right != null && tree.right.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearestP)) {
                nearestP = findNearest(tree.right, nearestP, p);
            }

            if (tree.left != null && tree.left.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearestP)) {
                nearestP = findNearest(tree.left, nearestP, p);
            }
        }
        return nearestP;
    }

    // unit testing of the methods (optional) 
    public static void main(String[] args) {
        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);

        KdTree tree = new KdTree();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            tree.insert(p);
        }

        tree.draw();
        System.out.println(tree.nearest(new Point2D(0.13, 0.67)));
    }
 }
