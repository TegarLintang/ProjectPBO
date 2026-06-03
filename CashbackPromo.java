public class CashbackPromo extends Promo {
    private double cashbackFixAmount;

    public CashbackPromo(String promoCode, double cashbackFixAmount) {
        super(promoCode);
        this.cashbackFixAmount = cashbackFixAmount;
    }

    @Override
    public double calculatePromoValue(double transactionAmount) {
        if (transactionAmount >= 50000) {
            return cashbackFixAmount;
        }
        return 0; 
    }

    @Override
    public String getPromoDescription() {
        return "Cashback Tunai Sebesar Rp" + cashbackFixAmount;
    }
}