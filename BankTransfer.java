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

    @Override
    public boolean validate() {
        // Pengecekan nominal dipindah ke sini karena parent-nya abstract
        if (getAmount() <= 0) {
            System.out.println("Validasi Gagal: Nominal transfer harus lebih dari Rp 0.");
            return false;
        }
        if (bankName == null || bankName.trim().isEmpty()) {
            System.out.println("Validasi Gagal: Nama Bank tujuan tidak boleh kosong!");
            return false;
        }
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Bank Transfer";
    }
}