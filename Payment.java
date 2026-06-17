public abstract class Payment {
    private double amount;

    public Payment(double amount) { 
        this.amount = amount; 
    }

    public double getAmount() { 
        return amount; 
    }

    public abstract double calculateFee();
    public abstract boolean validate();
    public abstract String getPaymentMethod();
}