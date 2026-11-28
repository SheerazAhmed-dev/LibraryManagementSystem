package library.ui;
import java.util.Scanner;
import java.time.LocalDate;
import library.services.HistoryService;
import library.models.ActivityRecord;
import library.models.SearchRecord;
import library.datastructures.DynamicArray;
import library.utilities.InputValidator;
import library.utilities.DateUtility;
public class HistoryMenu{
    private Scanner scanner;
    private HistoryService historyService;

    public HistoryMenu(Scanner scanner,HistoryService historyService){
        this.scanner = scanner;
        this.historyService = historyService;
    }
    public void showMenu(){
        int choice;
        do{
            System.out.println("\n========== HISTORY AND ACTIVITY LOGS ==========");
            System.out.println("1. View All Activity Logs");
            System.out.println("2. View All Search History");
            System.out.println("3. Search Activities by Member ID");
            System.out.println("4. Search Activities by Book ISBN");
            System.out.println("5. Search Activities by Event Type");
            System.out.println("6. Search Activities by Date");
            System.out.println("7. Search History by Search Type");
            System.out.println("8. Search History by Date");
            System.out.println("9. View History Statistics");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = InputValidator.getInt(scanner,"");
            switch(choice){
                case 1:
                    displayActivities(historyService.getAllActivities());
                    break;
                case 2:
                    displaySearches(historyService.getAllSearches());
                    break;
                case 3:
                    activitiesByMember();
                    break;
                case 4:
                    activitiesByBook();
                    break;
                case 5:
                    activitiesByType();
                    break;
                case 6:
                    activitiesByDate();
                    break;
                case 7:
                    searchesByType();
                    break;
                case 8:
                    searchesByDate();
                    break;
                case 9:
                    displayStatistics();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }while(choice != 0);
    }

    private void activitiesByMember(){
        String memberId = InputValidator.getString(scanner,"Enter Member ID: ");
        displayActivities(historyService.getActivitiesByMember(memberId));
    }
    private void activitiesByBook(){
        String isbn = InputValidator.getString(scanner,"Enter Book ISBN: ");
        displayActivities(historyService.getActivitiesByBook(isbn));
    }
    private void activitiesByType(){
        String type = InputValidator.getString(scanner,"Enter Activity Type: ");
        displayActivities(historyService.getActivitiesByType(type));
    }
    private void activitiesByDate(){
        LocalDate date = DateUtility.getDate(scanner,"Enter Date (dd-MM-yyyy): ");
        displayActivities(historyService.getActivitiesByDate(date));
    }
    private void searchesByType(){
        String type = InputValidator.getString(scanner,"Enter Search Type: ");
        displaySearches(historyService.getSearchesByType(type));
    }
    private void searchesByDate(){
        LocalDate date = DateUtility.getDate(scanner,"Enter Date (dd-MM-yyyy): ");
        displaySearches(historyService.getSearchesByDate(date));
    }

    private void displayActivities(DynamicArray<ActivityRecord> activities){
        if(activities.isEmpty()){
            System.out.println("No activity records found!");
            return;
        }
        for(int i=0; i<activities.size(); i++){
            activities.get(i).displayDetails();
            System.out.println("***********************");
        }
    }

    private void displaySearches(DynamicArray<SearchRecord> searches){
        if(searches.isEmpty()){
            System.out.println("No search records found!");
            return;
        }
        for(int i=0; i<searches.size(); i++){
            searches.get(i).displayDetails();
            System.out.println("**********************");
        }
    }

    private void displayStatistics(){
        System.out.println("\n========== HISTORY STATISTICS ==========");
        System.out.println("Total Activities: " + historyService.getActivityCount());
        System.out.println("Total Searches: " + historyService.getSearchCount());
        System.out.println("Unsuccessful Searches: " + historyService.getUnsuccessfulSearchCount());
        System.out.println("Successful Searches: " +
            (historyService.getSearchCount() - historyService.getUnsuccessfulSearchCount()));
    }
}
