import java.util.Arrays;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/* 
 * Performance requirement: the order of growth of the running time should be n^2 log n
 * space usage should be proportional to n plus the number of line segments returned
 */
public class FastCollinearPoints {
    private int numSegs=0;
    private LinkedQueue<LineSegment> lineSegs = new LinkedQueue<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkCornerCases(points);
        int numPts = points.length;

        // create a copy of points to be sorted by y-coordinate (natural order of the Point data type)
        // in order to do binary search later to remove redundant line segments
        Point[] sortedPts = new Point[numPts];
        System.arraycopy(points, 0, sortedPts, 0, numPts);
        Arrays.sort(sortedPts);

        // create another copy of points to be sorted by slope repeatedly
        Point[] ptsCopy = new Point[numPts];
        System.arraycopy(points, 0, ptsCopy, 0, numPts);

        // the runtime of every step in the loop should be multiplied by n
        for (int i = 0; i < numPts; i++) {
            // sort ptsCopy by slopes relative to sortedPts[i]
            // runtime ~ nlogn
            Arrays.sort(ptsCopy, 0, numPts, sortedPts[i].slopeOrder());

            // obtain the reference slope -infinity
            Double prevSlope = sortedPts[i].slopeTo(ptsCopy[0]);

            // a linked list to collect points having the same slope with 
            LinkedQueue<Point> pts = new LinkedQueue<>();

            // for each step in the loop, runtime should be multiplied by n
            // for each jth element added to the above linked list
            // at most one binary search may be conducted on sortedPts, runtime logn
            for (int j = 1; j < numPts; j++) {
                Double curSlope = sortedPts[i].slopeTo(ptsCopy[j]);
                if (curSlope.equals(prevSlope)) {
                    pts.enqueue(ptsCopy[j]);
                    // additional check at the last iteration
                    if (j == numPts-1) {
                        // add the the linesegment formed by pts if it is valid
                        addLineSeg(pts, sortedPts, i);
                    }
                } else {
                    prevSlope = curSlope;
                    // add the the linesegment formed by pts if it is valid
                    addLineSeg(pts, sortedPts, i);
                    pts = new LinkedQueue<>();
                    pts.enqueue(ptsCopy[j]);
                }
            }
        }
    }

    private void addLineSeg(LinkedQueue<Point> candidatePts, Point[] allPts, int curIdx) {
        boolean addSeg = true;
        if (candidatePts.size() >= 3) {
            for (Point pt: candidatePts) {
                if (Arrays.binarySearch(allPts, 0, curIdx, pt) >=0) {
                    addSeg = false;
                    break;
                }
            }
            if (addSeg) {
                candidatePts.enqueue(allPts[curIdx]);
                lineSegs.enqueue(parseLineSeg(candidatePts));
                numSegs += 1;
            }
        }
    }

    private LineSegment parseLineSeg(LinkedQueue<Point> pts) {
        int sz = pts.size();
        Point[] arrPts = new Point[sz];
        int i = 0;
        for (Point pt: pts) {
            arrPts[i] = pt;
            i+=1;
        }
        Arrays.sort(arrPts);
        return new LineSegment(arrPts[0], arrPts[sz-1]);
    }

    private void checkCornerCases(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        // check if any point is null, O(n) runtime
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        // check if points contain a repeated point, O(n^2) runtime
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].equals(points[j])) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
    // the number of line segments
    public int numberOfSegments() {
        return numSegs;
    }
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segs = new LineSegment[numSegs];
        int i = 0;
        for (LineSegment seg: lineSegs) {
            segs[i] = seg;
            i += 1;
        }
        return segs;
    }

    public static void main(String[] args) {

        // read the n points from a file
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
