public class MerchantUser extends User {
    private double transactionLimit;

    public MerchantUser(String userID, String name, String phone, double balance) {
        super(userID, name, phone, balance);
        this.transactionLimit = 50000000;    
    }

    public double getTransactionLimit() { return transactionLimit; }

    @Override
    public void topUp(double amount) {
        if (amount > transactionLimit) {
            System.out.println("Gagal: Nominal Top Up melebihi limit per transaksi Merchant User (Maks Rp50.000.000).");
            addTransactionRecord(new Transaction(amount, "TOP_UP", "FAILED"));
        } else {
            super.topUp(amount); 
        }
    }

    @Override
    public void pay(double amount) {
        if (amount > transactionLimit) {
            System.out.println("Gagal: Nominal bayar melebihi limit per transaksi Merchant User (Maks Rp50.000.000).");
            addTransactionRecord(new Transaction(amount, "PAYMENT", "FAILED"));
            return; 
        }
        super.pay(amount); 
    }

    public void receivePayment(double amount) {
        if (amount <= 0) {
            System.out.println("Gagal menerima pembayaran: Nominal tidak valid!");
        } else if (amount > transactionLimit) {
            System.out.println("Gagal: Dana masuk ditolak karena melebihi limit per transaksi (Maks Rp50.000.000)!");
        } else {
            addBalance(amount); 
            addTransactionRecord(new Transaction(amount, "RECEIVE", "SUCCESS"));
            System.out.println(">> " + getName() + " Berhasil menerima pembayaran sebesar Rp." + amount);
        }
    }
}