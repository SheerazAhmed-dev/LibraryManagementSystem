
package library.datastructures;

public class CustomStack<T> {
    private T array[];
    private int top;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public CustomStack() {
        this.capacity = 10;
        array = (T[]) new Object[capacity];
        this.size = 0;
        this.top = -1;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot push null onto the stack");
        }

        if (isFull()) {
            resize();
        }

        array[++top] = data;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("stack is empty");
        }
        T element = array[top];
        array[top] = null;
        top--;
        size--;
        return element;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("stack is empty");
        }
        return array[top];
    }

    public void clear() {
        for (int i = top; i >= 0; i--) {
            array[i] = null;
        }
        top = -1;
        size = 0;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Stack is empty!");
            return;
        }

        for (int i = top; i >= 0; i--) {
            System.out.println(array[i]);
        }
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newcapacity = capacity * 2;
        T[] newarray = (T[]) new Object[newcapacity];
        for (int i = 0; i < size; i++) {
            newarray[i] = array[i];
        }
        array = newarray;
        capacity = newcapacity;
    }
}
