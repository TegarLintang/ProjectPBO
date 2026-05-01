public class BankTransfer extends Payment {
    private String bankName;
    public BankTransfer(double amount, String bankName) {
        super(amount);
        this.bankName = bankName;
    }
}