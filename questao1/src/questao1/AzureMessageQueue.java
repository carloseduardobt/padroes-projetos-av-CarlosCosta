package questao1;

import java.math.BigDecimal;

public final class AzureMessageQueue implements MessageQueue {
    private static final BigDecimal MONTHLY_COST = new BigDecimal("0.50");
    private final String id;

    public AzureMessageQueue(String id) { this.id = id; }
    @Override public Provider getProvider() { return Provider.AZURE; }
    @Override public String getId() { return id; }
    @Override public BigDecimal getFixedMonthlyCost() { return MONTHLY_COST; }
}
