public class PremiumUser extends User {
    private double transactionLimit;
    private double cashbackRate;

    public PremiumUser(String userID, String name, String phone, double balance) {
        super(userID, name, phone, balance); 
        this.transactionLimit = 10000000;    // Limit 10 Juta
        this.cashbackRate = 0.05;            
    }

    public double getTransactionLimit() { return transactionLimit; }
    public double getCashbackRate() { return cashbackRate; }

    @Override
    public void topUp(double amount) {
        if (getBalance() + amount > transactionLimit) {
            System.out.println("Gagal: Top Up ditolak! Saldo akan melebihi limit Premium User (Maks Rp10.000.000).");
            addTransactionRecord(new Transaction(amount, "TOP_UP", "FAILED"));
        } else {
            super.topUp(amount); 
        }
    }

    @Override
    public void pay(double amount) {
        if (amount > transactionLimit) {
            System.out.println("Gagal: Nominal bayar melebihi limit transaksi Premium User (Maks Rp10.000.000).");
            addTransactionRecord(new Transaction(amount, "PAYMENT", "FAILED"));
            return; 
        }

        boolean isSuccess = (amount > 0 && getBalance() >= amount);
        super.pay(amount); 
        
        if (isSuccess) {
            double cashback = amount * cashbackRate;
            addBalance(cashback); 
            addTransactionRecord(new Transaction(cashback, "CASHBACK", "SUCCESS"));
            System.out.println(">> Yeay! " + getName() + " dapat Cashback 5% sebesar Rp" + cashback);
        }
    }
}