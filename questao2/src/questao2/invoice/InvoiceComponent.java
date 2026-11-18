package questao2.invoice;

import java.math.BigDecimal;
import java.util.List;

/** Componente comum da fatura base e de qualquer combinacao de servicos. */
public interface InvoiceComponent {
    String getLotName();
    BigDecimal getSaleValue();
    List<InvoiceLine> getLines();
    BigDecimal getTotal();
}
