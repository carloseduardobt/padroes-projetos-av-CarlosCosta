package questao2.observer;

import questao2.model.Bid;
import questao2.model.Lot;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class AuctionAuditor implements LotObserver {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final List<String> records = new ArrayList<>();

    @Override public synchronized void onBidAccepted(Lot lot, Bid bid) {
        String record = bid.getTimestamp().format(FORMATTER) + " | lote=" + lot.getName()
            + " | participante=" + bid.getBidder().getName()
            + " | valor=" + Money.format(bid.getAmount());
        records.add(record);
        System.out.println("[Auditoria] Lance registrado: " + record);
    }

    public synchronized List<String> getRecords() {
        return Collections.unmodifiableList(new ArrayList<>(records));
    }

    public synchronized void printHistory() {
        System.out.println("\n========== AUDITORIA DE LANCES ==========");
        for (String record : records) System.out.println(record);
        System.out.println("Total de lances auditados: " + records.size());
    }
}
