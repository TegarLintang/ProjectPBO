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
        if (!super.validate()) return false; 
        
        if (bankName == null || bankName.trim().isEmpty()) {
            System.out.println("Validasi Gagal: Nama Bank tujuan tidak boleh kosong!");
            return false;
        }
        return true;
    }
}