package library.models;
import library.interfaces.Displayable;
import library.interfaces.Searchable;

public class Book implements Displayable,Searchable{
    private String title;
    private String isbn;
    private String author;
    private String publisher;
    private String category;
    private int totalCopies;
    private int availableCopies;
    private int publicationYear;
    
    public Book(String isbn, String title, String author, String publisher, String category, int publicationYear, int totalCopies){
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
        this.availableCopies = totalCopies;
        this.publicationYear = publicationYear;
        this.totalCopies = totalCopies;   
    }
    @Override
    public boolean matches(String query){
        if(query == null || query.trim().isEmpty()){
            return false;
        }
        query = query.trim().toLowerCase();

        return title.toLowerCase().contains(query) ||
           isbn.toLowerCase().contains(query) ||
           author.toLowerCase().contains(query) ||
           publisher.toLowerCase().contains(query) ||
           category.toLowerCase().contains(query);
    }

    public String getIsbn(){
        return isbn;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public String getPublisher(){
        return publisher;
    }
    public String getCategory(){
        return category;
    }
    public int getAvailableCopies(){
        return availableCopies;
    }
    public int getPublicationYear(){
        return publicationYear;
    }
    public int getTotalCopies(){
        return totalCopies;
    }
    public int getBorrowedCopies(){
        return totalCopies - availableCopies;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setAvailableCopies(int availableCopies){
        if(availableCopies<0 || availableCopies>totalCopies){
            throw new IllegalArgumentException("Invalid available copies");
        }
        this.availableCopies = availableCopies;
    }
    public void setPublicationYear(int publicationYear){
        this.publicationYear = publicationYear;
    }
    public void setTotalCopies(int totalCopies){
        int borrowedCopies = getBorrowedCopies();

        if(totalCopies<borrowedCopies || totalCopies<=0){
            throw new IllegalArgumentException("Invalid total copies");
        }
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies - borrowedCopies;
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    public void setCategory(String category){
        this.category = category;
    }

    public void displayDetails(){
        System.out.println("ISBN: " + isbn);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Publisher: " + publisher);
        System.out.println("Category: " + category);
        System.out.println("Publication Year: " + publicationYear);
        System.out.println("Total Copies: " + totalCopies);
        System.out.println("Available Copies: " + availableCopies);
        System.out.println("Borrowed Copies: " + getBorrowedCopies());
    }
}
