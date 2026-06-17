public abstract class Promo {
    private String promoCode;

    public Promo(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public abstract double calculatePromoValue(double transactionAmount);
    
    public abstract String getPromoDescription();
}