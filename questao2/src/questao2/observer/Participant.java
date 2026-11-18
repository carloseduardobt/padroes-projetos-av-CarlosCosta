package questao2.observer;

import questao2.model.Bid;
import questao2.model.Bidder;
import questao2.model.Lot;
import java.math.BigDecimal;

public final class Participant implements LotObserver, Bidder {
    private final String id;
    private final String name;

    public Participant(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public void follow(Lot lot) { lot.addObserver(this); }
    public void unfollow(Lot lot) { lot.removeObserver(this); }
    public boolean placeBid(Lot lot, BigDecimal amount) { return lot.placeBid(this, amount); }

    @Override public boolean shouldReceive(Bid bid) {
        return !id.equals(bid.getBidder().getId());
    }
    @Override public void onBidAccepted(Lot lot, Bid bid) {
        System.out.println("[Participante " + name + "] lote '" + lot.getName()
            + "' recebeu novo lance de " + Money.format(bid.getAmount()));
    }
    public String getId() { return id; }
    public String getName() { return name; }
}
