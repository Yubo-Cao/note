/**
 * 使用数组模拟一个 Queue
 */
public class ArrayQueue {
    // 最大容量
    private int sz;
    // 队列头部的前一个位置
    private int front = -1;
    // 直接指向队列尾部，包含队列最后一个数据
    private int rear = -1;
    // 存放数据
    private int[] array;

    public ArrayQueue(int sz){
        // 接受最大容量
        this.sz = sz;
        array = new int[sz];
    }

    public boolean isFull(){
        return rear == sz - 1;
    }

    public boolean isEmpty(){
        return front == rear;
    }






    public static void main(String[] args) {

    }
}
