package questao2.invoice;

import java.math.BigDecimal;

public final class AuthenticityCertificateDecorator extends AdditionalServiceDecorator {
    private static final BigDecimal COST = new BigDecimal("300.00");
    public AuthenticityCertificateDecorator(InvoiceComponent wrapped) { super(wrapped); }
    @Override protected String serviceDescription() { return "Certificado de autenticidade"; }
    @Override protected BigDecimal serviceCost() { return COST; }
}
