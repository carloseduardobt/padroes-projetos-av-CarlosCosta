package questao2.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class Bid {
    private final Bidder bidder;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;

    public Bid(Bidder bidder, BigDecimal amount, LocalDateTime timestamp) {
        this.bidder = bidder;
        this.amount = amount;
        this.timestamp = timestamp;
    }
    public Bidder getBidder() { return bidder; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
