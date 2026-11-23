
package library.services;

import library.models.Book;
import library.models.Member;
import library.models.Transaction;
import library.datastructures.DynamicArray;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowService{
    private BookService bookService;
    private MemberService memberService;
    private TransactionService transactionService;
    private final int borrowingPeriod;
    private double dailyFineRate;

    public BorrowService(BookService bookService,MemberService memberService,TransactionService transactionService){
        this(bookService,memberService,transactionService,100.0);
    }

    public BorrowService(BookService bookService,MemberService memberService,TransactionService transactionService,double dailyFineRate){
        if(bookService == null || memberService == null || transactionService == null){
            throw new IllegalArgumentException("Services cannot be null");
        }
        if(dailyFineRate < 0){
            throw new IllegalArgumentException("Fine rate cannot be negative");
        }

        this.bookService = bookService;
        this.memberService = memberService;
        this.transactionService = transactionService;
        this.borrowingPeriod = 7;
        this.dailyFineRate = dailyFineRate;
    }

    public Transaction borrowBook(String transactionId,String memberId,String isbn,LocalDate borrowDate){
        if(transactionId == null || transactionId.isBlank() ||
           memberId == null || memberId.isBlank() ||
           isbn == null || isbn.isBlank() || borrowDate == null){
            throw new IllegalArgumentException("Invalid borrowing details");
        }

        Member member = memberService.searchById(memberId);
        Book book = bookService.searchByISBN(isbn);

        if(member == null){
            throw new IllegalArgumentException("Member not found");
        }
        if(book == null){
            throw new IllegalArgumentException("Book not found");
        }
        if(!member.isActive()){
            throw new IllegalStateException("Inactive members cannot borrow books");
        }
        if(book.getAvailableCopies() <= 0){
            throw new IllegalStateException("No available copies of this book");
        }
        if(hasActiveBorrowing(memberId)){
            throw new IllegalStateException("Member already has an active borrowing");
        }

        LocalDate dueDate = borrowDate.plusDays(borrowingPeriod);
        Transaction transaction = new Transaction(transactionId,member,book,borrowDate,dueDate);

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        transactionService.addTransaction(transaction);

        return transaction;
    }

    public Transaction returnBook(String transactionId,LocalDate returnDate){
        if(transactionId == null || transactionId.isBlank() || returnDate == null){
            throw new IllegalArgumentException("Invalid return details");
        }

        Transaction transaction = transactionService.getTransactionById(transactionId);

        if(transaction == null){
            throw new IllegalArgumentException("Transaction not found");
        }
        if(transaction.isReturned()){
            throw new IllegalStateException("Book has already been returned");
        }

        double fine = calculateFine(transaction,returnDate);

        transaction.finishReturn(returnDate,fine);

        Book book = transaction.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        return transaction;
    }

    public double calculateFine(Transaction transaction,LocalDate returnDate){
        if(transaction == null || returnDate == null){
            throw new IllegalArgumentException("Invalid fine calculation details");
        }
        if(returnDate.isBefore(transaction.getBorrowDate())){
            throw new IllegalArgumentException("Return date cannot be before borrowing date");
        }

        if(!returnDate.isAfter(transaction.getDueDate())){
            return 0.0;
        }

        long overdueDays = ChronoUnit.DAYS.between(transaction.getDueDate(),returnDate);
        return overdueDays * dailyFineRate;
    }

    public boolean hasActiveBorrowing(String memberId){
        if(memberId == null || memberId.isBlank()){
            return false;
        }

        DynamicArray<Transaction> activeTransactions = transactionService.getActiveTransactions();

        for(int i = 0; i < activeTransactions.size(); i++){
            Transaction transaction = activeTransactions.get(i);

            if(transaction.getMember().getId().equalsIgnoreCase(memberId.trim())){
                return true;
            }
        }

        return false;
    }

    public DynamicArray<Transaction> getOverdueTransactions(LocalDate currentDate){
        DynamicArray<Transaction> result = new DynamicArray<>();

        if(currentDate == null){
            throw new IllegalArgumentException("Date cannot be null");
        }

        DynamicArray<Transaction> activeTransactions = transactionService.getActiveTransactions();

        for(int i = 0; i < activeTransactions.size(); i++){
            Transaction transaction = activeTransactions.get(i);

            if(currentDate.isAfter(transaction.getDueDate())){
                result.add(transaction);
            }
        }

        return result;
    }

    public void setDailyFineRate(double dailyFineRate){
        if(dailyFineRate < 0){
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
