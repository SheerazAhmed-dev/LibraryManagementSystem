package library.ui;
import library.models.Member;
import library.services.MemberService;
import library.utilities.InputValidator;
import library.datastructures.DynamicArray;
import java.util.Scanner;

public class MemberMenu{
    private Scanner scanner;
    private MemberService memberService;

    public MemberMenu(Scanner scanner,MemberService memberService){
        this.scanner = scanner;
        this.memberService = memberService;
    }
    public void showMenu(){
        boolean running = true;
        while(running){
            System.out.println("\n========== MEMBER MANAGEMENT ==========");
            System.out.println("1. Register Member");
            System.out.println("2. Search Member by ID");
            System.out.println("3. Search Member by Name");
            System.out.println("4. Update Member");
            System.out.println("5. Deactivate Member");
            System.out.println("6. Reactivate Member");
            System.out.println("7. Delete Member");
            System.out.println("8. Display All Members");
            System.out.println("9. Sort Members by Name");
            System.out.println("10. View Member Count");
            System.out.println("0. Back to Main Menu");
            System.out.println("=======================================");
            int choice = InputValidator.getInt(scanner,"Enter your choice: ");
            switch(choice){
                case 1:
                    registerMember();
                    break;
                case 2:
                    searchById();
                    break;
                case 3:
                    searchByName();
                    break;
                case 4:
                    updateMember();
                    break;
                case 5:
                    deactivateMember();
                    break;
                case 6:
                    reactivateMember();
                    break;
                case 7:
                    deleteMember();
                    break;
                case 8:
                    memberService.displayAllMembers();
                    break;
                case 9:
                    sortMembers();
                    break;
                case 10:
                    System.out.println("Total Members: " + memberService.getMemberCount());
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void registerMember(){
        try{
            System.out.println("\n========== REGISTER MEMBER ==========");
            String id = InputValidator.getString(scanner,"Enter member ID: ");
            String name = InputValidator.getString(scanner,"Enter name: ");
            String email = InputValidator.getString(scanner,"Enter email: ");
            String phone = InputValidator.getString(scanner,"Enter phone: ");
            Member member = new Member(id,name,email,phone);
            memberService.registerMember(member);
            System.out.println("Member registered successfully!");
        }
        catch(IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void searchById(){
        String id = InputValidator.getString(scanner,"Enter member ID: ");
        Member member = memberService.searchById(id);
        if(member == null){
            System.out.println("Member not found!");
        }
        else{
            member.displayDetails();
        }
    }

    private void searchByName(){
        String name = InputValidator.getString(scanner,"Enter member name: ");
        DynamicArray<Member> result = memberService.searchByName(name);
        displaySearchResults(result);
    }

    private void updateMember(){
        try{
            System.out.println("\n========== UPDATE MEMBER ==========");
            String id = InputValidator.getString(scanner,"Enter member ID: ");
            Member member = memberService.searchById(id);
            if(member == null){
                System.out.println("Member not found!");
                return;
            }
            System.out.println("\nCurrent Member Details:");
            member.displayDetails();
            System.out.println("\nEnter New Details:");
            String name = InputValidator.getString(scanner,"Enter new name: ");
            String email = InputValidator.getString(scanner,"Enter new email: ");
            String phone = InputValidator.getString(scanner,"Enter new phone: ");
            memberService.updateMember(id,name,email,phone);
            System.out.println("Member updated successfully!");
        }
        catch(IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deactivateMember(){
        String id = InputValidator.getString(scanner,"Enter member ID: ");
        if(memberService.deactivateMember(id)){
            System.out.println("Member deactivated successfully!");
        }
        else{
            System.out.println("Member not found or already inactive!");
        }
    }

    private void reactivateMember(){
        String id = InputValidator.getString(scanner,"Enter member ID: ");
        if(memberService.reactivateMember(id)){
            System.out.println("Member reactivated successfully!");
        }
        else{
            System.out.println("Member not found or already active!");
        }
    }

    private void deleteMember(){
        try{
            String id = InputValidator.getString(scanner,"Enter member ID: ");
            Member member = memberService.searchById(id);
            if(member == null){
                System.out.println("Member not found!");
                return;
            }
            member.displayDetails();
            boolean confirm = InputValidator.getBoolean(scanner,"Are you sure you want to delete this member? (yes/no): ");
            if(!confirm){
                System.out.println("Deletion cancelled.");
                return;
            }
            if(memberService.deleteMember(id)){
                System.out.println("Member deleted successfully!");
            }
            else{
                System.out.println("Unable to delete member!");
            }
        }
        catch(IllegalArgumentException | IllegalStateException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void sortMembers(){
        DynamicArray<Member> sortedMembers = memberService.sortByName();
        if(sortedMembers.isEmpty()){
            System.out.println("No members registered!");
            return;
        }
        System.out.println("\n========== MEMBERS SORTED BY NAME ==========");
        for(int i = 0; i < sortedMembers.size(); i++){
            sortedMembers.get(i).displayDetails();
            System.out.println("***********************");
        }
    }

    private void displaySearchResults(DynamicArray<Member> result){
        if(result.isEmpty()){
            System.out.println("No matching members found!");
            return;
        }
        System.out.println("\nSearch Results: " + result.size());
        for(int i = 0; i < result.size(); i++){
            result.get(i).displayDetails();
            System.out.println("***********************");
        }
    }
}
