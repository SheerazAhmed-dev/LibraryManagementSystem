package library.models;
public abstract class Person{
    private String name;
    private String id;
    private String phone;
    private String email;

    public Person(String id, String name, String email, String phone){
        this.name = name;
        this.id = id;
        this.email = email;
        this.phone = phone;
    }
    public abstract void displayDetails();

    public String getEmail(){
        return email;
    }
    public String getName(){
        return name;
    }
    public String getPhone(){
        return phone;
    }
    public String getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public void setEmail(String email){
        this.email = email;
    }
}
