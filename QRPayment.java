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
        return 0.0;
    }

    @Override
    public boolean validate() {
        if (getAmount() <= 0) {
            System.out.println("Validasi Gagal: Nominal transfer harus lebih dari Rp 0.");
            return false;
        }
        if (qrCodeId == null || qrCodeId.trim().length() < 5) {
            System.out.println("Validasi Gagal: ID QR Code tidak valid (Minimal wajib 5 karakter)!");
            return false;
        }
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "QR Code Payment";
    }
}