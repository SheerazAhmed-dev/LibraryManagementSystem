package library.ui;
import library.models.Book;
import library.models.Member;
import library.models.Transaction;
import library.models.Reservation;
import library.models.SearchRecord;
import library.services.ReportService;
import library.datastructures.DynamicArray;
import library.utilities.DateUtility;
import java.util.Scanner;
import java.time.LocalDate;
public class ReportMenu{
    private Scanner scanner;
    private ReportService reportService;

    public ReportMenu(Scanner scanner,ReportService reportService){
        this.scanner = scanner;
        this.reportService = reportService;
    }

    public void showMenu(){
        boolean running = true;
        while(running){
            System.out.println("\n========== REPORTS AND ANALYTICS ==========");
            System.out.println("1. Book Inventory Report");
            System.out.println("2. Member Statistics");
            System.out.println("3. Borrowing Report");
            System.out.println("4. Overdue Report");
            System.out.println("5. Fine Report");
            System.out.println("6. Reservation Statistics");
            System.out.println("7. Search Analytics");
            System.out.println("0. Back to Main Menu");
            System.out.println("===========================================");
            int choice = getChoice();
            switch(choice){
                case 1:
                    bookInventoryReport();
                    break;
                case 2:
                    memberStatistics();
                    break;
                case 3:
                    borrowingReport();
                    break;
                case 4:
                    overdueReport();
                    break;
                case 5:
                    fineReport();
                    break;
                case 6:
                    reservationStatistics();
                    break;
                case 7:
                    searchAnalytics();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private int getChoice(){
        System.out.print("Enter your choice: ");
        try{
            return Integer.parseInt(scanner.nextLine().trim());
        }
        catch(NumberFormatException e){
            return -1;
        }
    }

    private void bookInventoryReport(){
        System.out.println("\n========== BOOK INVENTORY REPORT ==========");
        DynamicArray<Book> books = reportService.getBookInventory();
        System.out.println("Total Book Titles: " + reportService.getTotalBookTitles());
        System.out.println("Total Book Copies: " + reportService.getTotalBookCopies());
        System.out.println("Available Copies: " + reportService.getAvailableBookCopies());
        System.out.println("Borrowed Copies: " + reportService.getBorrowedBookCopies());
        if(books.isEmpty()){
            System.out.println("\nNo books available!");
            return;
        }
        System.out.println("\nBook Inventory Details:");
        for(int i = 0; i < books.size(); i++){
            books.get(i).displayDetails();
            System.out.println("***********************");
        }
    }

    private void memberStatistics(){
        System.out.println("\n========== MEMBER STATISTICS ==========");
        System.out.println("Total Members: " + reportService.getTotalMembers());
        System.out.println("Active Members: " + reportService.getActiveMemberCount());
        System.out.println("Inactive Members: " + reportService.getInactiveMemberCount());
        System.out.println("\nActive Members:");
        DynamicArray<Member> active = reportService.getActiveMembers();
        if(active.isEmpty()){
            System.out.println("No active members!");
        }
        else{
            for(int i = 0; i < active.size(); i++){
                active.get(i).displayDetails();
                System.out.println("***********************");
            }
        }
        System.out.println("\nInactive Members:");
        DynamicArray<Member> inactive = reportService.getInactiveMembers();
        if(inactive.isEmpty()){
            System.out.println("No inactive members!");
        }
        else{
            for(int i = 0; i < inactive.size(); i++){
                inactive.get(i).displayDetails();
                System.out.println("***********************");
            }
        }
    }

    private void borrowingReport(){
        System.out.println("\n========== BORROWING REPORT ==========");
        DynamicArray<Transaction> transactions = reportService.getAllTransactions();
        System.out.println("Total Transactions: " + reportService.getTotalTransactionCount());
        System.out.println("Active Borrowings: " + reportService.getActiveBorrowingCount());
        if(transactions.isEmpty()){
            System.out.println("\nNo borrowing transactions available!");
            return;
        }
        for(int i = 0; i < transactions.size(); i++){
            transactions.get(i).displayDetails();
            System.out.println("**********************");
        }
    }

    private void overdueReport(){
        System.out.println("\n========== OVERDUE REPORT ==========");
        LocalDate currentDate = DateUtility.getCurrentDate();
        DynamicArray<Transaction> overdue = reportService.getOverdueTransactions(currentDate);
        System.out.println("Overdue Transactions: " + overdue.size());
        if(overdue.isEmpty()){
            System.out.println("No overdue transactions!");
            return;
        }
        for(int i = 0; i < overdue.size(); i++){
            Transaction transaction = overdue.get(i);
            transaction.displayDetails();
            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(
                transaction.getDueDate(),currentDate
            );
            double estimatedFine = overdueDays * reportService.getDailyFineRate();
            System.out.println("Overdue Days: " + overdueDays);
            System.out.println("Estimated Fine: PKR " + estimatedFine);
            System.out.println("***********************");
        }
    }

    private void fineReport(){
        System.out.println("\n========== FINE REPORT ==========");
        LocalDate currentDate = DateUtility.getCurrentDate();
        System.out.println("Collected Fines: PKR " + reportService.getCollectedFines());
        System.out.println("Outstanding Fines: PKR " + reportService.getOutstandingFines(currentDate));
    }

    private void reservationStatistics(){
        System.out.println("\n========== RESERVATION STATISTICS ==========");
        DynamicArray<Reservation> all = reportService.getAllReservations();
        DynamicArray<Reservation> pending = reportService.getPendingReservations();
        DynamicArray<Reservation> fulfilled = reportService.getFulfilledReservations();
        DynamicArray<Reservation> cancelled = reportService.getCancelledReservations();
        System.out.println("Total Reservations: " + all.size());
        System.out.println("Pending Reservations: " + reportService.getPendingReservationCount());
        System.out.println("Fulfilled Reservations: " + reportService.getFulfilledReservationCount());
        System.out.println("Cancelled Reservations: " + reportService.getCancelledReservationCount());
        System.out.println("\nAll Reservation Details:");
        if(all.isEmpty()){
            System.out.println("No reservations available!");
            return;
        }
        for(int i = 0; i < all.size(); i++){
            all.get(i).displayDetails();
            System.out.println("***********************");
        }
    }

    private void searchAnalytics(){
        System.out.println("\n========== SEARCH ANALYTICS ==========");
        DynamicArray<SearchRecord> searches = reportService.getSearchHistory();
        System.out.println("Total Searches: " + reportService.getTotalSearchCount());
        System.out.println("Successful Searches: " + reportService.getSuccessfulSearchCount());
        System.out.println("Unsuccessful Searches: " + reportService.getUnsuccessfulSearchCount());
        System.out.printf("Search Success Rate: %.2f%%%n",reportService.getSearchSuccessRate());
        if(searches.isEmpty()){
            System.out.println("\nNo search history available!");
            return;
        }
        System.out.println("\nSearch History:");
        for(int i = 0; i < searches.size(); i++){
            searches.get(i).displayDetails();
            System.out.println("***********************");
        }
    }
}
