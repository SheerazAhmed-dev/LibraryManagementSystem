package library.models;
import java.time.LocalDate;
import library.interfaces.Searchable;

public class Member extends Person implements Searchable{
    private boolean activeStatus;
    private LocalDate registrationDate;
    public Member(String id, String name, String email, String phone){
        super(id, name, email, phone);
        this.activeStatus = true;
        this.registrationDate = LocalDate.now();
    }
    public boolean isActive(){
        return activeStatus;
    }
    public LocalDate getRegistrationDate(){
        return registrationDate;
    }
    public void setActiveStatus(boolean status){
        this.activeStatus = status;
    }
    @Override 
    public void displayDetails(){
        System.out.println("Member ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone: " + getPhone());
        System.out.println("Registration Date: " + registrationDate);
        System.out.println("Membership Status: " + (activeStatus ? "Active" : "Inactive"));
    }
    @Override
    public boolean matches(String query){
        if(query==null || query.trim().isEmpty()){
            return false;
        }
        query = query.trim().toLowerCase();

        return getName().toLowerCase().contains(query)||
           getId().toLowerCase().contains(query)||
           getPhone().toLowerCase().contains(query)||
           getEmail().toLowerCase().contains(query);
    }
}
