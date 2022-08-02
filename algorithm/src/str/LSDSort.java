package str;

import java.util.Arrays;

public class LSDSort {
    /*
     * This sort use KeyIndexCounting method to sort Strings
     * It require:
     * 1. String must be equal length
     * 2. String must only contain extended ASCII characters
     * It does by using KeyIndexCounting method to compare each characters
     * of String. Which obviously, would cause the String to be sorted
     * 
     * This sort would sort because, for any two String in result
     * They have different position because
     * 1. The first character is different
     * 2. The first character is the same, the second character is different
     * 3. By induction, the string that have different position is sorted according
     * to
     * their first different character, or randomly placed because there is no
     * different character.
     * 
     * It is linear time, 7WN + 3WN + N + W array accesses, where W is length.
     * Usually, R is far smaller than N, and thus ~WN time.
     * Therefore, linear in size of the input.
     */

    public static void sort(String[] a, int w) {
        // Sort a[] on leading w characters
        int N = a.length;
        int R = 256;
        String[] aux = new String[N];

        for (int d = w - 1; d >= 0; d--) {
            int[] count = new int[R + 1];
            for (String str : a) {
                count[str.charAt(d) + 1]++;
            }
            for (int r = 1; r < R; r++) {
                count[r] = count[r] + count[r - 1];
            }
            for (String str : a) {
                aux[count[str.charAt(d)]++] = str;
            }
            System.arraycopy(aux, 0, a, 0, aux.length);
        }
    }

    public static void main(String[] args) {
        String[] testStrings = new String[] {
                "4PGC938", "2IYE230", "3CIO720", "1ICK750", "1OHV845", "4JZY524", "1ICK750", "3CIO720", "1OHV845",
                "1OHV845", "2RLA629", "2RLA629", "3ATW723"
        };
        sort(testStrings, 7);
        System.out.println(Arrays.toString(testStrings));
    }
}
