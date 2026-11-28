package library.ui;
import library.models.Book;
import library.models.Member;
import library.models.Reservation;
import library.services.ReservationService;
import library.services.MemberService;
import library.services.BookService;
import library.services.BorrowService;
import library.datastructures.DynamicArray;
import library.utilities.InputValidator;
import library.utilities.IDGenerator;
import java.util.Scanner;
public class ReservationMenu{
    private Scanner scanner;
    private ReservationService reservationService;
    private MemberService memberService;
    private BookService bookService;
    private BorrowService borrowService;

    public ReservationMenu(Scanner scanner,ReservationService reservationService,MemberService memberService,BookService bookService,BorrowService borrowService){
        this.scanner = scanner;
        this.reservationService = reservationService;
        this.memberService = memberService;
        this.bookService = bookService;
        this.borrowService = borrowService;
    }

    public void showMenu(){
        boolean running = true;
        while(running){
            System.out.println("\n========== RESERVATION MANAGEMENT ==========");
            System.out.println("1. Reserve Book");
            System.out.println("2. Cancel Reservation");
            System.out.println("3. Search Reservation by ID");
            System.out.println("4. View Reservations for a Book");
            System.out.println("5. View All Reservations");
            System.out.println("6. View Pending Reservations");
            System.out.println("7. View Waiting Count for a Book");
            System.out.println("8. Borrow Reserved Book");
            System.out.println("0. Back to Main Menu");
            System.out.println("============================================");
            int choice = InputValidator.getInt(scanner,"Enter your choice: ");
            switch(choice){
                case 1:
                    reserveBook();
                    break;
                case 2:
                    cancelReservation();
                    break;
                case 3:
                    searchReservation();
                    break;
                case 4:
                    viewReservationsByBook();
                    break;
                case 5:
                    reservationService.displayAllReservations();
                    break;
                case 6:
                    viewPendingReservations();
                    break;
                case 7:
                    viewWaitingCount();
                    break;
                case 8:
                    borrowReservedBook();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void reserveBook(){
        try{
            System.out.println("\n========== RESERVE BOOK ==========");
            String memberId = InputValidator.getString(scanner,"Enter member ID: ");
            String isbn = InputValidator.getString(scanner,"Enter book ISBN: ");
            Member member = memberService.searchById(memberId);
            Book book = bookService.searchByISBN(isbn);
            if(member == null){
                System.out.println("Member not found!");
                return;
            }
            if(book == null){
                System.out.println("Book not found!");
                return;
            }
            String reservationId = IDGenerator.generateReservationId();
            Reservation reservation = reservationService.reserveBook(
                reservationId,member,book
            );
            System.out.println("\nReservation created successfully!");
            System.out.println("Generated Reservation ID: " + reservationId);
            reservation.displayDetails();
            System.out.println("Position in waiting queue: " + reservationService.getWaitingCount(isbn));
        }
        catch(IllegalArgumentException | IllegalStateException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cancelReservation(){
        String reservationId = InputValidator.getString(scanner,"Enter reservation ID: ");
        Reservation reservation = reservationService.getReservationById(reservationId);
        if(reservation == null){
            System.out.println("Reservation not found!");
            return;
        }
        reservation.displayDetails();
        boolean confirm = InputValidator.getBoolean(scanner,"Are you sure you want to cancel this reservation? (yes/no): ");
        if(!confirm){
            System.out.println("Cancellation aborted.");
            return;
        }
        try{
            if(reservationService.cancelReservation(reservationId)){
                System.out.println("Reservation cancelled successfully!");
            }
            else{
                System.out.println("Reservation cannot be cancelled!");
            }
        }
        catch(IllegalStateException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchReservation(){
        String reservationId = InputValidator.getString(scanner,"Enter reservation ID: ");
        Reservation reservation = reservationService.getReservationById(reservationId);
        if(reservation == null){
            System.out.println("Reservation not found!");
            return;
        }
        reservation.displayDetails();
    }

    private void viewReservationsByBook(){
        String isbn = InputValidator.getString(scanner,"Enter book ISBN: ");
        if(bookService.searchByISBN(isbn) == null){
            System.out.println("Book not found!");
            return;
        }
        reservationService.displayReservations(isbn);
    }

    private void viewPendingReservations(){
        DynamicArray<Reservation> reservations = reservationService.getAllReservations();
        int count = 0;
        System.out.println("\n========== PENDING RESERVATIONS ==========");
        for(int i = 0; i < reservations.size(); i++){
            Reservation reservation = reservations.get(i);
            if(!reservation.isCancelled() && !reservation.isFulfilled()){
                reservation.displayDetails();
                System.out.println("***********************");
                count++;
            }
        }
        if(count == 0){
            System.out.println("No pending reservations!");
        }
        else{
            System.out.println("Total Pending Reservations: " + count);
        }
    }

    private void viewWaitingCount(){
        String isbn = InputValidator.getString(scanner,"Enter book ISBN: ");
        if(bookService.searchByISBN(isbn) == null){
            System.out.println("Book not found!");
            return;
        }
        int count = reservationService.getWaitingCount(isbn);
        System.out.println("Pending reservations for this book: " + count);
    }

    private void borrowReservedBook(){
        try{
            System.out.println("\n========== BORROW RESERVED BOOK ==========");
            String reservationId = InputValidator.getString(scanner,"Enter reservation ID: ");
            Reservation reservation = reservationService.getReservationById(reservationId);
            if(reservation == null){
                System.out.println("Reservation not found!");
                return;
            }
            if(reservation.isCancelled() || reservation.isFulfilled()){
                System.out.println("This reservation is no longer active!");
                return;
            }
            System.out.println("\nReservation Details:");
            reservation.displayDetails();
            boolean confirm = InputValidator.getBoolean(scanner,"Proceed with borrowing this reserved book? (yes/no): ");
            if(!confirm){
                System.out.println("Borrowing cancelled.");
                return;
            }
            java.time.LocalDate borrowDate = library.utilities.DateUtility.getDate(
                scanner,"Enter borrowing date (dd-MM-yyyy): "
            );
            String transactionId = IDGenerator.generateTransactionId();
            borrowService.borrowReservedBook(
                transactionId,reservationId,borrowDate
            );
            System.out.println("Reserved book borrowed successfully!");
            System.out.println("Generated Transaction ID: " + transactionId);
        }
        catch(IllegalArgumentException | IllegalStateException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
