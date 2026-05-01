public class RegularUser extends User {
    private double transactionLimit;
    private double cashbackRate;

    public RegularUser(String userID, String name, String phone, double balance) {
        super(userID, name, phone, balance); 
        this.transactionLimit = 2000000;     // Limit 2 Juta
        this.cashbackRate = 0.01;            
    }

    public double getTransactionLimit() { return transactionLimit; }
    public double getCashbackRate() { return cashbackRate; }

    // OVERRIDE: Menambahkan validasi limit pada fitur Top Up
    @Override
    public void topUp(double amount) {
        if (getBalance() + amount > transactionLimit) {
            System.out.println("Gagal: Top Up ditolak! Saldo akan melebihi limit Regular User (Maks Rp2.000.000).");
            addTransactionRecord(new Transaction(amount, "TOP_UP", "FAILED"));
        } else {
            super.topUp(amount); // Lanjut top up kalau masih dalam batas aman
        }
    }

    // OVERRIDE: Menambahkan validasi limit pada fitur Pay
    @Override
    public void pay(double amount) {
        if (amount > transactionLimit) {
            System.out.println("Gagal: Nominal bayar melebihi limit transaksi Regular User (Maks Rp2.000.000).");
            addTransactionRecord(new Transaction(amount, "PAYMENT", "FAILED"));
            return; // Hentikan proses di sini
        }

        boolean isSuccess = (amount > 0 && getBalance() >= amount);
        super.pay(amount); 
        
        if (isSuccess) {
            double cashback = amount * cashbackRate;
            addBalance(cashback); 
            addTransactionRecord(new Transaction(cashback, "CASHBACK", "SUCCESS"));
            System.out.println(">> Yeay! " + getName() + " dapat Cashback 1% sebesar Rp" + cashback);
        }
    }
}