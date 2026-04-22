import java.util.ArrayList;
import java.util.List;

public class User {
    // 1. ENCAPSULATION: Semua atribut diubah jadi private
    private String userID;
    private String name;
    private String phone;
    private double balance;
    
    // List untuk menyimpan riwayat transaksi
    private List<Transaction> transactionHistory;

    public User(String userID, String name, String phone, double balance) {
        this.userID = userID;
        this.name = name;
        this.phone = phone;
        // Validasi saldo awal tidak boleh negatif
        if (balance >= 0) {
            this.balance = balance;
        } else {
            this.balance = 0;
            System.out.println("Peringatan: Saldo awal tidak boleh negatif. Saldo diatur ke 0.");
        }
        // Inisialisasi list history kosong
        this.transactionHistory = new ArrayList<>(); 
    }

    // 2. GETTER & SETTER
    public String getName() { return name; }
    public double getBalance() { return balance; }

    // 3. METHOD TOP UP (Dengan Validasi & Riwayat)
    public void topUp(double amount) {
        if (amount > 0) { // Validasi topUp harus positif
            balance += amount;
            System.out.println(name + " Berhasil melakukan top up sebesar Rp." + amount);
            // Simpan ke riwayat
            transactionHistory.add(new Transaction(amount, "TOP_UP", "SUCCESS"));
        } else {
            System.out.println("Gagal: Jumlah top up harus lebih dari 0!");
            transactionHistory.add(new Transaction(amount, "TOP_UP", "FAILED"));
        }
    }

    // 4. METHOD PAY (Dengan Validasi & Riwayat)
    public void pay(double amount) {
        if (amount <= 0) {
            System.out.println("Gagal: Jumlah pembayaran harus lebih dari 0!");
            transactionHistory.add(new Transaction(amount, "PAYMENT", "FAILED"));
        } else if (balance >= amount) { // Validasi saldo cukup
            balance -= amount;
            System.out.println(name + " Berhasil melakukan pembayaran sebesar Rp." + amount);
            transactionHistory.add(new Transaction(amount, "PAYMENT", "SUCCESS"));
        } else {
            System.out.println(name + " Saldo tidak cukup untuk melakukan pembayaran sebesar Rp." + amount);
            transactionHistory.add(new Transaction(amount, "PAYMENT", "FAILED"));
        }
    }

    // 5. METHOD SHOW TRANSACTION HISTORY
    public void showTransactionHistory() {
        System.out.println("\n--- Riwayat Transaksi: " + name + " ---");
        if (transactionHistory.isEmpty()) {
            System.out.println("Belum ada transaksi.");
        } else {
            for (Transaction t : transactionHistory) {
                t.printTransaction();
            }
        }
        System.out.println("----------------------------------\n");
    }

    public void showBalance() {
        System.out.println("================================");
        System.out.println("        STATUS E-WALLET         ");
        System.out.println("================================");
        System.out.println("ID User  : " + userID);
        System.out.println("Nama     : " + name);
        System.out.println("No. Telp : " + phone);
        System.out.println("Saldo    : Rp" + balance);
        System.out.println("================================\n");
    }
}