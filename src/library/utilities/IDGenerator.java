package library.utilities;
public class IDGenerator{
    private static int memberCounter = 1;
    private static int transactionCounter = 1;
    private static int reservationCounter = 1;

    public static String generateMemberId(){
        return "MEM-" + memberCounter++;
    }

    public static String generateTransactionId(){
        return "TRX-" + transactionCounter++;
    }

    public static String generateReservationId(){
        return "RES-" + reservationCounter++;
    }
}
