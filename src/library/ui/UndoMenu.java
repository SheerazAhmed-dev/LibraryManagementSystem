package library.ui;
import java.util.Scanner;
import library.services.UndoService;
import library.utilities.InputValidator;
public class UndoMenu{
    private Scanner scanner;
    private UndoService undoService;

    public UndoMenu(Scanner scanner,UndoService undoService){
        this.scanner = scanner;
        this.undoService = undoService;
    }

    public void showMenu(){
        int choice;
        do{
            System.out.println("\n========== UNDO OPERATIONS ==========");
            System.out.println("1. Undo Last Operation");
            System.out.println("2. View Undo History Count");
            System.out.println("3. Clear Undo History");
            System.out.println("0. Back to Main Menu");
            choice = InputValidator.getInt(scanner,"Enter your choice: ");
            switch(choice){
                case 1:
                    undoLastOperation();
                    break;
                case 2:
                    System.out.println("Undo Operations Available: " + undoService.getUndoCount());
                    break;
                case 3:
                    clearUndoHistory();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }while(choice != 0);
    }

    private void undoLastOperation(){
        if(!undoService.canUndo()){
            System.out.println("No operations available to undo!");
            return;
        }
        undoService.undo();
    }

    private void clearUndoHistory(){
        if(!undoService.canUndo()){
            System.out.println("Undo history is already empty!");
            return;
        }
        boolean confirm = InputValidator.getBoolean(
            scanner,"Are you sure you want to clear all undo history? (yes/no): "
        );
        if(confirm){
            undoService.clearHistory();
            System.out.println("Undo history cleared successfully!");
        }else{
            System.out.println("Operation cancelled.");
        }
    }
}
