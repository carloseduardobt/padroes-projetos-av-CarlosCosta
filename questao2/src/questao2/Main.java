package questao2;

import questao2.invoice.AuthenticityCertificateDecorator;
import questao2.invoice.BaseInvoice;
import questao2.invoice.ExpressDeliveryDecorator;
import questao2.invoice.InvoiceComponent;
import questao2.invoice.InvoicePrinter;
import questao2.invoice.SpecialPackagingDecorator;
import questao2.invoice.TransportInsuranceDecorator;
import questao2.model.AuctionResult;
import questao2.model.Lot;
import questao2.model.SellerSettlement;
import questao2.observer.AuctionAuditor;
import questao2.observer.AuctioneerPanel;
import questao2.observer.Participant;
import questao2.strategy.ArtCommission;
import questao2.strategy.ElectronicsCommission;
import questao2.strategy.RealEstateCommission;
import questao2.strategy.VehicleCommission;
import java.math.BigDecimal;

public final class Main {
    private Main() { }

    public static void main(String[] args) {
        System.out.println("CASA DE LEILOES ONLINE - DEMONSTRACAO");

        Participant ana = new Participant("P01", "Ana");
        Participant bruno = new Participant("P02", "Bruno");
        Participant carla = new Participant("P03", "Carla");
        AuctioneerPanel panel = new AuctioneerPanel("Principal");
        AuctionAuditor auditor = new AuctionAuditor();

        Lot painting = new Lot("Quadro Aurora", "Arte", money("1000.00"),
            money("1400.00"), new ArtCommission());
        Lot apartment = new Lot("Apartamento Centro", "Imoveis", money("50000.00"),
            money("70000.00"), new RealEstateCommission());

        // Participantes, painel e auditor acompanham os dois lotes.
        ana.follow(painting);
        bruno.follow(painting);
        carla.follow(painting);
        ana.follow(apartment);
        bruno.follow(apartment);
        carla.follow(apartment);
        painting.addObserver(panel);
        painting.addObserver(auditor);
        apartment.addObserver(panel);
        apartment.addObserver(auditor);

        System.out.println("\n--- LANCES NO LOTE DE ARTE ---");
        ana.placeBid(painting, money("1100.00"));   // aceito
        bruno.placeBid(painting, money("1155.00")); // aceito: exatamente 5% acima
        carla.placeBid(painting, money("1200.00")); // recusado: abaixo de 5%

        carla.unfollow(painting);
        System.out.println("Carla deixou de acompanhar o lote '" + painting.getName() + "'.");
        carla.placeBid(painting, money("1300.00")); // aceito; Carla nao recebe sua propria notificacao

        // Campanha: troca a comissao de Arte para a estrategia de Eletronicos sem recriar o lote.
        painting.changeCommissionStrategy(new ElectronicsCommission());
        ana.placeBid(painting, money("1365.00"));   // aceito
        bruno.placeBid(painting, money("1500.00")); // aceito
        bruno.unfollow(painting);
        System.out.println("Bruno deixou de acompanhar o lote '" + painting.getName() + "'.");

        System.out.println("\n--- LANCES NO LOTE DE IMOVEIS ---");
        carla.placeBid(apartment, money("55000.00")); // aceito
        ana.placeBid(apartment, money("57750.00"));   // aceito: exatamente 5% acima

        System.out.println("\n--- ENCERRAMENTO ---");
        AuctionResult paintingResult = painting.close();
        AuctionResult apartmentResult = apartment.close();

        SellerSettlement.print(paintingResult);
        SellerSettlement.print(apartmentResult);

        if (paintingResult.isSold()) {
            InvoiceComponent invoice = new BaseInvoice(painting.getName(), paintingResult.getSaleValue());
            invoice = new TransportInsuranceDecorator(invoice);
            invoice = new AuthenticityCertificateDecorator(invoice);
            invoice = new SpecialPackagingDecorator(invoice);
            invoice = new AuthenticityCertificateDecorator(invoice); // mesmo servico duas vezes
            invoice = new ExpressDeliveryDecorator(invoice);
            InvoicePrinter.print(invoice);
        }

        auditor.printHistory();
        if (auditor.getRecords().size() != painting.getBidCount() + apartment.getBidCount()) {
            throw new IllegalStateException("A auditoria deve conter todos os lances aceitos dos dois lotes.");
        }
    }

    private static BigDecimal money(String value) { return new BigDecimal(value); }
}
