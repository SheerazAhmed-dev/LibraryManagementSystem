package library.services;
import library.models.Book;
import library.models.Member;
import library.models.Reservation;
import library.datastructures.CircularQueue;
import library.datastructures.DynamicArray;
import library.datastructures.HashTable;

public class ReservationService{
    private HashTable<String,CircularQueue<Reservation>> reservationQueues;
    private DynamicArray<Reservation> reservations;
    private HistoryService historyService;
    public ReservationService(){
        this(new HistoryService());
    }
    public ReservationService(HistoryService historyService){
        if(historyService==null){
            throw new IllegalArgumentException("History service cannot be null");
        }
        reservationQueues = new HashTable<>();
        reservations = new DynamicArray<>();
        this.historyService = historyService;
    }

    public Reservation reserveBook(String reservationId,Member member,Book book){
        if(reservationId==null || reservationId.isBlank() || member==null || book==null){
            throw new IllegalArgumentException("Invalid reservation details");
        }
        if(!member.isActive()){
            throw new IllegalArgumentException("Inactive members cannot reserve books");
        }
        if(book.getAvailableCopies()>0){
            throw new IllegalArgumentException("Book is currently available. Reservation is not required");
        }
        if(getReservationById(reservationId) != null){
            throw new IllegalArgumentException("Reservation ID already exists");
        }
        for(int i=0; i<reservations.size(); i++){
            Reservation existing = reservations.get(i);
            if(existing.getMember().getId().equalsIgnoreCase(member.getId()) &&
               existing.getBook().getIsbn().equalsIgnoreCase(book.getIsbn()) &&
               !existing.isCancelled() && !existing.isFulfilled()){
                throw new IllegalArgumentException("Member already has a pending reservation for this book");
            }
        }
        Reservation reservation = new Reservation(reservationId,member,book);

        String isbn = book.getIsbn();
        CircularQueue<Reservation> queue = reservationQueues.get(isbn);
        if(queue==null){
            queue = new CircularQueue<>();
            reservationQueues.put(isbn,queue);
        }
        queue.enqueue(reservation);
        reservations.add(reservation);
        historyService.logActivity(
            "BOOK_RESERVED",
            "Reserved book: " + book.getTitle() + " | Reservation: " + reservationId,
            member.getId(),isbn
        );
        return reservation;
    }

    public boolean cancelReservation(String reservationId){
        Reservation reservation = getReservationById(reservationId);
        if(reservation==null || reservation.isCancelled() || reservation.isFulfilled()){
            return false;
        }
        reservation.cancel();
        historyService.logActivity(
            "RESERVATION_CANCELLED","Cancelled reservation: " + reservationId,
            reservation.getMember().getId(),reservation.getBook().getIsbn()
        );
        return true;
    }

    public Reservation getNextReservation(String isbn){
        if(isbn==null || isbn.isBlank()){
            return null;
        }
        String key = isbn.trim();
        CircularQueue<Reservation> queue = reservationQueues.get(key);
        if(queue==null){
            return null;
        }
        while(!queue.isEmpty()){
            Reservation reservation = queue.peek();
            if(reservation.isCancelled() || reservation.isFulfilled()){
                queue.dequeue();
            }
            else{
                return reservation;
            }
        }
        reservationQueues.remove(key);
        return null;
    }

    public boolean fulfillReservation(String reservationId){
        Reservation reservation = getReservationById(reservationId);
        if(reservation==null || reservation.isCancelled() || reservation.isFulfilled()){
            return false;
        }
        String isbn = reservation.getBook().getIsbn();
        CircularQueue<Reservation> queue = reservationQueues.get(isbn);
        if(queue==null || getNextReservation(isbn) != reservation){
            return false;
        }
        queue.dequeue();
        reservation.fulfill();
        if(queue.isEmpty()){
            reservationQueues.remove(isbn);
        }
        historyService.logActivity(
            "RESERVATION_FULFILLED",
            "Fulfilled reservation: " + reservationId,reservation.getMember().getId(),isbn
        );
        return true;
    }
    public Reservation processNextReservation(String isbn){
        Reservation reservation = getNextReservation(isbn);
        if(reservation==null){
            return null;
        }
        if(fulfillReservation(reservation.getReservationId())){
            return reservation;
        }
        return null;
    }

    public Reservation getReservationById(String reservationId){
        if(reservationId==null || reservationId.isBlank()){
            return null;
        }
        for(int i=0; i<reservations.size(); i++){
            Reservation reservation = reservations.get(i);
            if(reservation.getReservationId().equalsIgnoreCase(reservationId.trim())){
                return reservation;
            }
        }
        return null;
    }
    public DynamicArray<Reservation> getReservationsByBook(String isbn){
        DynamicArray<Reservation> result = new DynamicArray<>();
        if(isbn==null || isbn.isBlank()){
            return result;
        }
        for(int i=0; i<reservations.size(); i++){
            Reservation reservation = reservations.get(i);
            if(reservation.getBook().getIsbn().equalsIgnoreCase(isbn.trim())){
                result.add(reservation);
            }
        }
        return result;
    }

    public int getWaitingCount(String isbn){
        if(isbn==null || isbn.isBlank()){
            return 0;
        }
        int count = 0;
        for(int i = 0; i < reservations.size(); i++){
            Reservation reservation = reservations.get(i);
            if(reservation.getBook().getIsbn().equalsIgnoreCase(isbn.trim()) &&
               !reservation.isCancelled() && !reservation.isFulfilled()){
                count++;
            }
        }
        return count;
    }

    public DynamicArray<Reservation> getAllReservations(){
        DynamicArray<Reservation> result = new DynamicArray<>();
        for(int i=0; i<reservations.size(); i++){
            result.add(reservations.get(i));
        }
        return result;
    }

    public int getReservationCount(){
        return reservations.size();
    }

    public void displayReservations(String isbn){
        DynamicArray<Reservation> result = getReservationsByBook(isbn);
        if(result.isEmpty()){
            System.out.println("No reservations found!");
            return;
        }
        for(int i=0; i<result.size(); i++){
            result.get(i).displayDetails();
            System.out.println("***********************");
        }
    }

    public void displayAllReservations(){
        if(reservations.isEmpty()){
            System.out.println("No reservations available!");
            return;
        }
        for(int i=0; i<reservations.size(); i++){
            reservations.get(i).displayDetails();
            System.out.println("***********************");
        }
    }
}
