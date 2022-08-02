package str;
import java.util.Arrays;

public class KeyIndexSort {
    public static void main(String[] args) {
        /*
         * Key indexed counting/sorting
         */
        Student[] testStudents = new Student[] {
                new Student("Anderson", 2), new Student("Brown", 3), new Student("Davis", 3), new Student("Garcia", 4),
                new Student("Harris", 1), new Student("Jackson", 3), new Student("Johnson", 4), new Student("Jones", 3),
                new Student("Martin", 1), new Student("Martinez", 2), new Student("Miller", 2), new Student("Moore", 1),
                new Student("Robinson", 2), new Student("Smith", 4), new Student("Taylor", 3), new Student("Thomas", 4),
                new Student("Thompson", 4), new Student("White", 2), new Student("Williams", 3),
                new Student("Willson", 4)
        }; // N
        int[] count = new int[6]; // R
        for (Student student : testStudents) {
            count[student.section() + 1]++; // + 2N
        }
        for (int i = 1; i < count.length; i++) {
            count[i] = count[i] + count[i - 1]; // + 2R
        }
        Student[] sortedStudents = new Student[testStudents.length];
        for (Student student : testStudents) { // + N
            sortedStudents[count[student.section()]++] = student; // + 3N
        }
        // + 2N
        System.arraycopy(sortedStudents, 0, testStudents, 0, sortedStudents.length);

        System.out.println(Arrays.toString(testStudents));
        /*
         * Key-indexed counting uses 8N + 3R + 1 array accesses to stably sort N items
         * whose keys are integers between 0 and R - 1
         * 
         * (R = number of distinct keys)
         * (N = number of items)
         * 
         * First init, which is N + R + 1 array accesses
         * Then count, which 2N array accesses
         * Then self addition, 2R array accesses
         * Then sort, 3N accesses
         * Then copy back, 2N accesses
         * 
         * This sort is linear time, because it does not rely on any compareTo methods
         */
    }
}

record Student(String name, int section) {

}