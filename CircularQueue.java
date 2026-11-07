//enqueue, dequeue, peek, isEmpty, isFull, size, display
package library.datastructures;

public class CircularQueue<T> {
    private T array[];
    private int front;
    private int rear = -1;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public CircularQueue() {

        this.capacity = 10;

        array = (T[]) new Object[capacity];
        this.size = 0;
        this.front = 0;
    }

    public boolean isEmpty() {

        return size == 0;
    }

    public boolean isFull() {

        return size == capacity;

    }

    public void enqueue(T data) {
        if(data == null){
            throw new IllegalArgumentException("Null elements are not allowed!");
        }
        if (isFull()) {
            resize();
        }

        rear = (rear + 1) % capacity;
        array[rear] = data;
        size++;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newcapacity = capacity * 2;
        T[] newarray = (T[]) new Object[newcapacity];
        for (int i = 0; i < size; i++) {
            newarray[i] = array[(front + i) % capacity]; // push ta kayo new arraay mey
        }
        array = newarray; // naming change
        capacity = newcapacity;
        front = 0;
        rear = size - 1;

    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        T value = array[front];
        array[front] = null;
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        return array[front];

    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public void clear() {

        while (!isEmpty()) {
            dequeue();

        }

    }

    public void display() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        for (int i = 0; i < size; i++) {
            System.out.print(array[(front + i) % capacity] + " ");

        }
        System.out.println();
    }
}
