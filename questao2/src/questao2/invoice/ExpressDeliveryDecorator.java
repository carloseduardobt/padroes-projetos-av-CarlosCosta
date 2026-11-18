package questao2.invoice;

import java.math.BigDecimal;

public final class ExpressDeliveryDecorator extends AdditionalServiceDecorator {
    private static final BigDecimal COST = new BigDecimal("450.00");
    public ExpressDeliveryDecorator(InvoiceComponent wrapped) { super(wrapped); }
    @Override protected String serviceDescription() { return "Entrega expressa"; }
    @Override protected BigDecimal serviceCost() { return COST; }
}
