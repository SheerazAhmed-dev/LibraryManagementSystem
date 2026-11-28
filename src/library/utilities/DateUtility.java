package library.utilities;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class DateUtility{
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static LocalDate getDate(Scanner scanner,String message){
        while(true){
            System.out.print(message + " (dd-MM-yyyy): ");
            String input = scanner.nextLine().trim();
            try{
                return LocalDate.parse(input,FORMATTER);
            }
            catch(DateTimeParseException e){
                System.out.println("Invalid date. Please use dd-MM-yyyy format.");
            }
        }
    }

    public static String formatDate(LocalDate date){
        if(date == null){
            return "N/A";
        }
        return date.format(FORMATTER);
    }

    public static LocalDate getCurrentDate(){
        return LocalDate.now();
    }
}
