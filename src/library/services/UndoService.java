package library.services;
import library.models.Book;
import library.datastructures.CustomStack;
public class UndoService{
    private BookService bookService;
    private CustomStack<UndoAction> undoStack;
    private enum ActionType{
        ADD, UPDATE, DELETE
    }
    private static class UndoAction{
        ActionType type;
        Book book;
        String isbn;
        String title;
        String author;
        String publisher;
        String category;
        int totalCopies;
        int publicationYear;
        public UndoAction(ActionType type,Book book){
            this.type = type;
            this.book = book;
            this.isbn = book.getIsbn();
        }
        public UndoAction(Book book,String title,String author,String publisher,String category,int totalCopies,int publicationYear){
            this.type = ActionType.UPDATE;
            this.book = book;
            this.isbn = book.getIsbn();
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.category = category;
            this.totalCopies = totalCopies;
            this.publicationYear = publicationYear;
        }
    }

    public UndoService(BookService bookService){
        if(bookService == null){
            throw new IllegalArgumentException("Book service cannot be null");
        }
        this.bookService = bookService;
        undoStack = new CustomStack<>();
    }
    public void addBook(Book book){
        bookService.addBook(book);
        undoStack.push(new UndoAction(ActionType.ADD,book));
    }

    public void updateBook(String isbn,String title,String author,String publisher,String category,int totalCopies,int publicationYear){
        Book book = bookService.searchByISBN(isbn);
        if(book==null){
            throw new IllegalArgumentException("Book not found");
        }
        UndoAction action = new UndoAction(
            book,
            book.getTitle(),
            book.getAuthor(),
            book.getPublisher(),
            book.getCategory(),
            book.getTotalCopies(),
            book.getPublicationYear()
        );
        bookService.updateBook(isbn,title,author,publisher,category,totalCopies,publicationYear);
        undoStack.push(action);
    }

    public boolean deleteBook(String isbn){
        Book book = bookService.searchByISBN(isbn);
        if(book==null){
            return false;
        }
        if(bookService.deleteBook(isbn)){
            undoStack.push(new UndoAction(ActionType.DELETE,book));
            return true;
        }
        return false;
    }

    public boolean undo(){
        if(undoStack.isEmpty()){
            return false;
        }
        UndoAction action = undoStack.peek();
        switch(action.type){
            case ADD:
                if(!bookService.deleteBook(action.isbn)){
                    return false;
                }
                break;
            case UPDATE:
                bookService.updateBook(
                    action.isbn,
                    action.title,
                    action.author,
                    action.publisher,
                    action.category,
                    action.totalCopies,
                    action.publicationYear
                );
                break;
            case DELETE:
                bookService.addBook(action.book);
                break;
        }
        undoStack.pop();
        return true;
    }
    public boolean canUndo(){
        return !undoStack.isEmpty();
    }
    public int getUndoCount(){
        return undoStack.size();
    }
    public void clearHistory(){
        undoStack.clear();
    }
}
