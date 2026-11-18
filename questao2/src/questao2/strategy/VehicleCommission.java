package questao2.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class VehicleCommission implements CommissionStrategy {
    private static final BigDecimal RATE = new BigDecimal("0.08");
    private static final BigDecimal MAXIMUM = new BigDecimal("5000.00");
    @Override public BigDecimal calculate(BigDecimal saleValue) {
        BigDecimal calculated = saleValue.multiply(RATE).setScale(2, RoundingMode.HALF_UP);
        return calculated.min(MAXIMUM);
    }
    @Override public String getName() { return "Veiculos (8%, limitada a R$ 5.000,00)"; }
}
