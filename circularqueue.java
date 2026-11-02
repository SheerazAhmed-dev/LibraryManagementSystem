//enqueue, dequeue, peek, isEmpty, isFull, size, display

class circularqueue<T> {
    int array[];
    int front;
    int rear = -1;
    int capacity;
    int size;

    circularqueue(int capacity) {

        this.capacity = capacity;

        array = new int[capacity];
        this.size = 0;
        this.front = 0;
    }

    boolean isEmpty() {

        return size == 0;
    }

    boolean isfull() {

        return size == capacity;

    }

    void enqueue(T data) {
        if (isfull()) {
            System.out.print("queue is full");
            return;
        }

        rear = (rear + 1) % capacity;
        array[rear] = data;
        size++;
    }

    T dequeue() {
        if (isEmpty()) {
            System.out.print("queue is empty");
            return -1;
        }
        T value = (T) array[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    int peek() {
        if (isEmpty()) {
            System.out.print("queue is empty");
            return -1;
        }
        return array[front];
    }

}
