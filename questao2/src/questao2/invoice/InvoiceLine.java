package questao2.invoice;

import java.math.BigDecimal;

public final class InvoiceLine {
    private final String description;
    private final BigDecimal amount;
    public InvoiceLine(String description, BigDecimal amount) {
        this.description = description;
        this.amount = amount;
    }
    public String getDescription() { return description; }
    public BigDecimal getAmount() { return amount; }
}
