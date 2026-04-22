import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDateTime timestamp;
    private double amount;
    private String type; 
    private String status;

    public Transaction(double amount, String type, String status) {
        this.timestamp = LocalDateTime.now(); 
        this.amount = amount;
        this.type = type;
        this.status = status;
    }

    public void printTransaction() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = timestamp.format(formatter);
        System.out.println("[" + formattedTime + "] " + type + " | Rp" + amount + " | Status: " + status);
    }
}
