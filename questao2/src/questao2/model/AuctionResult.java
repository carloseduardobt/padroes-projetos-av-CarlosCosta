package questao2.model;


import java.math.BigDecimal;

public final class AuctionResult {
    private final boolean sold;
    private final BigDecimal saleValue;
    private final Bidder winner;
    private final BigDecimal commission;

    public AuctionResult(boolean sold, BigDecimal saleValue, Bidder winner, BigDecimal commission) {
        this.sold = sold;
        this.saleValue = saleValue;
        this.winner = winner;
        this.commission = commission;
    }
    public boolean isSold() { return sold; }
    public BigDecimal getSaleValue() { return saleValue; }
    public Bidder getWinner() { return winner; }
    public BigDecimal getCommission() { return commission; }
    public BigDecimal getSellerNetValue() { return saleValue.subtract(commission); }
}
