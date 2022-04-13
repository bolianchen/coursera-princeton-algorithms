import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;

public class SuccessorWithDelete {
    int numElement;
    UnionFindWSCE uf;
    boolean deleted[];

    public SuccessorWithDelete(int n) {
        numElement = n;
        // add one dummy element as the sentinel
        uf = new UnionFindWSCE(n+1);
        deleted = new boolean[n+1];
    }

    public void delete(int i) {
        uf.union(i, i+1);
        deleted[i] = true;
    }

    public int successor(int i) {
        return uf.largest[uf.root(i+1)];
    }

    public void request(int i) {

        if (i >= numElement) {
            System.out.println("maximum allowable input is " + (numElement - 1));
            return;
        }

        if (deleted[i]) {
            System.out.println("element " + i + " has already been deleted");
            return;
        }

        delete(i);

        int suc = successor(i);

        if (suc == numElement) {
            System.out.println("element " + i + " is the largest in the set");
            return;
        } 

        System.out.println("successor " + suc + " found");

    }

    public static void main(String[] args) {

        System.out.print("Please enter the number of elements in the set:");
        int n = Integer.valueOf(StdIn.readString());
        SuccessorWithDelete swd = new SuccessorWithDelete(n);

        System.out.print("Please enter the element to delete:");

        while (!StdIn.isEmpty()) {
            int d = Integer.valueOf(StdIn.readString());
            swd.request(d);
        }

    }

}
