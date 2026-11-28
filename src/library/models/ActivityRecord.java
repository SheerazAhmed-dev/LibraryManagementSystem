package library.models;
import java.time.LocalDateTime;

public class ActivityRecord{
    private String activityId;
    private String activityType;
    private String description;
    private String memberId;
    private String bookIsbn;
    private LocalDateTime timestamp;

    public ActivityRecord(String activityId,String activityType,String description,String memberId,String bookIsbn){
        if(activityId==null || activityId.isBlank() ||
           activityType==null || activityType.isBlank() ||
           description==null || description.isBlank()){
            throw new IllegalArgumentException("Invalid activity details");
        }
        this.activityId = activityId;
        this.activityType = activityType;
        this.description = description;
        this.memberId = memberId;
        this.bookIsbn = bookIsbn;
        this.timestamp = LocalDateTime.now();
    }

    public String getActivityId(){
        return activityId;
    }

    public String getActivityType(){
        return activityType;
    }

    public String getDescription(){
        return description;
    }

    public String getMemberId(){
        return memberId;
    }

    public String getBookIsbn(){
        return bookIsbn;
    }

    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public void displayDetails(){
        System.out.println("Activity ID: " + activityId);
        System.out.println("Activity Type: " + activityType);
        System.out.println("Description: " + description);
        System.out.println("Member ID: " + (memberId==null ? "N/A" : memberId));
        System.out.println("Book ISBN: " + (bookIsbn==null ? "N/A" : bookIsbn));
        System.out.println("Timestamp: " + timestamp);
    }
}
