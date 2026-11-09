package library.datastructures;
public class BinarySearchTree{
    private class Node{
        String title;
        SinglyLinkedList<String> isbns;
        Node left;
        Node right;

        Node(String title, String isbn){
            this.left = null;
            this.right = null;
            this.isbns = new SinglyLinkedList<>();
            this.isbns.addLast(isbn);
            this.title = title;
        }
    }

    private Node root;
    private int size;
    private int bookCount;

    public BinarySearchTree(){
        root = null;
        size = 0;
        bookCount = 0;
    }

    public int size(){
        return size;
    }

    public int bookCount(){
        return bookCount;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public void insert(String title, String isbn){
        if(title == null || title.trim().isEmpty()){
            throw new IllegalArgumentException("Book title is empty! enter valid title");
        }
        if(isbn == null || isbn.trim().isEmpty()){
            throw new IllegalArgumentException("Book ISBN is empty! enter valid isbn");
        }

        title = title.trim();
        isbn = isbn.trim();

        if(root == null){
            root = new Node(title, isbn);
            bookCount++;
            size++;
            return;
        }

        Node temp = root;
        while(true){
            int comp = title.compareToIgnoreCase(temp.title);

            if(comp == 0){
                if(temp.isbns.contains(isbn)){
                    return;
                }
                temp.isbns.addLast(isbn);
                bookCount++;
                return;
            }
            if(comp < 0){
                if(temp.left == null){
                    temp.left = new Node(title, isbn);
                    size++;
                    bookCount++;
                    return;
                }
                temp = temp.left;
            }else{
                if(temp.right == null){
                    temp.right = new Node(title, isbn);
                    size++;
                    bookCount++;
                    return;
                }
                temp = temp.right;
            }
        }
    }

    public SinglyLinkedList<String> search(String title){
        if(title == null || title.trim().isEmpty()){
            throw new IllegalArgumentException("Enter valid book title, book title cannot be empty!");
        }

        Node temp = find(title.trim());
        SinglyLinkedList<String> found = new SinglyLinkedList<>();

        if(temp != null){
            for(int i=0; i<temp.isbns.size(); i++){
                found.addLast(temp.isbns.get(i));
            }
        }
        return found;
    }

    public boolean titleExists(String title){
        if(title == null || title.trim().isEmpty()){
            return false;
        }
        return find(title.trim()) != null;
    }

    private Node find(String title){
        Node temp = root;
        while(temp != null){
            int comp = title.compareToIgnoreCase(temp.title);

            if(comp == 0){
                return temp;
            }
            if(comp < 0){
                temp = temp.left;
            }else{
                temp = temp.right;
            }
        }
        return null;
    }

    public boolean delete(String title, String isbn){
        if(title == null || title.trim().isEmpty() || isbn == null || isbn.trim().isEmpty()){
            throw new IllegalArgumentException("Title/Isbn invalid!");
        }

        Node temp = find(title.trim());

        if(temp == null || !temp.isbns.contains(isbn.trim())){
            return false;
        }

        int index = temp.isbns.indexOf(isbn.trim());
        temp.isbns.remove(index);
        bookCount--;

        if(temp.isbns.isEmpty()){
            root = deleteRecursive(root, title.trim());
            size--;
        }
        return true;
    }

    public boolean deleteEntireTitle(String title){
        if(title == null || title.trim().isEmpty()){
            throw new IllegalArgumentException("Book name is invalid!");
        }

        Node temp = find(title.trim());

        if(temp == null){
            return false;
        }

        bookCount -= temp.isbns.size();
        root = deleteRecursive(root, title.trim());
        size--;

        return true;
    }

    private Node deleteRecursive(Node node, String title){
        if(node == null){
            return null;
        }

        int comp = title.compareToIgnoreCase(node.title);

        if(comp < 0){
            node.left = deleteRecursive(node.left, title);
        }else if(comp > 0){
            node.right = deleteRecursive(node.right, title);
        }else{
            if(node.left == null){
                return node.right;
            }
            if(node.right == null){
                return node.left;
            }

            Node successor = findMin(node.right);
            node.title = successor.title;
            node.isbns = successor.isbns;
            node.right = deleteRecursive(node.right, successor.title);
        }
        return node;
    }

    private Node findMin(Node node){
        while(node.left != null){
            node = node.left;
        }
        return node;
    }

    public void inOrderTraversal(){
        if(isEmpty()){
            System.out.println("The tree is empty!");
            return;
        }
        inOrder(root);
    }

    private void inOrder(Node node){
        if(node == null){
            return;
        }
        inOrder(node.left);
        showNode(node);
        inOrder(node.right);
    }

    public void preOrderTraversal(){
        if(isEmpty()){
            System.out.println("The tree is empty!");
            return;
        }
        preOrder(root);
    }

    private void preOrder(Node node){
        if(node == null){
            return;
        }
        showNode(node);
        preOrder(node.left);
        preOrder(node.right);
    }

    public void postOrderTraversal(){
        if(isEmpty()){
            System.out.println("The tree is empty!");
            return;
        }
        postOrder(root);
    }

    private void postOrder(Node node){
        if(node == null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        showNode(node);
    }

    private void showNode(Node node){
        System.out.println("Title: " + node.title);
        System.out.print("ISBNs: ");
        node.isbns.display();
    }

    public void clear(){
        root = null;
        size = 0;
        bookCount = 0;
    }
}
