
package library.services;

import library.models.Transaction;
import library.datastructures.SinglyLinkedList;
import library.datastructures.DynamicArray;

public class TransactionService{
    private SinglyLinkedList<Transaction> transactions;

    public TransactionService(){
        transactions = new SinglyLinkedList<>();
    }

    public void addTransaction(Transaction transaction){
        if(transaction == null){
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        if(getTransactionById(transaction.getTransactionId()) != null){
            throw new IllegalArgumentException("Transaction ID already exists");
        }

        transactions.addLast(transaction);
    }

    public Transaction getTransactionById(String transactionId){
        if(transactionId == null || transactionId.isBlank()){
            return null;
        }

        for(int i = 0; i < transactions.size(); i++){
            Transaction transaction = transactions.get(i);

            if(transaction.getTransactionId().equalsIgnoreCase(transactionId.trim())){
                return transaction;
            }
        }

        return null;
    }

    public DynamicArray<Transaction> getTransactionsByMember(String memberId){
        DynamicArray<Transaction> result = new DynamicArray<>();

        if(memberId == null || memberId.isBlank()){
            return result;
        }

        for(int i = 0; i < transactions.size(); i++){
            Transaction transaction = transactions.get(i);

            if(transaction.getMember().getId().equalsIgnoreCase(memberId.trim())){
                result.add(transaction);
            }
        }

        return result;
    }

    public DynamicArray<Transaction> getTransactionsByBook(String isbn){
        DynamicArray<Transaction> result = new DynamicArray<>();

        if(isbn == null || isbn.isBlank()){
            return result;
        }

        for(int i = 0; i < transactions.size(); i++){
            Transaction transaction = transactions.get(i);

            if(transaction.getBook().getIsbn().equalsIgnoreCase(isbn.trim())){
                result.add(transaction);
            }
        }

        return result;
    }

    public DynamicArray<Transaction> getActiveTransactions(){
        DynamicArray<Transaction> result = new DynamicArray<>();

        for(int i = 0; i < transactions.size(); i++){
            Transaction transaction = transactions.get(i);

            if(!transaction.isReturned()){
                result.add(transaction);
            }
        }

        return result;
    }

    public DynamicArray<Transaction> getAllTransactions(){
        DynamicArray<Transaction> result = new DynamicArray<>();

        for(int i = 0; i < transactions.size(); i++){
            result.add(transactions.get(i));
        }

        return result;
    }

    public int getTransactionCount(){
        return transactions.size();
    }

    public int getActiveTransactionCount(){
        int count = 0;

        for(int i = 0; i < transactions.size(); i++){
            if(!transactions.get(i).isReturned()){
                count++;
            }
        }

        return count;
    }

    public void displayAllTransactions(){
        if(transactions.isEmpty()){
            System.out.println("No transactions available!");
            return;
        }

        for(int i = 0; i < transactions.size(); i++){
            transactions.get(i).displayDetails();
            System.out.println("-------------------------");
        }
    }

    public void displayActiveTransactions(){
        DynamicArray<Transaction> active = getActiveTransactions();

        if(active.isEmpty()){
            System.out.println("No active transactions!");
            return;
        }

        for(int i = 0; i < active.size(); i++){
            active.get(i).displayDetails();
            System.out.println("-------------------------");
        }
    }
}
