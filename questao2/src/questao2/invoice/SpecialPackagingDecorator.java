package questao2.invoice;

import java.math.BigDecimal;

public final class SpecialPackagingDecorator extends AdditionalServiceDecorator {
    private static final BigDecimal COST = new BigDecimal("180.00");
    public SpecialPackagingDecorator(InvoiceComponent wrapped) { super(wrapped); }
    @Override protected String serviceDescription() { return "Embalagem especial"; }
    @Override protected BigDecimal serviceCost() { return COST; }
}
