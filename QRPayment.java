public class QRPayment extends Payment {
    private String qrCodeId;
    public QRPayment(double amount, String qrCodeId) {
        super(amount);
        this.qrCodeId = qrCodeId;
    }

    @Override
    public double calculateFee() {
        if (getAmount() > 100000) {
            return getAmount() * 0.007; 
        }
        return 0.0; // Gratis
    }
}