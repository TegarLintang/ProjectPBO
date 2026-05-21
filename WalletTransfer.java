public class WalletTransfer extends Payment {
    private String targetPhoneNumber;
    
    public WalletTransfer(double amount, String targetPhoneNumber) {
        super(amount);
        this.targetPhoneNumber = targetPhoneNumber;
    }

    @Override
    public double calculateFee() {
        return 0.0;
    }

    @Override
    public boolean validate() {
        if (!super.validate()) return false;
        
        if (targetPhoneNumber == null || !targetPhoneNumber.startsWith("08") || targetPhoneNumber.length() < 10) {
            System.out.println("Validasi Gagal: Nomor HP tujuan tidak valid (Harus berawalan '08' dan minimal 10 digit)!");
            return false;
        }
        return true;
    }
}