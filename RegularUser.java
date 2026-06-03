public class RegularUser extends User {
    private double transactionLimit;
    private double cashbackRate;

    public RegularUser(String userID, String name, String phone, double balance) {
        super(userID, name, phone, balance); 
        this.transactionLimit = 2000000;     
        this.cashbackRate = 0.01;            
    }

    // Override 3 method abstrak dari User
    @Override
    public double getTransactionLimit() { return transactionLimit; }
    
    @Override
    public double getCashbackRate() { return cashbackRate; }
    
    @Override
    public String getAccountType() { return "Regular User"; }

    @Override
    public void topUp(double amount) {
        if (amount > transactionLimit) {
            System.out.println("Gagal: Nominal Top Up melebihi limit per transaksi Regular User.");
            addTransactionRecord(new Transaction(amount, "TOP_UP", "FAILED"));
        } else {
            super.topUp(amount); 
        }
    }

    @Override
    public void pay(double amount) {
        if (amount > transactionLimit) {
            System.out.println("Gagal: Nominal bayar melebihi limit per transaksi Regular User.");
            addTransactionRecord(new Transaction(amount, "PAYMENT", "FAILED"));
            return; 
        }

        boolean isSuccess = (amount > 0 && getBalance() >= amount);
        super.pay(amount); 
        if (isSuccess) {
            double cashback = amount * getCashbackRate(); // Menggunakan getter abstrak
            addBalance(cashback); 
            addTransactionRecord(new Transaction(cashback, "CASHBACK", "SUCCESS"));
            System.out.println(">> Yeay! " + getName() + " dapat Cashback sebesar Rp" + cashback);
        }
    }
}