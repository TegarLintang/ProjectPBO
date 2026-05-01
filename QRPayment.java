public class QRPayment extends Payment {
    private String qrCodeId;
    public QRPayment(double amount, String qrCodeId) {
        super(amount);
        this.qrCodeId = qrCodeId;
    }
}