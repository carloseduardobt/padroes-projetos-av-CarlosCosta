package questao2.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class TransportInsuranceDecorator extends AdditionalServiceDecorator {
    private static final BigDecimal RATE = new BigDecimal("0.015");
    public TransportInsuranceDecorator(InvoiceComponent wrapped) { super(wrapped); }
    @Override protected String serviceDescription() { return "Seguro de transporte (1,5%)"; }
    @Override protected BigDecimal serviceCost() {
        return getSaleValue().multiply(RATE).setScale(2, RoundingMode.HALF_UP);
    }
}
