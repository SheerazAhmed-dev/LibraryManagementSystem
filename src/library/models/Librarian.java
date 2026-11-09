package library.models;
public class Librarian extends Person{
    private String designation;
    public Librarian(String id, String name, String email, String phone, String designation){
        super(id, name, email, phone);
        this.designation = designation;
    }
    public String getDesignation(){
        return designation;
    }
    public void setDesignation(String designation){
        this.designation = designation;
    }
    @Override 
    public void displayDetails(){
        System.out.println("Librarian ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone: " + getPhone());
        System.out.println("Designation: " + designation);
    }
}
