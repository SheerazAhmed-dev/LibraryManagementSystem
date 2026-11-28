package library.ui;
import library.models.Book;
import library.services.BookService;
import library.services.UndoService;
import library.utilities.InputValidator;
import library.datastructures.DynamicArray;
import java.util.Scanner;
public class BookMenu{
    private Scanner scanner;
    private BookService bookService;
    private UndoService undoService;

    public BookMenu(Scanner scanner,BookService bookService,UndoService undoService){
        this.scanner = scanner;
        this.bookService = bookService;
        this.undoService = undoService;
    }

    public void showMenu(){
        boolean running = true;
        while(running){
            System.out.println("\n========== BOOK MANAGEMENT ==========");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book by ISBN");
            System.out.println("3. Search Book by Title");
            System.out.println("4. Search Book by Author");
            System.out.println("5. Search Book by Category");
            System.out.println("6. Update Book");
            System.out.println("7. Delete Book");
            System.out.println("8. Display All Books");
            System.out.println("9. Sort Books by Title");
            System.out.println("10. View Book Count");
            System.out.println("0. Back to Main Menu");
            System.out.println("=====================================");

            int choice = InputValidator.getInt(scanner,"Enter your choice: ");
            switch(choice){
                case 1:
                    addBook();
                    break;
                case 2:
                    searchByISBN();
                    break;
                case 3:
                    searchByTitle();
                    break;
                case 4:
                    searchByAuthor();
                    break;
                case 5:
                    searchByCategory();
                    break;
                case 6:
                    updateBook();
                    break;
                case 7:
                    deleteBook();
                    break;
                case 8:
                    bookService.displayAllBooks();
                    break;
                case 9:
                    sortBooks();
                    break;
                case 10:
                    System.out.println("Total Books: " + bookService.getBookCount());
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addBook(){
        try{
            System.out.println("\n========== ADD BOOK ==========");

            String isbn = InputValidator.getString(scanner,"Enter ISBN: ");
            String title = InputValidator.getString(scanner,"Enter title: ");
            String author = InputValidator.getString(scanner,"Enter author: ");
            String publisher = InputValidator.getString(scanner,"Enter publisher: ");
            String category = InputValidator.getString(scanner,"Enter category: ");
            int year = InputValidator.getPositiveInt(scanner,"Enter publication year: ");
            int copies = InputValidator.getPositiveInt(scanner,"Enter total copies: ");
            Book book = new Book(isbn,title,author,publisher,category,year,copies);
            undoService.addBook(book);

            System.out.println("Book added successfully!");
        }
        catch(IllegalArgumentException | IllegalStateException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchByISBN(){
        String isbn = InputValidator.getString(scanner,"Enter ISBN: ");
        Book book = bookService.searchByISBN(isbn);
        if(book==null){
            System.out.println("Book not found!");
        }
        else{
            book.displayDetails();
        }
    }
    private void searchByTitle(){
        String title = InputValidator.getString(scanner,"Enter book title: ");
        DynamicArray<Book> result = bookService.searchByTitle(title);
        displaySearchResults(result);
    }
    private void searchByAuthor(){
        String author = InputValidator.getString(scanner,"Enter author name: ");
        DynamicArray<Book> result = bookService.searchByAuthor(author);
        displaySearchResults(result);
    }

    private void searchByCategory(){
        String category = InputValidator.getString(scanner,"Enter category: ");
        DynamicArray<Book> result = bookService.searchByCategory(category);
        displaySearchResults(result);
    }
    private void updateBook(){
        try{
            System.out.println("\n========== UPDATE BOOK ==========");
            String isbn = InputValidator.getString(scanner,"Enter ISBN of book to update: ");
            Book book = bookService.searchByISBN(isbn);
            if(book==null){
                System.out.println("Book not found!");
                return;
            }
            System.out.println("\nCurrent Book Details:");
            book.displayDetails();
            System.out.println("\nEnter New Details:");

            String title = InputValidator.getString(scanner,"Enter new title: ");
            String author = InputValidator.getString(scanner,"Enter new author: ");
            String publisher = InputValidator.getString(scanner,"Enter new publisher: ");
            String category = InputValidator.getString(scanner,"Enter new category: ");
            int totalCopies = InputValidator.getPositiveInt(scanner,"Enter new total copies: ");
            int year = InputValidator.getPositiveInt(scanner,"Enter new publication year: ");
            undoService.updateBook(isbn,title,author,publisher,category,totalCopies,year);

            System.out.println("Book updated successfully!");
        }
        catch(IllegalArgumentException | IllegalStateException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteBook(){
        try{
            String isbn = InputValidator.getString(scanner,"Enter ISBN of book to delete: ");
            Book book = bookService.searchByISBN(isbn);
            if(book == null){
                System.out.println("Book not found!");
                return;
            }
            book.displayDetails();
            boolean confirm = InputValidator.getBoolean(scanner,"Are you sure you want to delete this book? (yes/no): ");
            if(!confirm){
                System.out.println("Deletion cancelled.");
                return;
            }
            undoService.deleteBook(isbn);
            System.out.println("Book deleted successfully!");
        }
        catch(IllegalArgumentException | IllegalStateException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void sortBooks(){
        DynamicArray<Book> sortedBooks = bookService.sortByTitle();
        if(sortedBooks.isEmpty()){
            System.out.println("No books available!");
            return;
        }
        System.out.println("\n========== BOOKS SORTED BY TITLE ==========");
        for(int i=0; i<sortedBooks.size(); i++){
            sortedBooks.get(i).displayDetails();
            System.out.println("***********************");
        }
    }

    private void displaySearchResults(DynamicArray<Book> result){
        if(result.isEmpty()){
            System.out.println("No matching books found!");
            return;
        }
        System.out.println("\nSearch Results: " + result.size());
        for(int i=0; i<result.size(); i++){
            result.get(i).displayDetails();
            System.out.println("***********************");
        }
    }
}
