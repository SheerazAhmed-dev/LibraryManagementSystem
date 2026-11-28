package library.ui;
import library.models.Transaction;
import library.services.BorrowService;
import library.services.MemberService;
import library.services.BookService;
import library.datastructures.DynamicArray;
import library.utilities.InputValidator;
import library.utilities.DateUtility;
import library.utilities.IDGenerator;
import java.util.Scanner;
import java.time.LocalDate;

public class BorrowMenu{
    private Scanner scanner;
    private BorrowService borrowService;
    private MemberService memberService;
    private BookService bookService;

    public BorrowMenu(Scanner scanner,BorrowService borrowService,MemberService memberService,BookService bookService){
        this.scanner = scanner;
        this.borrowService = borrowService;
        this.memberService = memberService;
        this.bookService = bookService;
    }
    public void showMenu(){
        boolean running = true;
        while(running){
            System.out.println("\n========== BORROWING MANAGEMENT ==========");
            System.out.println("1. Borrow Book");
            System.out.println("2. Borrow Reserved Book");
            System.out.println("3. Return Book");
            System.out.println("4. View Overdue Transactions");
            System.out.println("5. View Borrowing Information");
            System.out.println("6. Update Daily Fine Rate");
            System.out.println("0. Back to Main Menu");
            System.out.println("==========================================");
            int choice = InputValidator.getInt(scanner,"Enter your choice: ");
            switch(choice){
                case 1:
                    borrowBook();
                    break;
                case 2:
                    borrowReservedBook();
                    break;
                case 3:
                    returnBook();
                    break;
                case 4:
                    viewOverdueTransactions();
                    break;
                case 5:
                    viewBorrowingInformation();
                    break;
                case 6:
                    updateFineRate();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void borrowBook(){
        try{
            System.out.println("\n========== BORROW BOOK ==========");
            String memberId = InputValidator.getString(scanner,"Enter member ID: ");
            String isbn = InputValidator.getString(scanner,"Enter book ISBN: ");
            if(memberService.searchById(memberId)==null){
                System.out.println("Member not found!");
                return;
            }
            if(bookService.searchByISBN(isbn)==null){
                System.out.println("Book not found!");
                return;
            }
            LocalDate borrowDate = DateUtility.getDate(scanner,"Enter borrowing date (dd-MM-yyyy): ");
            String transactionId = IDGenerator.generateTransactionId();
            Transaction transaction = borrowService.borrowBook(
                transactionId,memberId,isbn,borrowDate
            );
            System.out.println("\nBook borrowed successfully!");
            System.out.println("Generated Transaction ID: " + transactionId);
            transaction.displayDetails();
        }
        catch(IllegalArgumentException | IllegalStateException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void borrowReservedBook(){
        try{
            System.out.println("\n========== BORROW RESERVED BOOK ==========");
            String reservationId = InputValidator.getString(scanner,"Enter reservation ID: ");
            LocalDate borrowDate = DateUtility.getDate(scanner,"Enter borrowing date (dd-MM-yyyy): ");
            String transactionId = IDGenerator.generateTransactionId();
            Transaction transaction = borrowService.borrowReservedBook(
                transactionId,reservationId,borrowDate
            );
            System.out.println("\nReserved book borrowed successfully!");
            System.out.println("Generated Transaction ID: " + transactionId);
            transaction.displayDetails();
        }
        catch(IllegalArgumentException | IllegalStateException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void returnBook(){
        try{
            System.out.println("\n========== RETURN BOOK ==========");
            String transactionId = InputValidator.getString(scanner,"Enter transaction ID: ");
            LocalDate returnDate = DateUtility.getDate(scanner,"Enter return date (dd-MM-yyyy): ");
            Transaction transaction = borrowService.returnBook(transactionId,returnDate);
            System.out.println("\nBook returned successfully!");
            transaction.displayDetails();
            if(transaction.getFine() > 0){
                System.out.println("Fine to pay: PKR " + transaction.getFine());
            }
            else{
                System.out.println("No fine charged.");
            }
        }
        catch(IllegalArgumentException | IllegalStateException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewOverdueTransactions(){
        LocalDate currentDate = DateUtility.getCurrentDate();
        DynamicArray<Transaction> overdueTransactions = borrowService.getOverdueTransactions(currentDate);
        if(overdueTransactions.isEmpty()){
            System.out.println("\nNo overdue books!");
            return;
        }
        System.out.println("\n========== OVERDUE TRANSACTIONS ==========");
        System.out.println("Overdue Transactions: " + overdueTransactions.size());
        for(int i=0; i<overdueTransactions.size(); i++){
            Transaction transaction = overdueTransactions.get(i);
            transaction.displayDetails();
            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(
                transaction.getDueDate(),currentDate
            );
            double estimatedFine = overdueDays * borrowService.getDailyFineRate();
            System.out.println("Overdue Days: " + overdueDays);
            System.out.println("Estimated Fine: PKR " + estimatedFine);
            System.out.println("****************************************");
        }
    }

    private void viewBorrowingInformation(){
        System.out.println("\n========== BORROWING INFORMATION ==========");
        System.out.println("Borrowing Period: " + borrowService.getBorrowingPeriod() + " days");
        System.out.println("Daily Fine Rate: PKR " + borrowService.getDailyFineRate());
        System.out.println("Maximum Active Borrowings per Member: 1");
    }

    private void updateFineRate(){
        try{
            double rate = InputValidator.getNonNegativeDouble(scanner,"Enter new daily fine rate (PKR): ");
            borrowService.setDailyFineRate(rate);
            System.out.println("Daily fine rate updated successfully!");
        }
        catch(IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
