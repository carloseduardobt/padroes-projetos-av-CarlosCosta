package questao1;

import java.math.BigDecimal;

public final class AwsVirtualMachine implements VirtualMachine {
    private static final BigDecimal HOURLY_COST = new BigDecimal("0.10");
    private final String id;

    public AwsVirtualMachine(String id) { this.id = id; }
    @Override public Provider getProvider() { return Provider.AWS; }
    @Override public String getId() { return id; }
    @Override public BigDecimal getHourlyCost() { return HOURLY_COST; }
}
