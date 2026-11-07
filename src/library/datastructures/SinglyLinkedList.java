package library.datastructures;

public class SinglyLinkedList<T>{
    private static class Node<T>{
        T data;
        Node<T> next;

        Node(T data){
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public SinglyLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    //Method to check if the element is valid or not 
    private void checkElement(T element){
        if(element == null){
            throw new IllegalArgumentException("Null elements are not allowed!");
        }
    }

    //method to check if the index is valid or not
    private void checkIndex(int index){
        if(index<0 || index>=size){
            throw new IndexOutOfBoundsException("Invalid index: " + index + ", for proper index refrence; current size is: " + size);
        }
    }

    //Adding at the begining
    public void addFirst(T element){
        checkElement(element);
        Node<T> newNode = new Node<>(element);

        if(head == null){
            head = newNode;
            tail = newNode;
        }else{
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    //adding at the last
    public void addLast(T element){
        checkElement(element);
        Node<T> newNode = new Node<>(element);

        if(head == null){
            head = newNode;
            tail = newNode;
            size++;
        }else{
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }

    //Method to add element at a specific index
    public void insert(int index, T element){
        checkElement(element);
        if(index<0 || index>size){
            throw new IndexOutOfBoundsException("Invalid index!");
        }

        if(index==0){
            addFirst(element);
            return;
        }
        if(index==size){
            addLast(element);
            return;
        }
        Node<T> newNode = new Node<>(element);
        Node<T> temp = head;
        int i=0;
        while(i<index-1){
            temp = temp.next;
            i++;
        }
        newNode.next = temp.next;
        temp.next = newNode;
        size++;
    }

    //method to remove the first element
    public T removeFirst(){
        if(head==null){
            throw new IllegalStateException("List is empty!");
        }
        T removedElement = head.data;
        head = head.next;
        size--;
        if(head==null){
            tail = null;
        }
        return removedElement;
    }

    //method to remove the last element
    public T removeLast(){
        if(head==null){
            throw new IllegalStateException("List is empty!");
        }

        if(head==tail){
            return removeFirst();
        }
    
        T removedElement = tail.data;
        Node<T> temp = head;
        while(temp.next != tail){
            temp = temp.next;
        }
        
        tail = temp;
        tail.next = null;
        size--;
        return removedElement;
    }

    //method to remove an element of specific index
    public T remove(int index){
        checkIndex(index);
        if(index==0){
            return removeFirst();
        }
        
        Node<T> temp = head;
        int i=0;
        while(i<index-1){
            temp=temp.next;
            i++;
        }
        Node<T> removedNode = temp.next;
        temp.next = removedNode.next;

        if(removedNode == tail){
            tail = temp;
        }

        size--;
        return removedNode.data;
    }

    public T get(int index){
        checkIndex(index);
        Node<T> temp = head;
        int i=0;
        while(i<index){
            temp = temp.next;
            i++;
        }
        return temp.data;
    }

    public int indexOf(T element){
        if(element == null){
            return -1;
        }
        
        Node<T> temp = head;
        int index = 0;
        while(temp != null){
            if(temp.data.equals(element)){
                return index;
            }
            temp = temp.next;
            index++;
        }
        return -1;
    }

    public boolean contains(T element){
        return indexOf(element) != -1;
    }

    public int size(){
        return size;
    }
    
    public boolean isEmpty(){
        return size==0;
    }

    public void clear(){
        head = null;
        tail = null;
        size = 0;
    }
    public void display(){
        Node<T> temp = head;
        System.out.print("Head -> ");
        while(temp != null){
            System.out.print(temp.data + " -> ");
            temp = temp.next;
        }
        System.out.println("Null");
    }
}
