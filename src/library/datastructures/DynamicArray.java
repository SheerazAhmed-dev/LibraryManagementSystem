package library.datastructures;

public class DynamicArray<T>{
    private static final int INITIAL_CAPACITY = 10;

    private T[] elements;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public DynamicArray(){
        capacity = INITIAL_CAPACITY;
        size = 0;
        elements = (T[]) new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity){
        T[] newArray = (T[]) new Object[newCapacity];

        for(int i=0; i<size; i++){
            newArray[i] = elements[i];
        }

        elements = newArray;
        capacity = newCapacity;
    }

    private void checkIndex(int index){
        if(index<0 || index>=size){
            throw new IndexOutOfBoundsException("Invalid index: " + index + ", Size: " + size);
        }
    }

    private void checkElement(T element){
        if(element == null){
            throw new IllegalArgumentException("Null element not allowed!");
        }
    }

    public void add(T element){
        checkElement(element);

        if(size == capacity){
            resize(capacity*2);
        }
        elements[size] = element;
        size++;
    }

    public void insert(int index, T element){
        checkElement(element);

        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("Invalid insert index: " + index);
        }

        if(size == capacity){
            resize(capacity*2);
        }

        for(int i=size; i>index; i--){
            elements[i] = elements[i-1];
        }
        elements[index] = element;
        size++;
    }

    public T get(int index){
        checkIndex(index);
        return elements[index];
    }

    public T set(int index, T element){
        checkIndex(index);
        
        checkElement(element);

        T oldElement = elements[index];
        elements[index] = element;

        return oldElement;
    }

    public T remove(int index){
        checkIndex(index);

        T removedElement = elements[index];
        
        for(int i=index; i<size-1; i++){
            elements[i] = elements[i+1];
        }

        elements[size-1] = null;
        size--;
        shrinkIfNeeded();

        return removedElement;
    }

    private void shrinkIfNeeded(){
        if(capacity>INITIAL_CAPACITY && size<=capacity/4){
            int newCapacity;

            if(capacity/2 < INITIAL_CAPACITY){
                newCapacity = INITIAL_CAPACITY;
            }else{
                newCapacity = capacity/2;
            }

            resize(newCapacity);
        }
    }

    public int indexOf(T element){
        if(element == null){
            return -1;
        }

        for(int i=0; i<size; i++){
            if(elements[i].equals(element)){
                return i;
            }
        }

        return -1;
    }

    public boolean contains(T element){
        return indexOf(element) != -1;
    }

    public int size(){
        return size;
    }

    public int capacity(){
        return capacity;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){
        for(int i=0; i<size; i++){
            elements[i] = null;
        }

        size=0;
        if(capacity>INITIAL_CAPACITY){
            resize(INITIAL_CAPACITY);
        }
    }

    public void display(){
        System.out.print("[");
        for(int i=0; i<size; i++){
            System.out.print(elements[i]);
            if(i<size-1){
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}
