package questao1;

import java.math.BigDecimal;

public final class AwsObjectStorage implements ObjectStorage {
    private static final BigDecimal COST_PER_GB = new BigDecimal("0.023");
    private final String id;
    private final long sizeGb;

    public AwsObjectStorage(String id, long sizeGb) {
        this.id = id;
        this.sizeGb = sizeGb;
    }
    @Override public Provider getProvider() { return Provider.AWS; }
    @Override public String getId() { return id; }
    @Override public long getSizeGb() { return sizeGb; }
    @Override public BigDecimal getCostPerGbMonthly() { return COST_PER_GB; }
}
