package library.services;
import library.models.ActivityRecord;
import library.models.SearchRecord;
import library.datastructures.DynamicArray;
import java.time.LocalDate;

public class HistoryService{
    private DynamicArray<ActivityRecord> activities;
    private DynamicArray<SearchRecord> searches;
    private int activityCounter;
    private int searchCounter;

    public HistoryService(){
        activities = new DynamicArray<>();
        searches = new DynamicArray<>();
        activityCounter = 1;
        searchCounter = 1;
    }
    public ActivityRecord logActivity(String activityType,String description,String memberId,String bookIsbn){
        String activityId = "ACT-" + activityCounter++;
        ActivityRecord record = new ActivityRecord(
            activityId,activityType,description,memberId,bookIsbn
        );
        activities.add(record);
        return record;
    }
    public SearchRecord logSearch(String searchQuery,String searchType,int resultCount){
        String searchId = "SRCH-" + searchCounter++;
        SearchRecord record = new SearchRecord(
            searchId,searchQuery,searchType,resultCount
        );
        searches.add(record);
        return record;
    }
    public DynamicArray<ActivityRecord> getAllActivities(){
        DynamicArray<ActivityRecord> result = new DynamicArray<>();
        for(int i=0; i<activities.size(); i++){
            result.add(activities.get(i));
        }
        return result;
    }
    public DynamicArray<SearchRecord> getAllSearches(){
        DynamicArray<SearchRecord> result = new DynamicArray<>();

        for(int i=0; i<searches.size(); i++){
            result.add(searches.get(i));
        }
        return result;
    }
    public DynamicArray<ActivityRecord> getActivitiesByMember(String memberId){
        DynamicArray<ActivityRecord> result = new DynamicArray<>();
        if(memberId==null || memberId.isBlank()){
            return result;
        }
        for(int i=0; i<activities.size(); i++){
            ActivityRecord record = activities.get(i);
            if(record.getMemberId() != null &&
               record.getMemberId().equalsIgnoreCase(memberId.trim())){
                result.add(record);
            }
        }
        return result;
    }
    public DynamicArray<ActivityRecord> getActivitiesByBook(String isbn){
        DynamicArray<ActivityRecord> result = new DynamicArray<>();

        if(isbn==null || isbn.isBlank()){
            return result;
        }
        for(int i=0; i<activities.size(); i++){
            ActivityRecord record = activities.get(i);
            if(record.getBookIsbn() != null &&
               record.getBookIsbn().equalsIgnoreCase(isbn.trim())){
                result.add(record);
            }
        }
        return result;
    }
    public DynamicArray<ActivityRecord> getActivitiesByType(String activityType){
        DynamicArray<ActivityRecord> result = new DynamicArray<>();

        if(activityType==null || activityType.isBlank()){
            return result;
        }
        for(int i=0; i<activities.size(); i++){
            ActivityRecord record = activities.get(i);
            if(record.getActivityType().equalsIgnoreCase(activityType.trim())){
                result.add(record);
            }
        }
        return result;
    }
    public DynamicArray<ActivityRecord> getActivitiesByDate(LocalDate date){
        DynamicArray<ActivityRecord> result = new DynamicArray<>();

        if(date==null){
            return result;
        }
        for(int i=0; i<activities.size(); i++){
            ActivityRecord record = activities.get(i);
            if(record.getTimestamp().toLocalDate().equals(date)){
                result.add(record);
            }
        }
        return result;
    }
    public DynamicArray<SearchRecord> getSearchesByDate(LocalDate date){
        DynamicArray<SearchRecord> result = new DynamicArray<>();

        if(date==null){
            return result;
        }
        for(int i=0; i<searches.size(); i++){
            SearchRecord record = searches.get(i);
            if(record.getTimestamp().toLocalDate().equals(date)){
                result.add(record);
            }
        }
        return result;
    }
    public DynamicArray<SearchRecord> getSearchesByType(String searchType){
        DynamicArray<SearchRecord> result = new DynamicArray<>();

        if(searchType==null || searchType.isBlank()){
            return result;
        }
        for(int i=0; i<searches.size(); i++){
            SearchRecord record = searches.get(i);
            if(record.getSearchType().equalsIgnoreCase(searchType.trim())){
                result.add(record);
            }
        }
        return result;
    }
    public int getActivityCount(){
        return activities.size();
    }
    public int getSearchCount(){
        return searches.size();
    }
    public int getUnsuccessfulSearchCount(){
        int count = 0;
        for(int i=0; i<searches.size(); i++){
            if(!searches.get(i).hasResults()){
                count++;
            }
        }
        return count;
    }
    public void displayAllActivities(){
        if(activities.isEmpty()){
            System.out.println("No activity history available!");
            return;
        }
        for(int i=0; i<activities.size(); i++){
            activities.get(i).displayDetails();
            System.out.println("***********************");
        }
    }
    public void displayAllSearches(){
        if(searches.isEmpty()){
            System.out.println("No search history available!");
            return;
        }
        for(int i=0; i<searches.size(); i++){
            searches.get(i).displayDetails();
            System.out.println("***********************");
        }
    }
    public void clearHistory(){
        activities.clear();
        searches.clear();
        activityCounter = 1;
        searchCounter = 1;
    }
}
