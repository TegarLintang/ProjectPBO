public abstract class Promo {
    private String promoCode;

    public Promo(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getPromoCode() {
        return promoCode;
    }

    // Abstract method untuk menghitung nilai promo
    public abstract double calculatePromoValue(double transactionAmount);
    
    // Abstract method untuk rincian promo
    public abstract String getPromoDescription();
}