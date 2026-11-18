package questao2.strategy;

import java.math.BigDecimal;

/** Strategy: encapsula uma formula de comissao substituivel em tempo de execucao. */
public interface CommissionStrategy {
    BigDecimal calculate(BigDecimal saleValue);
    String getName();
}
