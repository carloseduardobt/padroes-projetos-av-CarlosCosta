package questao1;

import java.math.BigDecimal;

public final class AzureVirtualMachine implements VirtualMachine {
    private static final BigDecimal HOURLY_COST = new BigDecimal("0.12");
    private final String id;

    public AzureVirtualMachine(String id) { this.id = id; }
    @Override public Provider getProvider() { return Provider.AZURE; }
    @Override public String getId() { return id; }
    @Override public BigDecimal getHourlyCost() { return HOURLY_COST; }
}
