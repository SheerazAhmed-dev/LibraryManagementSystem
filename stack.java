class stack<T> {
    private T array[];
    private int top;
    private int size;
    private int capacity;

    stack(int capacity) {
        this.capacity = capacity;
        array = (T[]) new Object[capacity];
        this.size = 0;
        this.top = -1;
    }

    public boolean isfull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(T data) {
        if (isfull()) {
            resize();

        }

        array[++top] = data;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalArgumentException("stack is empty");

        }
        size--;
        return array[top--];
    }

    public T peak() {
        if (isEmpty()) {
            throw new IllegalArgumentException("stack is empty");

        }
        return array[top];
    }

    void resize() {
        int newcapacity = capacity * 2;
        T[] newarray = (T[]) new Object[newcapacity];
        for (int i = 0; i < array.length; i++) {
            newarray[i] = array[i];

        }
        array = newarray;
        capacity = newcapacity;

    }

}