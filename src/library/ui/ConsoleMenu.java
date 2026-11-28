package library.ui;
import library.services.*;
import java.util.Scanner;
public class ConsoleMenu{
    private Scanner scanner;
    private BookService bookService;
    private MemberService memberService;
    private BorrowService borrowService;
    private ReservationService reservationService;
    private ReportService reportService;
    private HistoryService historyService;
    private UndoService undoService;

    public ConsoleMenu(BookService bookService,MemberService memberService,
                       BorrowService borrowService,ReservationService reservationService,
                       ReportService reportService,HistoryService historyService,
                       UndoService undoService){
        this.scanner = new Scanner(System.in);
        this.bookService = bookService;
        this.memberService = memberService;
        this.borrowService = borrowService;
        this.reservationService = reservationService;
        this.reportService = reportService;
        this.historyService = historyService;
        this.undoService = undoService;
    }

    public void start(){
        boolean running = true;
        while(running){
            System.out.println("\n=================================");
            System.out.println("       LIBRARY MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Book Management");
            System.out.println("2. Member Management");
            System.out.println("3. Borrowing Management");
            System.out.println("4. Reservation Management");
            System.out.println("5. Reports and Analytics");
            System.out.println("6. History and Activity Logs");
            System.out.println("7. Undo Operations");
            System.out.println("0. Exit");
            System.out.println("=================================");
            int choice = getMenuChoice();
            switch(choice){
                case 1:
                    new BookMenu(scanner,bookService,undoService).showMenu();
                    break;
                case 2:
                    new MemberMenu(scanner,memberService).showMenu();
                    break;
                case 3:
                    new BorrowMenu(scanner,borrowService,memberService,bookService).showMenu();
                    break;
                case 4:
                    new ReservationMenu(scanner,reservationService,memberService,bookService,borrowService).showMenu();
                    break;
                case 5:
                    new ReportMenu(scanner,reportService).showMenu();
                    break;
                case 6:
                    new HistoryMenu(scanner,historyService).showMenu();
                    break;
                case 7:
                    new UndoMenu(scanner,undoService).showMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using the Library Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int getMenuChoice(){
        System.out.print("Enter your choice: ");
        try{
            return Integer.parseInt(scanner.nextLine().trim());
        }
        catch(NumberFormatException e){
            return -1;
        }
    }
}