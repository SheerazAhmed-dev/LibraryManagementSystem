
package library.models;

import java.time.LocalDateTime;

public class SearchRecord{
    private String searchId;
    private String searchQuery;
    private String searchType;
    private int resultCount;
    private LocalDateTime timestamp;

    public SearchRecord(String searchId,String searchQuery,String searchType,int resultCount){
        if(searchId == null || searchId.isBlank() ||
           searchQuery == null || searchQuery.isBlank() ||
           searchType == null || searchType.isBlank() || resultCount < 0){
            throw new IllegalArgumentException("Invalid search details");
        }

        this.searchId = searchId;
        this.searchQuery = searchQuery;
        this.searchType = searchType;
        this.resultCount = resultCount;
        this.timestamp = LocalDateTime.now();
    }

    public String getSearchId(){
        return searchId;
    }

    public String getSearchQuery(){
        return searchQuery;
    }

    public String getSearchType(){
        return searchType;
    }

    public int getResultCount(){
        return resultCount;
    }

    public boolean hasResults(){
        return resultCount > 0;
    }

    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public void displayDetails(){
        System.out.println("Search ID: " + searchId);
        System.out.println("Search Query: " + searchQuery);
        System.out.println("Search Type: " + searchType);
        System.out.println("Result Count: " + resultCount);
        System.out.println("Results Found: " + (hasResults() ? "Yes" : "No"));
        System.out.println("Timestamp: " + timestamp);
    }
}
