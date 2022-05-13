import java.util.Arrays;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.AcyclicSP;

import edu.princeton.cs.algs4.Picture; 


public class SeamCarver {

    private Picture currPic;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException(
                    "picture argument cannot be null");
        }
        currPic = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return currPic;
    }

    // width of current picture
    public int width() {
        return currPic.width();
    }

    // height of current picture
    public int height() {
        return currPic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 ||  x > width() - 1 || y > height() - 1) {
            throw new IllegalArgumentException("x or y are out of range");
        }
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
            return 1000;
        }

        double xGrad = computeXGrad(x, y);
        double yGrad = computeYGrad(x, y);

        return Math.sqrt(xGrad + yGrad);
    }

    private double computeXGrad(int x, int y) {
        int[] rightRGB = parsePixRGB(x + 1, y);
        int rightR = rightRGB[0];
        int rightG = rightRGB[1];
        int rightB = rightRGB[2];

        int[] leftRGB = parsePixRGB(x - 1, y);
        int leftR = leftRGB[0];
        int leftG = leftRGB[1];
        int leftB = leftRGB[2];

        double xGrad = (
                Math.pow(rightR - leftR, 2) + Math.pow(rightG - leftG, 2) +
                Math.pow(rightB - leftB, 2));

        return xGrad;
    }

    private double computeYGrad(int x, int y) {
        int[] bottomRGB = parsePixRGB(x, y + 1);
        int bottomR = bottomRGB[0];
        int bottomG = bottomRGB[1];
        int bottomB = bottomRGB[2];

        int[] topRGB = parsePixRGB(x, y - 1);
        int topR = topRGB[0];
        int topG = topRGB[1];
        int topB = topRGB[2];

        double yGrad = (
                Math.pow(bottomR - topR, 2) + Math.pow(bottomG - topG, 2) +
                Math.pow(bottomB - topB, 2));

        return yGrad;
    }

    private int[] parsePixRGB(int x, int y) {
        int pixColor = currPic.getRGB(x, y);
        int r = (pixColor >> 16) & 0xFF;
        int g = (pixColor >> 8) & 0xFF;
        int b = (pixColor >> 0) & 0xFF;
        return new int[]{r, g, b};
    }


    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int row;
        int col;
        double pixEnergy;
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(2 + width() * height());
        for (int v = 0; v <= width() * height(); v += 1) {
            if (v == 0) {
                for (row = 0; row < height(); row += 1) {
                    col = 0;
                    int w = cvtPixCoordsToVertex(col, row);
                    DirectedEdge e = new DirectedEdge(v, w, 0);
                    g.addEdge(e);
                }
            } else {
                row = (v - 1) / width();
                col = (v - 1) % width();
                pixEnergy = energy(col, row);
                if (col == width() - 1) {
                    DirectedEdge e = new DirectedEdge(
                            v, 1 + width() * height(), pixEnergy);
                    g.addEdge(e);
                } else {
                    for (int i = -1; i <= 1; i += 1) {
                        if (row + i >= 0 && row + i <= height() -1) {
                            int w = cvtPixCoordsToVertex(col + 1, row + i);
                            DirectedEdge e = new DirectedEdge(v, w, pixEnergy);
                            g.addEdge(e);
                        }
                    }
                }
            }
        }
        AcyclicSP asp = new AcyclicSP(g, 0);
        int[] seam = new int[width()];
        int idx = 0;
        for (DirectedEdge e : asp.pathTo(1 + width() * height())) {
            if (e.from() != 0) {
                seam[idx] = (e.from() - 1) / width();
                idx += 1;
            }
        }
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int row;
        int col;
        double pixEnergy;
        EdgeWeightedDigraph g = new EdgeWeightedDigraph(2 + width() * height());
        for (int v = 0; v <= width() * height(); v += 1) {
            if (v == 0) {
                for (col = 0; col < width(); col += 1) {
                    row = 0;
                    int w = cvtPixCoordsToVertex(col, row);
                    DirectedEdge e = new DirectedEdge(v, w, 0);
                    g.addEdge(e);
                }
            } else {
                row = (v - 1) / width();
                col = (v - 1) % width();
                pixEnergy = energy(col, row);
                if (row == height() - 1) {
                    DirectedEdge e = new DirectedEdge(
                            v, 1 + width() * height(), pixEnergy);
                    g.addEdge(e);
                } else {
                    for (int i = -1; i <= 1; i += 1) {
                        if (col + i >= 0 && col + i <= width() -1) {
                            int w = cvtPixCoordsToVertex(col + i, row + 1);
                            DirectedEdge e = new DirectedEdge(v, w, pixEnergy);
                            g.addEdge(e);
                        }
                    }
                }
            }
        }
        AcyclicSP asp = new AcyclicSP(g, 0);
        int[] seam = new int[height()];
        int idx = 0;
        for (DirectedEdge e : asp.pathTo(1 + width() * height())) {
            if (e.from() != 0) {
                seam[idx] = (e.from() - 1) % width();
                idx += 1;
            }
        }
        return seam;
    }

    private int cvtPixCoordsToVertex(int col, int row) {
        return 1 + row * width() + col;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

        checkSeamValidity(seam, "horizontal");

        // create a all-black Picture whose height is less than currPic by 1
        Picture newPic = new Picture(width(), height() - 1);
        // set its color according to the color the kept part of currPic
        int rowNewPic;
        for (int col = 0; col < width(); col += 1) {
            rowNewPic = 0;
            for (int row = 0; row < height(); row += 1) {
                if (row != seam[col]) {
                    newPic.set(col, rowNewPic, currPic.get(col, row));
                    rowNewPic += 1;
                }
            }
        }
        currPic = newPic;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

        
        checkSeamValidity(seam, "vertical");

        // create a all-black Picture whose width is less than currPic by 1
        Picture newPic = new Picture(width() - 1, height());
        // set its color according to the color the kept part of currPic
        int colNewPic;
        for (int row = 0; row < height(); row += 1) {
            colNewPic = 0;
            for (int col = 0; col < width(); col += 1) {
                if (col != seam[row]) {
                    newPic.set(colNewPic, row, currPic.get(col, row));
                    colNewPic += 1;
                }
            }
        }
        currPic = newPic;
    }

    private void checkSeamValidity(int[] seam, String orientation) {
        if (seam == null) {
            throw new IllegalArgumentException("seam cannot be null");
        }

        int entryUpperBound = -1;

        if (orientation == "horizontal") {
            if (seam.length != width()) {
                throw new IllegalArgumentException("wrong seam length");
            }
            entryUpperBound = height();
        } else if (orientation == "vertical") {
            if (seam.length != height()) {
                throw new IllegalArgumentException("wrong seam length");
            }
            entryUpperBound = width();
        } else {
            throw new IllegalArgumentException();
        }

        int prev = -1;
        for (int i: seam) {
            if (i < 0 || i >= entryUpperBound) {
                throw new IllegalArgumentException("seam entry out of range");
            }
            if (prev != -1) {
                if (i - prev < -1 || i - prev > 1) {
                    throw new IllegalArgumentException(
                            "adjacent seam entries differ more than 1");
                }
            }
            prev = i;
        }
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        SeamCarver sc = new SeamCarver(pic);

        // test energy computation
        //int col = Integer.parseInt(args[1]);
        //int row = Integer.parseInt(args[2]);
        //double scEnergy = sc.energy(col, row);
        //System.out.println(scEnergy);
        int[] s = sc.findVerticalSeam();
        sc.removeVerticalSeam(s);


    }
}
