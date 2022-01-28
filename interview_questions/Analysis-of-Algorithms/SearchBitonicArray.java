import java.util.*;

/* 
 * SearchBitonicArray.find is to check if an bitonic array contains target
 * whose worst runtime is 3*lgn
 */
public class SearchBitonicArray {

    // passed to the binarySearch method to search key in an array sorted in descending order
    private static class DescendingComparator implements Comparator<Integer> {
        public int compare(Integer o1, Integer o2) {
            return -1*Integer.compare(o1,o2);
        }
    }

    // check if target can be found in a bitonic array
    public static boolean find(Integer[] bitonic, int target) {
        Comparator<Integer> dc = new DescendingComparator();
        int peakIdx = findPeak(bitonic);
        if (target == bitonic[peakIdx]) {
            return true;
        }
        if (Arrays.binarySearch(bitonic, 0, peakIdx, target)>=0 || 
            Arrays.binarySearch(bitonic, peakIdx+1, bitonic.length, target, dc)>=0) {
                return true;
        }
        return false;
    }

    // a helper method to find the index of the peak in the bitonic array
    public static int findPeak(Integer[] bitonic) {
        int N = bitonic.length;
        int start = 0;
        int end = N-1;
        int mid = (start + end)/2;
        while (mid>start && mid<end) {
            if (bitonic[mid] > bitonic[mid-1] && bitonic[mid] > bitonic[mid+1]) {
                break;
            } else if (bitonic[mid] > bitonic[mid-1]) {
                start = mid+1;
                mid = (start + end)/2;
            } else {
                end = mid-1;
                mid = (start + end)/2;
            }
        }
        return mid;
    }

    public static void main(String[] args) {
        
        Integer[] bitonic = new Integer[]{1, 3, 7, 5, 2, 0};
        int target = Integer.parseInt(args[0]);
        System.out.println(find(bitonic, target));
    }
}
