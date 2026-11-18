package questao2.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class RealEstateCommission implements CommissionStrategy {
    private static final BigDecimal RATE = new BigDecimal("0.03");
    private static final BigDecimal FIXED_FEE = new BigDecimal("1500.00");
    @Override public BigDecimal calculate(BigDecimal saleValue) {
        return saleValue.multiply(RATE).add(FIXED_FEE).setScale(2, RoundingMode.HALF_UP);
    }
    @Override public String getName() { return "Imoveis (3% + R$ 1.500,00)"; }
}
