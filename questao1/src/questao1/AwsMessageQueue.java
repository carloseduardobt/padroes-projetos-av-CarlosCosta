package questao1;

import java.math.BigDecimal;

public final class AwsMessageQueue implements MessageQueue {
    private static final BigDecimal MONTHLY_COST = new BigDecimal("0.40");
    private final String id;

    public AwsMessageQueue(String id) { this.id = id; }
    @Override public Provider getProvider() { return Provider.AWS; }
    @Override public String getId() { return id; }
    @Override public BigDecimal getFixedMonthlyCost() { return MONTHLY_COST; }
}
