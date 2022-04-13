import java.util.Arrays;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/* 
 * Performance requirement: the order of growth of the running time should be n^4
 * space usage should be proportional to n plus the number of line segments returned
 */
public class BruteCollinearPoints {
    private int numSegs=0;
    private LinkedQueue<LineSegment> lineSegs = new LinkedQueue<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        checkCornerCases(points);

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    Point p = points[i];
                    Point q = points[j];
                    Point r = points[k];
                    Double slopePQ = p.slopeTo(q);
                    Double slopePR = p.slopeTo(r);
                    // ignore the 4th loop if the first 3 points are not collinear
                    if (!slopePQ.equals(slopePR)) {
                        continue;
                    }
                    for (int l = k + 1; l < points.length; l++) {
                        Point s = points[l];
                        Double slopePS = p.slopeTo(s);
                        if (slopePQ.equals(slopePS)) {
                            Point[] pts = new Point[]{p, q, r, s};
                            Arrays.sort(pts);
                            lineSegs.enqueue(new LineSegment(pts[0], pts[3]));
                            numSegs += 1;
                        }
                    }
                }
            }
        }
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
	BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	for (LineSegment segment : collinear.segments()) {
	    StdOut.println(segment);
	    segment.draw();
	}
	StdDraw.show();
    }

}
