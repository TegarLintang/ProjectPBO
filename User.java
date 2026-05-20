import java.util.ArrayList;
import java.util.List;

public class User {
    private String userID;
    private String name;
    private String phone;
    private double balance;
    private List<Transaction> transactionHistory;

    public User(String userID, String name, String phone, double balance) {
        this.userID = userID;
        this.name = name;
        this.phone = phone;
        if (balance >= 0) {
            this.balance = balance;
        } else {
            this.balance = 0;
            System.out.println("Peringatan: Saldo awal tidak boleh negatif.");
        }
        this.transactionHistory = new ArrayList<>(); 
    }

    public String getUserID() { return userID; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public double getBalance() { return balance; }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void topUp(double amount) {
        if (amount > 0) { 
            balance += amount;
            System.out.println(name + " Berhasil top up sebesar Rp." + amount);
            transactionHistory.add(new Transaction(amount, "TOP_UP", "SUCCESS"));
        } else {
            System.out.println("Gagal: Jumlah top up harus lebih dari 0!");
            transactionHistory.add(new Transaction(amount, "TOP_UP", "FAILED"));
        }
    }

    public void pay(double amount) {
        if (amount <= 0) {
            System.out.println("Gagal: Jumlah pembayaran harus lebih dari 0!");
            transactionHistory.add(new Transaction(amount, "PAYMENT", "FAILED"));
        } else if (balance >= amount) { 
            balance -= amount;
            System.out.println(name + " Berhasil melakukan pembayaran sebesar Rp." + amount);
            transactionHistory.add(new Transaction(amount, "PAYMENT", "SUCCESS"));
        } else {
            System.out.println(name + " Saldo tidak cukup untuk membayar Rp." + amount);
            transactionHistory.add(new Transaction(amount, "PAYMENT", "FAILED"));
        }
    }

    public void showTransactionHistory() {
        System.out.println("\n--- Riwayat Transaksi: " + name + " ---");
        if (transactionHistory.isEmpty()) {
            System.out.println("Belum ada transaksi.");
        } else {
            for (Transaction t : transactionHistory) {
                t.printTransaction();
            }
        }
    }

    public void showBalance() {
        System.out.println("\n=== STATUS E-WALLET ===");
        System.out.println("ID User  : " + userID);
        System.out.println("Nama     : " + name);
        System.out.println("No. Telp : " + phone);
        System.out.println("Saldo    : Rp" + balance);
    }

    protected void addBalance(double amount) {
        this.balance += amount;
    }

    protected void addTransactionRecord(Transaction t) {
        this.transactionHistory.add(t);
    }
}