package str;

import java.util.Arrays;

public class MSDSort {
    /*
     * This sort the string use the first character, paritition to alphabet chart
     * number of sub array and sort them again.
     */
    private static int R = 256;
    private static final int M = 15;
    /*
     * Consider easy case, where all keys are distinct
     * Then N subarrys, NR array access
     * However, N/M can be achieved, if put M to cut off. However, for each
     * M ^ 2 / 4 compare is needed, which are NM/4 compares
     * So NR or NM/4, to minimize, choose M be porportional to sqrt(R)
     * 
     * So main problem with it, is just we want data to be random.
     */
    private static String[] aux;

    private static int charAt(String s, int d) {
        // End behavior.
        // -1 is end of String
        // char is normal.
        // In algorithm, 0 is ignored, and 1 stand for strings
        // start with first alphabet char, and need more sort.
        if (d < s.length()) {
            return s.charAt(d);
        } else {
            return -1;
        }
    }

    public static void sort(String[] a) {
        int N = a.length;
        aux = new String[N];
        sort(a, 0, N - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        // For very small array, use Insertion sort
        if (hi <= lo + M) {
            // Insertion
            for (int i = 0; i < a.length; i++) {
                // Use substring to prevent re-exam characters that we know to be equal
                for (int j = i; j >= 1 && a[j - 1].substring(d).compareTo(a[j].substring(d)) > 0; j--) {
                    var tmp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = tmp;
                }
            }
            return;
        }
        int[] count = new int[R + 2];
        for (int i = lo; i <= hi; i++) {
            count[charAt(a[i], d) + 2]++;
            // Frquency count
            // 1 is done, 2 .... R + 1 are str with alphabet.indexOf(charAt(d)) == index
        }
        for (int r = 0; r < R + 1; r++) {
            count[r + 1] += count[r];
            // 0 is done, 1 ... R are position of aux where alphabet.indexOf(charAt(d))
            // should be
        }
        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }

        // After that distribution, all the value would be start index of subarray
        // whose dth character value is r. Notice all count[r] advanced one

        // finally, we put those back to a to sort
        // notice aux is copied from 0
        System.arraycopy(aux, 0, a, lo, hi - lo + 1);
        for (int r = 0; r < R; r++) {
            // Recursively sort subarrays that still need that.
            // therefore, start from 0 and sort subarray
            // count[R] is end index of r-1
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
        }
    }

    public static void main(String[] args) {
        String[] testStrings = "she sells seashells by the seashore the shells she sells are surely seashells"
                .split("\\s+");
        sort(testStrings);
        System.out.println(Arrays.toString(testStrings));
    }

    /*
     * Use that implementation, two pitfalls
     * - You don't want to use large R. That's a problem cause everytime create R+2 is
     *   huge overhead
     * - You must use Insertion Sort cutoff. Stack is awful
     * - Equal keys cause problems too. Since this would literally cause -1, -1, -1,
     *   recursion, until insertion sort comes to terminate it.
     *  - To sort N random strings from an R character alphabets, MSD string sort
     *    exaines about N log_R N characters on average.
     * - 8N + 3R ~ 7wN + 3WR, w is average string length, W is max length of keys
     */
}
