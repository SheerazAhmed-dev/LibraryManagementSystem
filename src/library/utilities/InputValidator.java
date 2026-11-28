package library.utilities;
import java.util.Scanner;
public class InputValidator{
    public static String getString(Scanner scanner,String message){
        while(true){
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if(!input.isBlank()){
                return input;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
    }

    public static int getInt(Scanner scanner,String message){
        while(true){
            System.out.print(message);
            try{
                return Integer.parseInt(scanner.nextLine().trim());
            }
            catch(NumberFormatException e){
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    public static int getPositiveInt(Scanner scanner,String message){
        while(true){
            int value = getInt(scanner,message);
            if(value > 0){
                return value;
            }
            System.out.println("Value must be greater than zero.");
        }
    }

    public static double getDouble(Scanner scanner,String message){
        while(true){
            System.out.print(message);
            try{
                return Double.parseDouble(scanner.nextLine().trim());
            }
            catch(NumberFormatException e){
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static double getNonNegativeDouble(Scanner scanner,String message){
        while(true){
            double value = getDouble(scanner,message);
            if(value >= 0){
                return value;
            }
            System.out.println("Value cannot be negative.");
        }
    }

    public static boolean getBoolean(Scanner scanner,String message){
        while(true){
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")){
                return true;
            }
            if(input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")){
                return false;
            }
            System.out.println("Please enter yes or no.");
        }
    }
}
