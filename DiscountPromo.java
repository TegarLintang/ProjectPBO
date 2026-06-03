public class DiscountPromo extends Promo {
    private double discountPercentage;

    public DiscountPromo(String promoCode, double discountPercentage) {
        super(promoCode);
        this.discountPercentage = discountPercentage;
    }

    @Override
    public double calculatePromoValue(double transactionAmount) {
        return transactionAmount * discountPercentage;
    }

    @Override
    public String getPromoDescription() {
        return "Diskon Langsung sebesar " + (discountPercentage * 100) + "%";
    }
}