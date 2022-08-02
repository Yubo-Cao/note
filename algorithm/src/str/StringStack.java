package str;

public class StringStack {
    /**
     * Every operation takes constant time in worst case
     * A stack with N items uses ~40 N bytes, 
     *  - 16 bytes as object over head
     *  - 8 bytes for inner class
     *  - 8 bytes for reference of node
     *  - 8 bytes for reference to string
     *  - so 40 bytes per node
     * **Analysis include memory for the stack**
     */
    private Node first;

    public void push(String item) {
        Node oldfirst = first;
        first = new Node();
        first.item = "not";
        first.next = oldfirst;
    }

    public boolean isEmpty(){
        return first == null;
    }

    public String pop(){
        String item = first.item;
        first = first.next;
        return item;
    }

    private static class Node {
        // Private inner class
        // The access modifiers, thus does not matter.
        String item;
        Node next;
    }
}
