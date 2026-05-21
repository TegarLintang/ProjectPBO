public class Payment {
    private double amount;
    
    public Payment(double amount) { 
        this.amount = amount; 
    }
    
    public double getAmount() { 
        return amount; 
    }

    public double calculateFee() {
        return 0.0;
    }

    public boolean validate() {
        if (amount <= 0) {
            System.out.println("Validasi Gagal: Nominal transfer harus lebih dari Rp 0.");
            return false;
        }
        return true;
    }
}