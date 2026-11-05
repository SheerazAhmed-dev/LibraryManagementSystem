//enqueue, dequeue, peek, isEmpty, isFull, size, display

class circularqueue<T> {
    T array[];
    int front;
    int rear = -1;
    int capacity;
    int size;

    @SuppressWarnings("unchecked")
    circularqueue() {

        this.capacity = 10;

        array = (T[]) new Object[capacity];
        this.size = 0;
        this.front = 0;
    }

    boolean isEmpty() {

        return size == 0;
    }

    boolean isFull() {

        return size == capacity;

    }

    void enqueue(T data) {
        if (isFull()) {
            resize();
        }

        rear = (rear + 1) % capacity;
        array[rear] = data;
        size++;
    }

    void resize() {
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

    T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        T value = (T) array[front];
        array[front] = null;
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        return (T) array[front];

    }

    int size() {
        return size;
    }

    int capacity() {
        return capacity;
    }

    void clear() {

        while (!isEmpty()) {
            dequeue();

        }

    }

    void display() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        for (int i = 0; i < size; i++) {
            System.out.print(array[(front + i) % capacity]);

        }
        System.out.println();
    }
}
