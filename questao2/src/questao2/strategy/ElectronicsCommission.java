package questao2.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class ElectronicsCommission implements CommissionStrategy {
    private static final BigDecimal RATE = new BigDecimal("0.12");
    private static final BigDecimal MINIMUM = new BigDecimal("50.00");
    @Override public BigDecimal calculate(BigDecimal saleValue) {
        BigDecimal calculated = saleValue.multiply(RATE).setScale(2, RoundingMode.HALF_UP);
        return calculated.max(MINIMUM);
    }
    @Override public String getName() { return "Eletronicos (12%, minimo de R$ 50,00)"; }
}
