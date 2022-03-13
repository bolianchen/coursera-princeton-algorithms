import java.util.TreeSet;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private int size;
    private Node root;

    private class Node {
        private Point2D key;
        private Node right;
        private Node left;
        public Node(Point2D p) {
            key = p;
        }
    }

    // construct an empty kdtree of points 
    public KdTree() {
        size = 0;
    }

    //is the kdtree empty?
    public boolean isEmpty() {
        return size == 0;
    }
    // number of points in the tree
    public int size() { 
        return size;
    }

    // add the point to the kdtree (if it is not already in the set)
    public void insert(Point2D p) {
        if (!contains(p)) {
            root = insertHelper(root, p, true);
            size += 1;
        }
    }

    // a helper method for insert
    private Node insertHelper(Node tree, Point2D p, boolean compareX) {
        if (tree == null) {
            return new Node(p);
        }

        Point2D treeKey = tree.key;
        if (isSmaller(p, treeKey, compareX)) {
            tree.left = insertHelper(tree.left, p, !compareX);
        } else {
            tree.right = insertHelper(tree.right, p, !compareX);
        }
        return tree;
    }

    // does the kdtree contain point p?
    public boolean contains(Point2D p) {
        boolean compareX = true;
        Node tree = root;
        while (tree != null) {
            Point2D treeKey = tree.key;
            if (p.equals(treeKey)) {
                return true;
            }
            if (isSmaller(p, treeKey, compareX)) {
                tree = tree.left;
            } else {
                tree = tree.right;
            }
            compareX = !compareX;
        }
        return false;
    }
    
    // a helper method
    // check if point#1 is smaller thant p2 according to compareX
    private boolean isSmaller(Point2D p1, Point2D p2, boolean compareX) {
        if (compareX) {
            if (p1.x() < p2.x()) {
                return true;
            }
        } else {
            if (p1.y() < p2.y()) {
                return true;
            }
        }
        return false;
    }

    // draw all points to standard draw in black and
    // the subdivisions in red (vertical) and blue(horizontal)
    public void draw() {
        drawHelper(root, true, 0.0, 0.0, 1.0, 1.0);
    }
    
    // a helper method for draw
    private void drawHelper(Node tree, boolean compareX, double x0, double y0, double x1, double y1) {
        if (tree == null) {
            return;
        }
        Point2D treeKey = tree.key;
        StdDraw.setPenRadius(0.005);
        if (compareX) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(treeKey.x(), y0, treeKey.x(), y1);
            drawHelper(tree.left, false, x0, y0, treeKey.x(), y1);
            drawHelper(tree.right, false, treeKey.x(), y0, x1, y1);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x0, treeKey.y(), x1, treeKey.y());
            drawHelper(tree.left, true, x0, y0, x1, treeKey.y());
            drawHelper(tree.right, true, x0, treeKey.y(), x1, y1);
        }
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.BLACK);
        treeKey.draw();
    }

    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        LinkedQueue<Point2D> pointsInRange = new LinkedQueue<>();
        findPointsInRange(root, rect, pointsInRange, true, 0.0, 0.0, 1.0, 1.0);
        return pointsInRange;
    }

    // a helper method for range
    private void findPointsInRange(Node tree, RectHV rect, LinkedQueue<Point2D> pts, boolean compareX,
            double x0, double y0, double x1, double y1) {
        RectHV treeRect = new RectHV(x0, y0, x1, y1);
        if (rect.intersects(treeRect)) {
            Point2D treeKey = tree.key;
            if (rect.contains(treeKey)) {
                pts.enqueue(treeKey);
            }
            if (tree.left != null) {
                if (compareX) {
                    findPointsInRange(tree.left, rect, pts, !compareX, x0, y0, treeKey.x(), y1);
                } else {
                    findPointsInRange(tree.left, rect, pts, !compareX, x0, y0, x1, treeKey.y());
                }
            }
            if (tree.right != null) {
                if (compareX) {
                    findPointsInRange(tree.right, rect, pts, !compareX, treeKey.x(), y0, x1, y1);
                } else {
                    findPointsInRange(tree.right, rect, pts, !compareX, x0, treeKey.y(), x1, y1);
                }
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (root == null) {
            return null;
        }
        Point2D nearestP = findNearest(root, null, p);
        return nearestP;
    }

    private Point2D findNearest(Node tree, Point2D nearestP, Point2D p) {
        Point2D treeKey = tree.key;
        if (nearestP == null || p.distanceTo(nearestP) > p.distanceTo(treeKey)) {
            nearestP = treeKey;
        }
        if (tree.left != null) {
            nearestP = findNearest(tree.left, nearestP, p);
        }
        if (tree.right != null) {
            nearestP = findNearest(tree.right, nearestP, p);
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

        //Point2D p1 = new Point2D(0.2, 0.5);
        //tree.insert(p1);
        //Point2D p2 = new Point2D(0.3, 0.4);
        //tree.insert(p2);
        //Point2D p3 = new Point2D(0.7, 0.6);
        //tree.insert(p3);
        //Point2D p4 = new Point2D(0.9, 0.2);
        //tree.insert(p4);
        //System.out.println(tree.contains(p1));
        //System.out.println(tree.contains(p2));
        //System.out.println(tree.contains(p3));
        //System.out.println(tree.contains(p4));
        //System.out.println(tree.contains(p5));
        //System.out.println(tree.contains(p6));
        //System.out.println(tree.contains(p7));
        //System.out.println(tree.contains(p8));
        //System.out.println(tree.contains(p9));
        //System.out.println(tree.contains(p10));
        //RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        //RectHV rect = new RectHV(0.0, 0.0, 0.5, 0.5);
        //RectHV rect = new RectHV(0.5, 0.5, 1.0, 1.0);
        //RectHV rect = new RectHV(0.5, 0.0, 1.0, 0.5);
        //Iterable<Point2D> pts = tree.range(rect);
        //for (Point2D pt: pts) {
        //    System.out.println(pt);
        //}
        tree.draw();
        System.out.println(tree.nearest(new Point2D(0.499,0.2)));
    }
 }
