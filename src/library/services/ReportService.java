
package library.services;

import library.models.Book;
import library.models.Member;
import library.models.Transaction;
import library.models.Reservation;
import library.models.SearchRecord;
import library.datastructures.DynamicArray;
import java.time.LocalDate;

public class ReportService{
    private BookService bookService;
    private MemberService memberService;
    private TransactionService transactionService;
    private ReservationService reservationService;
    private BorrowService borrowService;
    private HistoryService historyService;

    public ReportService(BookService bookService,MemberService memberService,
                         TransactionService transactionService,ReservationService reservationService,
                         BorrowService borrowService,HistoryService historyService){

        if(bookService == null || memberService == null || transactionService == null ||
           reservationService == null || borrowService == null || historyService == null){
            throw new IllegalArgumentException("Report services cannot be null");
        }

        this.bookService = bookService;
        this.memberService = memberService;
        this.transactionService = transactionService;
        this.reservationService = reservationService;
        this.borrowService = borrowService;
        this.historyService = historyService;
    }

    // Book Reports

    public DynamicArray<Book> getBookInventory(){
        return bookService.getAllBooks();
    }

    public int getTotalBookTitles(){
        return bookService.getBookCount();
    }

    public int getTotalBookCopies(){
        int count = 0;
        DynamicArray<Book> books = bookService.getAllBooks();

        for(int i = 0; i < books.size(); i++){
            count += books.get(i).getTotalCopies();
        }

        return count;
    }

    public int getAvailableBookCopies(){
        int count = 0;
        DynamicArray<Book> books = bookService.getAllBooks();

        for(int i = 0; i < books.size(); i++){
            count += books.get(i).getAvailableCopies();
        }

        return count;
    }

    public int getBorrowedBookCopies(){
        return getTotalBookCopies() - getAvailableBookCopies();
    }

    // Member Reports

    public DynamicArray<Member> getAllMembers(){
        return memberService.getAllMembers();
    }

    public DynamicArray<Member> getActiveMembers(){
        DynamicArray<Member> result = new DynamicArray<>();
        DynamicArray<Member> members = memberService.getAllMembers();

        for(int i = 0; i < members.size(); i++){
            if(members.get(i).isActive()){
                result.add(members.get(i));
            }
        }

        return result;
    }

    public DynamicArray<Member> getInactiveMembers(){
        DynamicArray<Member> result = new DynamicArray<>();
        DynamicArray<Member> members = memberService.getAllMembers();

        for(int i = 0; i < members.size(); i++){
            if(!members.get(i).isActive()){
                result.add(members.get(i));
            }
        }

        return result;
    }

    public int getTotalMembers(){
        return memberService.getMemberCount();
    }

    public int getActiveMemberCount(){
        return getActiveMembers().size();
    }

    public int getInactiveMemberCount(){
        return getInactiveMembers().size();
    }

    // Borrowing Reports

    public DynamicArray<Transaction> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    public DynamicArray<Transaction> getActiveTransactions(){
        return transactionService.getActiveTransactions();
    }

    public DynamicArray<Transaction> getOverdueTransactions(LocalDate currentDate){
        if(currentDate == null){
            throw new IllegalArgumentException("Date cannot be null");
        }

        return borrowService.getOverdueTransactions(currentDate);
    }

    public int getTotalTransactionCount(){
        return transactionService.getTransactionCount();
    }

    public int getActiveBorrowingCount(){
        return transactionService.getActiveTransactionCount();
    }

    public int getOverdueCount(LocalDate currentDate){
        return getOverdueTransactions(currentDate).size();
    }

    // Fine Reports

    public double getCollectedFines(){
        double total = 0.0;
        DynamicArray<Transaction> transactions = transactionService.getAllTransactions();

        for(int i = 0; i < transactions.size(); i++){
            Transaction transaction = transactions.get(i);

            if(transaction.isReturned()){
                total += transaction.getFine();
            }
        }

        return total;
    }

    public double getOutstandingFines(LocalDate currentDate){
        if(currentDate == null){
            throw new IllegalArgumentException("Date cannot be null");
        }

        double total = 0.0;
        DynamicArray<Transaction> overdue = getOverdueTransactions(currentDate);

        for(int i = 0; i < overdue.size(); i++){
            total += borrowService.calculateFine(overdue.get(i),currentDate);
        }

        return total;
    }

    // Reservation Reports

    public DynamicArray<Reservation> getAllReservations(){
        return reservationService.getAllReservations();
    }

    public DynamicArray<Reservation> getPendingReservations(){
        DynamicArray<Reservation> result = new DynamicArray<>();
        DynamicArray<Reservation> reservations = reservationService.getAllReservations();

        for(int i = 0; i < reservations.size(); i++){
            Reservation reservation = reservations.get(i);

            if(!reservation.isFulfilled() && !reservation.isCancelled()){
                result.add(reservation);
            }
        }

        return result;
    }

    public DynamicArray<Reservation> getFulfilledReservations(){
        DynamicArray<Reservation> result = new DynamicArray<>();
        DynamicArray<Reservation> reservations = reservationService.getAllReservations();

        for(int i = 0; i < reservations.size(); i++){
            if(reservations.get(i).isFulfilled()){
                result.add(reservations.get(i));
            }
        }

        return result;
    }

    public DynamicArray<Reservation> getCancelledReservations(){
        DynamicArray<Reservation> result = new DynamicArray<>();
        DynamicArray<Reservation> reservations = reservationService.getAllReservations();

        for(int i = 0; i < reservations.size(); i++){
            if(reservations.get(i).isCancelled()){
                result.add(reservations.get(i));
            }
        }

        return result;
    }

    public int getPendingReservationCount(){
        return getPendingReservations().size();
    }

    public int getFulfilledReservationCount(){
        return getFulfilledReservations().size();
    }

    public int getCancelledReservationCount(){
        return getCancelledReservations().size();
    }

    // Search Analytics

    public DynamicArray<SearchRecord> getSearchHistory(){
        return historyService.getAllSearches();
    }

    public int getTotalSearchCount(){
        return historyService.getSearchCount();
    }

    public int getUnsuccessfulSearchCount(){
        return historyService.getUnsuccessfulSearchCount();
    }

    public int getSuccessfulSearchCount(){
        return getTotalSearchCount() - getUnsuccessfulSearchCount();
    }

    public double getSearchSuccessRate(){
        int total = getTotalSearchCount();

        if(total == 0){
            return 0.0;
        }

        return (double) getSuccessfulSearchCount() / total * 100;
    }
}
