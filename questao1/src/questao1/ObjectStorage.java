package questao1;

import java.math.BigDecimal;

public interface ObjectStorage {
    Provider getProvider();
    String getId();
    long getSizeGb();
    BigDecimal getCostPerGbMonthly();

    default BigDecimal getMonthlyCost() {
        return getCostPerGbMonthly().multiply(BigDecimal.valueOf(getSizeGb()));
    }
}
