package questao1;

import java.math.BigDecimal;

public interface MessageQueue {
    Provider getProvider();
    String getId();
    BigDecimal getFixedMonthlyCost();
}
