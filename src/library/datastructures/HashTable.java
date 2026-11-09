package library.datastructures;
public class HashTable<K, V>{
    private static class Node<K, V>{
        K key;
        V value;
        Node<K, V> next;
        public Node(K key, V value){
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private Node<K, V>[] table;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public HashTable(){
        size = 0;
        capacity = 10;
        table = (Node<K, V>[]) new Node[capacity];
    }

    private int hash(K key){
        return Math.floorMod(key.hashCode(), capacity);
    }

    public void put(K key, V value){
        if(value == null || key == null){
            throw new IllegalArgumentException("Key/value must not be null");
        }
        int index = hash(key);
        Node<K, V> temp = table[index];

        while(temp != null){
            if(temp.key.equals(key)){
                temp.value = value;
                return;
            }
            temp = temp.next;
        }
        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;

        if((double)size/capacity>=0.75){
            resize();
        }
    }

    public V get(K key){
        if(key == null){
            throw new IllegalArgumentException("Key must not be null!");
        }
        int index = hash(key);
        Node<K, V> temp = table[index];
        while(temp != null){
            if(temp.key.equals(key)){
                return temp.value;
            }
            temp = temp.next;
        }
        return null;
    }

    public boolean containsKey(K key){
        if(key == null){
            throw new IllegalArgumentException("Key must not be null!");
        }
        int index = hash(key);
        Node<K, V> temp = table[index];

        while(temp != null){
            if(temp.key.equals(key)){
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public V remove(K key){
        if(key == null){
            throw new IllegalArgumentException("Key must not be null!");
        }
        int index = hash(key);
        Node<K, V> now = table[index];
        Node<K, V> prev = null;

        while(now != null){
            if(now.key.equals(key)){
                if(prev == null){
                    table[index] = now.next;
                }else{
                    prev.next = now.next;
                }
                V value = now.value;
                now.next = null;
                size--;
                return value;
            }
            prev = now;
            now = now.next;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize(){
        int newCapacity = capacity * 2;
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];
        for(int i = 0; i < capacity; i++){
            Node<K, V> temp = table[i];

            while(temp != null){
                Node<K, V> next = temp.next;
                int index = Math.floorMod(temp.key.hashCode(), newCapacity);
                temp.next = newTable[index];
                newTable[index] = temp;
                temp = next;
            }
        }
        table = newTable;
        capacity = newCapacity;
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
        for(int i = 0; i < capacity; i++){
            table[i] = null;
        }
        size = 0;
    }

    public void display(){
        for(int i = 0; i < capacity; i++){
            System.out.print("Bucket " + i + ": ");
            Node<K, V> temp = table[i];
            while(temp != null){
                System.out.print("[" + temp.key + " = " + temp.value + "] -> ");
                temp = temp.next;
            }
            System.out.println("null");
        }
    }
}
