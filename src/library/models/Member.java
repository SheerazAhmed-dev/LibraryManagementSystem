package library.models;
import java.time.LocalDate;

public class Member extends Person{
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
}
