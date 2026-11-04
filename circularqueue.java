//enqueue, dequeue, peek, isEmpty, isFull, size, display

class circularqueue<T> {
    Object array[];
    int front;
    int rear = -1;
    int capacity;
    int size;

    circularqueue() {

        this.capacity = 10;

        array = new Object[capacity];
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
            resize();
        }

        rear = (rear + 1) % capacity;
        array[rear] = data;
        size++;
    }

    void resize() {
        int newcapacity = capacity * 2;
        Object newarray[] = new Object[newcapacity];
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
            System.out.print("queue is empty");
            return null;
        }
        T value = (T) array[front];
        front = (front + 1) % capacity;
        size--;
        return value;
    }

    T peek() {
        if (isEmpty()) {
            System.out.print("queue is empty");
            return null;
        }
        return (T) array[front];
    }

    int size() {
        return size;
    }

    int capacity() {
        return capacity;
    }

    void remove() {

        while (!isEmpty()) {
            dequeue();

        }

    }

}
