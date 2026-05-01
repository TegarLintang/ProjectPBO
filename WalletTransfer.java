public class WalletTransfer extends Payment {
    private String targetPhoneNumber;
    public WalletTransfer(double amount, String targetPhoneNumber) {
        super(amount);
        this.targetPhoneNumber = targetPhoneNumber;
    }
}