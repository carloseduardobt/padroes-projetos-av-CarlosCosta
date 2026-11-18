package questao2.model;

import questao2.observer.Money;

public final class SellerSettlement {
    private SellerSettlement() { }

    public static void print(AuctionResult result) {
        if (!result.isSold()) {
            System.out.println("Acerto do vendedor: lote nao vendido; nenhum valor a repassar.");
            return;
        }
        System.out.println("\n========== ACERTO DO VENDEDOR ==========");
        System.out.println("Valor de venda: " + Money.format(result.getSaleValue()));
        System.out.println("Comissao cobrada: " + Money.format(result.getCommission()));
        System.out.println("Valor liquido a repassar: " + Money.format(result.getSellerNetValue()));
    }
}
