package library.services;
import library.models.Book;
import library.models.Member;
import library.models.Transaction;
import library.models.Reservation;
import library.datastructures.DynamicArray;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowService{
    private BookService bookService;
    private MemberService memberService;
    private TransactionService transactionService;
    private ReservationService reservationService;
    private HistoryService historyService;
    private final int borrowingPeriod;
    private double dailyFineRate;

    public BorrowService(BookService bookService,MemberService memberService,TransactionService transactionService){
        this(bookService,memberService,transactionService,null,100.0,new HistoryService());
    }
    public BorrowService(BookService bookService,MemberService memberService,TransactionService transactionService,double dailyFineRate){
        this(bookService,memberService,transactionService,null,dailyFineRate,new HistoryService());
    }
    public BorrowService(BookService bookService,MemberService memberService,TransactionService transactionService,HistoryService historyService){
        this(bookService,memberService,transactionService,null,100.0,historyService);
    }
    public BorrowService(BookService bookService,MemberService memberService,TransactionService transactionService,ReservationService reservationService,HistoryService historyService){
        this(bookService,memberService,transactionService,reservationService,100.0,historyService);
    }
    public BorrowService(BookService bookService,MemberService memberService,TransactionService transactionService,double dailyFineRate,HistoryService historyService){
        this(bookService,memberService,transactionService,null,dailyFineRate,historyService);
    }
    public BorrowService(BookService bookService,MemberService memberService,TransactionService transactionService,ReservationService reservationService,double dailyFineRate,HistoryService historyService){
        if(bookService==null || memberService==null || transactionService==null || historyService==null){
            throw new IllegalArgumentException("Services cannot be null");
        }
        if(dailyFineRate<0){
            throw new IllegalArgumentException("Fine rate cannot be negative");
        }
        this.bookService = bookService;
        this.memberService = memberService;
        this.transactionService = transactionService;
        this.reservationService = reservationService;
        this.historyService = historyService;
        this.borrowingPeriod = 7;
        this.dailyFineRate = dailyFineRate;
    }
    public Transaction borrowBook(String transactionId,String memberId,String isbn,LocalDate borrowDate){
        return borrowBookInternal(transactionId,memberId,isbn,borrowDate,false);
    }
    public Transaction borrowReservedBook(String transactionId,String reservationId,LocalDate borrowDate){
        if(reservationService==null){
            throw new IllegalStateException("Reservation service is not connected");
        }
        if(reservationId==null || reservationId.isBlank()){
            throw new IllegalArgumentException("Invalid reservation ID");
        }
        Reservation reservation = reservationService.getReservationById(reservationId);
        if(reservation==null || reservation.isCancelled() || reservation.isFulfilled()){
            throw new IllegalArgumentException("Active reservation not found");
        }
        Reservation nextReservation = reservationService.getNextReservation(reservation.getBook().getIsbn());
        if(nextReservation != reservation){
            throw new IllegalStateException("This reservation is not next in the waiting queue");
        }
        Transaction transaction = borrowBookInternal(transactionId,reservation.getMember().getId(),
                                                    reservation.getBook().getIsbn(),borrowDate,true);

        if(!reservationService.fulfillReservation(reservationId)){
            transaction.finishReturn(borrowDate,0.0);
            Book book = transaction.getBook();
            book.setAvailableCopies(book.getAvailableCopies()+1);
            throw new IllegalStateException("Unable to fulfil reservation");
        }
        return transaction;
    }
    private Transaction borrowBookInternal(String transactionId,String memberId,String isbn,LocalDate borrowDate,boolean reserved){
        if(transactionId==null || transactionId.isBlank()||
           memberId==null || memberId.isBlank() || isbn==null || isbn.isBlank() || borrowDate==null){
            throw new IllegalArgumentException("Invalid borrowing details");
        }
        if(transactionService.getTransactionById(transactionId) != null){
            throw new IllegalArgumentException("Transaction ID already exists");
        }
        Member member = memberService.searchById(memberId);
        Book book = bookService.searchByISBN(isbn);
        if(member==null){
            throw new IllegalArgumentException("Member not found");
        }
        if(book==null){
            throw new IllegalArgumentException("Book not found");
        }
        if(!member.isActive()){
            throw new IllegalStateException("Inactive members cannot borrow books");
        }
        if(book.getAvailableCopies()<=0){
            throw new IllegalStateException("No available copies of this book");
        }
        if(hasActiveBorrowing(memberId)){
            throw new IllegalStateException("Member already has an active borrowing");
        }
        if(!reserved && reservationService != null && reservationService.getWaitingCount(isbn)>0){
            throw new IllegalStateException("This book has pending reservations");
        }
        LocalDate dueDate = borrowDate.plusDays(borrowingPeriod);
        Transaction transaction = new Transaction(transactionId,member,book,borrowDate,dueDate);
        transactionService.addTransaction(transaction);
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        historyService.logActivity(
            "BOOK_BORROWED",
            "Borrowed book: " + book.getTitle() + " | Transaction: " + transactionId,
            member.getId(),book.getIsbn()
        );
        return transaction;
    }
    public Transaction returnBook(String transactionId,LocalDate returnDate){
        if(transactionId==null || transactionId.isBlank() || returnDate==null){
            throw new IllegalArgumentException("Invalid return details");
        }
        Transaction transaction = transactionService.getTransactionById(transactionId);
        if(transaction==null){
            throw new IllegalArgumentException("Transaction not found");
        }
        if(transaction.isReturned()){
            throw new IllegalStateException("Book has already been returned");
        }
        double fine = calculateFine(transaction,returnDate);
        transaction.finishReturn(returnDate,fine);
        Book book = transaction.getBook();
        book.setAvailableCopies(book.getAvailableCopies()+1);
        historyService.logActivity(
            "BOOK_RETURNED",
            "Returned book: " + book.getTitle() + " | Fine: PKR " + fine,
            transaction.getMember().getId(),book.getIsbn()
        );
        if(reservationService != null && reservationService.getWaitingCount(book.getIsbn())>0){
            historyService.logActivity(
                "BOOK_HELD_FOR_RESERVATION",
                "Returned book held for the next reservation: " + book.getTitle(),null,book.getIsbn()
            );
        }
        return transaction;
    }
    public double calculateFine(Transaction transaction,LocalDate returnDate){
        if(transaction==null || returnDate==null){
            throw new IllegalArgumentException("Invalid fine calculation details");
        }
        if(returnDate.isBefore(transaction.getBorrowDate())){
            throw new IllegalArgumentException("Return date cannot be before borrowing date");
        }
        if(!returnDate.isAfter(transaction.getDueDate())){
            return 0.0;
        }
        long overdueDays = ChronoUnit.DAYS.between(transaction.getDueDate(),returnDate);
        return overdueDays*dailyFineRate;
    }
    public boolean hasActiveBorrowing(String memberId){
        if(memberId==null || memberId.isBlank()){
            return false;
        }
        DynamicArray<Transaction> activeTransactions = transactionService.getActiveTransactions();
        for(int i=0; i<activeTransactions.size(); i++){
            Transaction transaction = activeTransactions.get(i);
            if(transaction.getMember().getId().equalsIgnoreCase(memberId.trim())){
                return true;
            }
        }
        return false;
    }
    public DynamicArray<Transaction> getOverdueTransactions(LocalDate currentDate){
        DynamicArray<Transaction> result = new DynamicArray<>();
        if(currentDate==null){
            throw new IllegalArgumentException("Date cannot be null");
        }
        DynamicArray<Transaction> activeTransactions = transactionService.getActiveTransactions();

        for(int i=0; i<activeTransactions.size(); i++){
            Transaction transaction = activeTransactions.get(i);
            if(currentDate.isAfter(transaction.getDueDate())){
                result.add(transaction);
            }
        }
        return result;
    }
    public void setDailyFineRate(double dailyFineRate){
        if(dailyFineRate<0){
            throw new IllegalArgumentException("Fine rate cannot be negative");
        }
        this.dailyFineRate = dailyFineRate;
    }
    public double getDailyFineRate(){
        return dailyFineRate;
    }
    public int getBorrowingPeriod(){
        return borrowingPeriod;
    }
}
