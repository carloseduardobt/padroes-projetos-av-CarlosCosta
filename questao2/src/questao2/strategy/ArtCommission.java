package questao2.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ArtCommission implements CommissionStrategy {
    private static final BigDecimal RATE = new BigDecimal("0.20");
    @Override public BigDecimal calculate(BigDecimal saleValue) {
        return saleValue.multiply(RATE).setScale(2, RoundingMode.HALF_UP);
    }
    @Override public String getName() { return "Arte (20%)"; }
}
