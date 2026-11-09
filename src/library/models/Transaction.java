package library.models;
import java.time.LocalDate;
public class Transaction{
    private String transactionId;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private LocalDate dueDate;
    private Member member;
    private double fine;

    public Transaction(String TId, Member m, Book b, LocalDate bd, LocalDate dD){
        if(bd == null || dD == null || dD.isBefore(bd)){
            throw new IllegalArgumentException("Invalid borrowing or due date");
        }
        if(TId == null || TId.isBlank() || m == null || b == null){
            throw new IllegalArgumentException("Invalid transaction details");
        }
        this.transactionId = TId;
        this.book = b;
        this.borrowDate = bd;
        this.dueDate = dD;
        this.returnDate = null;
        this.member = m;
        this.fine = 0.0;
    }

    public String getTransactionId(){
        return transactionId;
    }
    public Book getBook(){
        return book;
    }
    public Member getMember(){
        return member;
    }
    public LocalDate getBorrowDate(){
        return borrowDate;
    }
    public LocalDate getDueDate(){
        return dueDate;
    }
    public LocalDate getReturnDate(){
        return returnDate;
    }
    public double getFine(){
        return fine;
    }
    public boolean isReturned(){
        return returnDate != null;
    }
    public void finishReturn(LocalDate returnDate, double fine){
    if(this.returnDate != null){
        throw new IllegalStateException("Book is already returned!");
    }
    if(returnDate == null || returnDate.isBefore(borrowDate)){
        throw new IllegalArgumentException("Invalid return date");
    }
    if(fine<0){
        throw new IllegalArgumentException("Fine should not be negative!");
    }
    this.fine = fine;
    this.returnDate = returnDate;
}
    
    public void displayDetails(){
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Member: " + member.getName());
        System.out.println("Book: " + book.getTitle());
        System.out.println("Borrow Date: " + borrowDate);
        System.out.println("Due Date: " + dueDate);
        System.out.println("Return Date: " + (returnDate == null ? "Not returned" : returnDate));
        System.out.println("Fine: " + fine);
        System.out.println("Status: " + (isReturned()?"Returned" : "Borrowed"));
    }
}
