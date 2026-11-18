package questao1;

import java.math.BigDecimal;

public final class AzureObjectStorage implements ObjectStorage {
    private static final BigDecimal COST_PER_GB = new BigDecimal("0.02");
    private final String id;
    private final long sizeGb;

    public AzureObjectStorage(String id, long sizeGb) {
        this.id = id;
        this.sizeGb = sizeGb;
    }
    @Override public Provider getProvider() { return Provider.AZURE; }
    @Override public String getId() { return id; }
    @Override public long getSizeGb() { return sizeGb; }
    @Override public BigDecimal getCostPerGbMonthly() { return COST_PER_GB; }
}
