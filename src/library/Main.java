package library;
import library.services.*;
import library.ui.ConsoleMenu;
public class Main{
    public static void main(String[] args){
        HistoryService historyService = new HistoryService();
        BookService bookService = new BookService(historyService);
        MemberService memberService = new MemberService(historyService);
        ReservationService reservationService = new ReservationService(historyService);
        TransactionService transactionService = new TransactionService();

        BorrowService borrowService = new BorrowService(
            bookService,
            memberService,
            transactionService,
            reservationService,
            historyService
        );
        ReportService reportService = new ReportService(
            bookService,memberService,transactionService,
            reservationService,borrowService,historyService
        );
        UndoService undoService = new UndoService(bookService);

        ConsoleMenu consoleMenu = new ConsoleMenu(
            bookService,memberService,borrowService,
            reservationService,reportService,historyService,
            undoService
        );
        consoleMenu.start();
    }
}
