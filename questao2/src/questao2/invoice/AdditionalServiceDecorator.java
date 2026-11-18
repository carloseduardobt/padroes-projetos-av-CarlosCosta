package questao2.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Decorator abstrato: envolve outra fatura e acrescenta uma linha e um custo. */
public abstract class AdditionalServiceDecorator implements InvoiceComponent {
    private final InvoiceComponent wrapped;

    protected AdditionalServiceDecorator(InvoiceComponent wrapped) {
        if (wrapped == null) throw new IllegalArgumentException("A fatura envolvida e obrigatoria.");
        this.wrapped = wrapped;
    }

    protected abstract String serviceDescription();
    protected abstract BigDecimal serviceCost();

    @Override public String getLotName() { return wrapped.getLotName(); }
    @Override public BigDecimal getSaleValue() { return wrapped.getSaleValue(); }
    @Override public List<InvoiceLine> getLines() {
        List<InvoiceLine> lines = new ArrayList<>(wrapped.getLines());
        lines.add(new InvoiceLine(serviceDescription(), serviceCost()));
        return List.copyOf(lines);
    }
    @Override public BigDecimal getTotal() { return wrapped.getTotal().add(serviceCost()); }
}
