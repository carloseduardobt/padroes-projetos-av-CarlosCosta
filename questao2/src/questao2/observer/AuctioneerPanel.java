package questao2.observer;

import questao2.model.Bid;
import questao2.model.Lot;

public final class AuctioneerPanel implements LotObserver {
    private final String panelName;
    public AuctioneerPanel(String panelName) { this.panelName = panelName; }

    @Override public void onBidAccepted(Lot lot, Bid bid) {
        System.out.println("[Painel " + panelName + "] " + lot.getName()
            + " | maior lance: " + Money.format(lot.getCurrentBid())
            + " | total de lances: " + lot.getBidCount());
    }
}
