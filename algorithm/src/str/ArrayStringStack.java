package str;

public class ArrayStringStack {
    private String[] s;
    // Use array to store items
    // Choose array and link is a fundamental decision
    // Fundamental defect is, you have declare size before hand. And stack may overflow

    private int N = 0;

    public ArrayStringStack(int capacity){
        // cheat: require size of stack to be known beforehand
        // but won't work most of time

        s = new String[capacity];
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public void push(String item){
        // may overflow
        // nullable
        s[N++] = item; // Todo: Dynamic expandable array
    }

    public String pop(){
        // may underflow
        // loiter, hold a reference to an object, when it is no longer needed
        String item = s[--N];
        s[N] = null;
        return item; // This avoid loiter and alow GC to reclaim memory
    }
}
