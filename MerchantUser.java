public class MerchantUser extends User {
    private double transactionLimit;

    public MerchantUser(String userID, String name, String phone, double balance) {
        super(userID, name, phone, balance);
        this.transactionLimit = 50000000;    // Limit 50 Juta
    }

    public double getTransactionLimit() { return transactionLimit; }

    @Override
    public void topUp(double amount) {
        if (getBalance() + amount > transactionLimit) {
            System.out.println("Gagal: Top Up ditolak! Saldo akan melebihi limit Merchant User (Maks Rp50.000.000).");
            addTransactionRecord(new Transaction(amount, "TOP_UP", "FAILED"));
        } else {
            super.topUp(amount); 
        }
    }

    @Override
    public void pay(double amount) {
        if (amount > transactionLimit) {
            System.out.println("Gagal: Nominal bayar melebihi limit transaksi Merchant User (Maks Rp50.000.000).");
            addTransactionRecord(new Transaction(amount, "PAYMENT", "FAILED"));
            return; 
        }
        super.pay(amount); // Merchant tidak dapat cashback di instruksinya
    }

    public void receivePayment(double amount) {
        if (amount <= 0) {
            System.out.println("Gagal menerima pembayaran: Nominal tidak valid!");
        } else if (getBalance() + amount > transactionLimit) {
            // Validasi limit juga berlaku saat Merchant menerima dana
            System.out.println("Gagal: Dana masuk ditolak karena akan melampaui limit penyimpanan Merchant (Rp50 Juta)!");
        } else {
            addBalance(amount); 
            addTransactionRecord(new Transaction(amount, "RECEIVE", "SUCCESS"));
            System.out.println(">> " + getName() + " Berhasil menerima pembayaran sebesar Rp." + amount);
        }
    }
}