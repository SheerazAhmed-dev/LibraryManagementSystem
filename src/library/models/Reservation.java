package library.models;
import java.time.LocalDate;

public class Reservation{
    private String reservationId;
    private Member member;
    private Book book;
    private LocalDate reservationDate;
    private boolean fulfilled;
    private boolean cancelled;

    public Reservation(String reservationId, Member member, Book book){
        if(reservationId == null || reservationId.isBlank() || member == null || book == null){
            throw new IllegalArgumentException("Invalid reservation details");
        }
        this.reservationId = reservationId;
        this.member = member;
        this.book = book;
        this.reservationDate = LocalDate.now();
        this.fulfilled = false;
        this.cancelled = false;
    }

    public String getReservationId(){
        return reservationId;
    }
    public Member getMember(){
        return member;
    }
    public Book getBook(){
        return book;
    }
    public LocalDate getReservationDate(){
        return reservationDate;
    }
    public boolean isFulfilled(){
        return fulfilled;
    }
    public boolean isCancelled(){
        return cancelled;
    }
    
    public void fulfill(){
        if(cancelled || fulfilled){
            throw new IllegalStateException("Reservation is already completed");
        }
        fulfilled = true;
    }
    public void cancel(){
        if(fulfilled || cancelled){
            throw new IllegalStateException("Reservation is already completed");
        }
        cancelled = true;
    }
    public void displayDetails(){
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Member: " + member.getName());
        System.out.println("Book: " + book.getTitle());
        System.out.println("Reservation Date: " + reservationDate);
        String status = fulfilled ? "Fulfilled" : cancelled ? "Cancelled" : "Pending";
        System.out.println("Status: " + status);
    }
}