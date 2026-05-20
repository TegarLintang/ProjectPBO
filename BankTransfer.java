public class BankTransfer extends Payment {
    private String bankName;
    public BankTransfer(double amount, String bankName) {
        super(amount);
        this.bankName = bankName;
    }

    @Override
    public double calculateFee() {
        return 2500.0; 
    }
}