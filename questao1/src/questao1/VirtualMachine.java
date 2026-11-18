package questao1;

import java.math.BigDecimal;

public interface VirtualMachine {
    Provider getProvider();
    String getId();
    BigDecimal getHourlyCost();

    default BigDecimal getMonthlyCost() {
        return getHourlyCost().multiply(BigDecimal.valueOf(730));
    }
}
