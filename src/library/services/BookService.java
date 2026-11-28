package library.services;
import library.models.Book;
import library.datastructures.DynamicArray;
import library.datastructures.HashTable;
import library.datastructures.BinarySearchTree;
import library.datastructures.SinglyLinkedList;

public class BookService{
    private DynamicArray<Book> books;
    private HashTable<String,Book> isbnIndex;
    private BinarySearchTree titleIndex;
    private HistoryService historyService;

    public BookService(){
        this(new HistoryService());
    }
    public BookService(HistoryService historyService){
        if(historyService==null){
            throw new IllegalArgumentException("History service cannot be null");
        }
        books = new DynamicArray<>();
        isbnIndex = new HashTable<>();
        titleIndex = new BinarySearchTree();
        this.historyService = historyService;
    }
    public void addBook(Book book){
        if(book==null){
            throw new IllegalArgumentException("Book cannot be null");
        }
        String isbn = book.getIsbn();
        if(isbnIndex.containsKey(isbn)){
            throw new IllegalArgumentException("A book with this ISBN already exists");
        }
        books.add(book);
        isbnIndex.put(isbn,book);
        titleIndex.insert(book.getTitle(),isbn);
        historyService.logActivity("BOOK_ADDED","Added book: " + book.getTitle(),null,isbn);
    }
    public Book searchByISBN(String isbn){
        if(isbn==null || isbn.isBlank()){
            return null;
        }
        Book book = isbnIndex.get(isbn.trim());
        historyService.logSearch(isbn.trim(),"ISBN",book == null ? 0 : 1);
        return book;
    }
    public DynamicArray<Book> searchByTitle(String title){
        DynamicArray<Book> result = new DynamicArray<>();
        if(title==null || title.isBlank()){
            return result;
        }
        SinglyLinkedList<String> isbns = titleIndex.search(title.trim());
        for(int i=0; i<isbns.size(); i++){
            Book book = isbnIndex.get(isbns.get(i));
            if(book != null){
                result.add(book);
            }
        }
        historyService.logSearch(title.trim(),"TITLE",result.size());
        return result;
    }
    public DynamicArray<Book> searchByAuthor(String author){
        DynamicArray<Book> result = new DynamicArray<>();
        if(author==null || author.isBlank()){
            return result;
        }
        for(int i=0; i<books.size(); i++){
            Book book = books.get(i);
            if(book.getAuthor().equalsIgnoreCase(author.trim())){
                result.add(book);
            }
        }
        historyService.logSearch(author.trim(),"AUTHOR",result.size());
        return result;
    }
    public DynamicArray<Book> searchByCategory(String category){
        DynamicArray<Book> result = new DynamicArray<>();
        if(category==null || category.isBlank()){
            return result;
        }
        for(int i=0; i<books.size(); i++){
            Book book = books.get(i);
            if(book.getCategory().equalsIgnoreCase(category.trim())){
                result.add(book);
            }
        }
        historyService.logSearch(category.trim(),"CATEGORY",result.size());
        return result;
    }
    public void updateBook(String isbn,String title,String author,String publisher,String category,int totalCopies,int publicationYear){
        Book book = searchByISBN(isbn);

        if(book==null){
            throw new IllegalArgumentException("Book not found");
        }
        if(title==null || title.isBlank() || author==null || author.isBlank() ||
           publisher==null || publisher.isBlank() || category==null || category.isBlank()){
            throw new IllegalArgumentException("Book details cannot be empty");
        }
        String oldTitle = book.getTitle();
        if(!oldTitle.equalsIgnoreCase(title.trim())){
            titleIndex.delete(oldTitle,book.getIsbn());
            book.setTitle(title.trim());
            titleIndex.insert(book.getTitle(),book.getIsbn());
        }
        book.setAuthor(author.trim());
        book.setPublisher(publisher.trim());
        book.setCategory(category.trim());
        book.setTotalCopies(totalCopies);
        book.setPublicationYear(publicationYear);
        historyService.logActivity("BOOK_UPDATED","Updated book: " + book.getTitle(),null,book.getIsbn());
    }
    public boolean deleteBook(String isbn){
        if(isbn==null || isbn.isBlank()){
            return false;
        }
        Book book = searchByISBN(isbn);
        if(book==null){
            return false;
        }
        if(book.getBorrowedCopies()>0){
            throw new IllegalStateException("Cannot delete a book with active borrowings");
        }
        int index = -1;
        for(int i=0; i<books.size(); i++){
            if(books.get(i).getIsbn().equalsIgnoreCase(isbn.trim())){
                index = i;
                break;
            }
        }
        if(index != -1){
            books.remove(index);
        }
        isbnIndex.remove(isbn.trim());
        titleIndex.delete(book.getTitle(),book.getIsbn());
        historyService.logActivity("BOOK_DELETED","Deleted book: " + book.getTitle(),null,book.getIsbn());
        return true;
    }
    public DynamicArray<Book> getAllBooks(){
        DynamicArray<Book> result = new DynamicArray<>();
        for(int i=0; i<books.size(); i++){
            result.add(books.get(i));
        }
        return result;
    }
    public DynamicArray<Book> sortByTitle(){
        DynamicArray<Book> result = getAllBooks();
        for(int i=0; i<result.size()-1; i++){
            for(int j=0; j<result.size()-i-1; j++){
                if(result.get(j).getTitle().compareToIgnoreCase(result.get(j+1).getTitle())>0){
                    Book temp = result.get(j);
                    result.set(j,result.get(j+1));
                    result.set(j + 1,temp);
                }
            }
        }
        return result;
    }
    public int getBookCount(){
        return books.size();
    }
    public boolean isEmpty(){
        return books.isEmpty();
    }
    public void displayAllBooks(){
        if(books.isEmpty()){
            System.out.println("No books available!");
            return;
        }
        for(int i=0; i<books.size(); i++){
            books.get(i).displayDetails();
            System.out.println("***********************");
        }
    }
}
