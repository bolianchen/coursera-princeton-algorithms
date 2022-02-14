public class CountInversions {

    // brute-force method with O(n^2) runtime
    public static int countInvBF(int[] arr) {
        int invCount = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    invCount++;
                }
            }
        }
        return invCount;
    }

    // O(nlogn) runtime by using bottom-up Merge Sort
    public static int countInvByMergeSort(int[] arr) {
        int invCount = 0;
        int N = arr.length;
        int[] aux = new int[N];
        for (int sz = 1; sz < N; sz = sz + sz) {
            for (int lo = 0; lo < N-sz; lo += sz+sz) {
                invCount += countInvWhileMerging(arr, aux, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
            }
        }
        return invCount;
    }

    // a helper function which count inversions while merging two sorted array
    private static int countInvWhileMerging(int[] arr, int[] aux, int lo, int mid, int hi) {
        int countInv = 0;
        int i = lo;
        int j = mid+1;
        for (int k = lo; k<=hi; k++) {
            aux[k] = arr[k];
        }
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                arr[k] = aux[j++];
            } else if (j > hi) {
                arr[k] = aux[i++];
            } else if (aux[j] < aux[i]) {
                countInv += mid-i+1;
                arr[k] = aux[j++];
            } else {
                arr[k] = aux[i++];
            }
        }
        return countInv;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{8, 4, 2, 1};
        System.out.println(countInvByMergeSort(arr));
    }
}
