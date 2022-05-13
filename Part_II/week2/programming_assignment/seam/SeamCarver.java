import java.util.Arrays;
import java.util.LinkedList;
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
        return new Picture(currPic);
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
        transposeCurrPic();
        int[] seam = findVerticalSeam();
        transposeCurrPic();
        return seam;
    }

    private void transposeCurrPic() {
        Picture transPic = new Picture(height(), width());
        for (int row = 0; row < height(); row += 1) {
            for (int col = 0; col < width(); col += 1) {
                transPic.setRGB(row, col, currPic.getRGB(col, row));
            }
        }
        currPic = transPic;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        // initialize a energy map
        double[][] energy = new double[width()][height()];
        for (int row = 0; row < height(); row += 1) {
            for (int col = 0; col < width(); col += 1) {
                energy[col][row] = energy(col, row);
            }
        }

        double[][] distTo = new double[width()][height()];
        int[][] colTo = new int[width()][height()];
        for (int row = 0; row < height(); row += 1) {
            for (int col = 0; col < width(); col += 1) {
                if (row == 0) {
                    // all the pixels at 0th row are defined as sources
                    distTo[col][row] = 0;
                    colTo[col][row] = -1;
                } else {
                    distTo[col][row] = Double.POSITIVE_INFINITY;
                }
            }
        }

        // do relaxation
        for (int row = 0; row < height() - 1; row += 1) {
            for (int col = 0; col < width(); col += 1) {
                // relax at most 3 edges for each pixel
                for (int i = -1; i <= 1; i += 1) {
                    if (col + i >= 0 && col + i < width()) {
                        if (distTo[col + i][row + 1] >
                                distTo[col][row] + energy[col + i][row + 1]) {
                            distTo[col + i][row + 1] = 
                                distTo[col][row] + energy[col + i][row + 1];
                            colTo[col + i][row + 1] = col;
                        }
                    }
                }
            }
        }

        // find the colum index for the minimum distance at the bottom row
        int colOfMin = 0;
        double minDist = distTo[colOfMin][height() - 1];
        for (int col = 1; col < width(); col += 1) {
            if (distTo[col][height() - 1] < minDist) {
                colOfMin = col;
                minDist = distTo[col][height() - 1];
            }
        }


        // collect seam entries
        int[] seam = new int[height()];
        for (int row = height() - 1; row >= 0; row -= 1) {
            seam[row] = colOfMin;
            colOfMin = colTo[colOfMin][row];
        }

        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        transposeCurrPic();
        checkSeamValidity(seam, "vertical");
        removeVerticalSeam(seam);
        transposeCurrPic();
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
                    newPic.setRGB(colNewPic, row, currPic.getRGB(col, row));
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
        int[] vs = sc.findVerticalSeam();
        System.out.println(Arrays.toString(vs));
        int[] hs = sc.findHorizontalSeam();
        System.out.println(Arrays.toString(hs));


    }
}
