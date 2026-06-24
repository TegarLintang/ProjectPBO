import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class User {
    private String userID;
    private String name;
    private String phone;
    private double balance;
    private List<Transaction> transactionHistory;
    private String pin; // Fitur Bonus 3: Keamanan PIN

    public User(String userID, String name, String phone, double balance) {
        this.userID = userID;
        this.name = name;
        this.phone = phone;
        this.balance = (balance >= 0) ? balance : 0;
        this.transactionHistory = new ArrayList<>(); 
        this.pin = "123456"; 
    }

    // --- FITUR BONUS 4: LAPORAN KEUANGAN BULANAN ---
    public void generateMonthlyReport() {
        double totalIn = 0;
        double totalOut = 0;

        System.out.println("\n=== LAPORAN KEUANGAN BULANAN ===");
        System.out.println("Nama User : " + this.name);
        for (Transaction t : transactionHistory) {
            if (t.getStatus().equals("SUCCESS")) {
                if (t.getType().equals("TOP_UP") || t.getType().equals("RECEIVE") || t.getType().equals("CASHBACK")) {
                    totalIn += t.getAmount();
                } else if (t.getType().equals("PAYMENT")) {
                    totalOut += t.getAmount();
                }
            }
        }
        System.out.println("Total Uang Masuk  : Rp" + totalIn);
        System.out.println("Total Uang Keluar : Rp" + totalOut);
        System.out.println("Saldo Akhir Saat Ini: Rp" + this.balance);
        System.out.println("================================");
    }

    // --- FITUR BONUS 5: SAVE/LOAD DATA KE FILE (.txt) ---
    public void saveDataToFile() {
        try {
            FileWriter writer = new FileWriter(this.userID + "_data.txt");
            writer.write(this.balance + "\n");
            writer.write(this.pin + "\n");
            writer.close();
            System.out.println(">> Data berhasil disimpan ke sistem (File: " + this.userID + "_data.txt)");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan data.");
        }
    }

    public void loadDataFromFile() {
        try {
            File file = new File(this.userID + "_data.txt");
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                if (fileScanner.hasNextDouble()) {
                    this.balance = fileScanner.nextDouble();
                    fileScanner.nextLine(); 
                }
                if (fileScanner.hasNextLine()) {
                    this.pin = fileScanner.nextLine();
                }
                fileScanner.close();
                System.out.println(">> Data terdahulu berhasil dimuat!");
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat data dari file.");
        }
    }

    // (Kode verifikasi PIN, getter, setter, topUp, pay, processPayment, dsb tetap sama seperti sebelumnya)
    public boolean verifyPin(String inputPin) { return this.pin.equals(inputPin); }
    public void changePin(String oldPin, String newPin) {
        if (verifyPin(oldPin)) { this.pin = newPin; System.out.println(">> PIN diperbarui."); } 
        else { System.out.println(">> Gagal: PIN lama salah!"); }
    }
    public String getUserID() { return userID; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public double getBalance() { return balance; }

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
            System.out.println("Gagal: Jumlah pembayaran tidak valid!");
            transactionHistory.add(new Transaction(amount, "PAYMENT", "FAILED"));
        } else if (balance >= amount) { 
            balance -= amount;
            System.out.println(name + " Berhasil membayar sebesar Rp." + amount);
            transactionHistory.add(new Transaction(amount, "PAYMENT", "SUCCESS"));
        } else {
            System.out.println(name + " Saldo tidak cukup untuk membayar Rp." + amount);
            transactionHistory.add(new Transaction(amount, "PAYMENT", "FAILED"));
        }
    }

    public void showTransactionHistory() {
        System.out.println("\n--- Riwayat Transaksi: " + name + " ---");
        if (transactionHistory.isEmpty()) { System.out.println("Belum ada transaksi."); } 
        else { for (Transaction t : transactionHistory) { t.printTransaction(); } }
    }

    public void showBalance() {
        System.out.println("\n=== STATUS E-WALLET ===");
        System.out.println("ID User  : " + userID);
        System.out.println("Nama     : " + name);
        System.out.println("Tipe Akun: " + getAccountType());
        System.out.println("Saldo    : Rp" + balance);
    }

    protected void addBalance(double amount) { this.balance += amount; }
    protected void addTransactionRecord(Transaction t) { this.transactionHistory.add(t); }

    public void processPayment(Payment p) {
        if (!p.validate()) { System.out.println("Transfer dibatalkan.\n"); return; }
        double biayaAdmin = p.calculateFee(); 
        double totalPotongan = p.getAmount() + biayaAdmin;
        System.out.println("\n--- RINCIAN TRANSFER ---");
        System.out.println("Metode           : " + p.getPaymentMethod());
        System.out.println("Nominal Transfer : Rp" + p.getAmount());
        System.out.println("Biaya Admin      : Rp" + biayaAdmin);
        System.out.println("Total Potongan   : Rp" + totalPotongan);
        System.out.println("------------------------");
        this.pay(totalPotongan); 
    }

    public abstract double getTransactionLimit();
    public abstract double getCashbackRate();
    public abstract String getAccountType();
}