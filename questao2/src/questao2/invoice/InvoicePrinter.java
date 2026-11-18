package questao2.invoice;

import questao2.observer.Money;

public final class InvoicePrinter {
    private InvoicePrinter() { }

    /** Nao conhece os servicos; imprime qualquer InvoiceComponent polimorficamente. */
    public static void print(InvoiceComponent invoice) {
        System.out.println("\n========== FATURA DO COMPRADOR ==========");
        System.out.println("Lote: " + invoice.getLotName());
        for (InvoiceLine line : invoice.getLines()) {
            System.out.println(line.getDescription() + ": " + Money.format(line.getAmount()));
        }
        System.out.println("TOTAL A PAGAR: " + Money.format(invoice.getTotal()));
    }
}
