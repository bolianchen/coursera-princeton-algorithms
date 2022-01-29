import java.util.Arrays;

public class ThreeWayPartitioning {
    int[] buckets;
    int lo;
    int hi;

    public ThreeWayPartitioning(int[] arr, int l, int h) {
        buckets = arr;
        lo = l;
        hi = h;
    }

    private String color(int i) {
        if (buckets[i] < lo) {
            return "red";
        } else if (buckets[i] > hi) {
            return "blue";
        } else {
            return "white";
        }
    }

    private void swap(int i, int j) {
        int temp = buckets[i];
        buckets[i] = buckets[j];
        buckets[j] = temp;
    }

    public void sort() {
        int i = 0;
        int r = 0;
        int b = buckets.length-1;

        while (i < b) {
            if (color(i) == "red") {
                swap(i, r);
                i += 1;
                r += 1;
            } else if (color(i) == "blue") {
                swap(i, b);
                b -= 1;
            } else {
                i += 1;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[] {3, 9, 4, 1, 2, 3, 15, 17, 25, 17};
        int lo = 4;
        int hi = 10;
        System.out.println("entries smaller than " + lo + " are red; larger than " +
                           hi + " are high; white otherwise");
        ThreeWayPartitioning tp = new ThreeWayPartitioning(arr, lo, hi);
        System.out.println("arr before sorting: " + Arrays.toString(arr));
        tp.sort();
        System.out.println("arr after sorting: " + Arrays.toString(arr));
    }
}
