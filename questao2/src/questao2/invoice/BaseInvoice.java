package questao2.invoice;

import java.math.BigDecimal;
import java.util.List;

public final class BaseInvoice implements InvoiceComponent {
    private final String lotName;
    private final BigDecimal saleValue;

    public BaseInvoice(String lotName, BigDecimal saleValue) {
        this.lotName = lotName;
        this.saleValue = saleValue;
    }
    @Override public String getLotName() { return lotName; }
    @Override public BigDecimal getSaleValue() { return saleValue; }
    @Override public List<InvoiceLine> getLines() {
        return List.of(new InvoiceLine("Valor da venda do lote", saleValue));
    }
    @Override public BigDecimal getTotal() { return saleValue; }
}
