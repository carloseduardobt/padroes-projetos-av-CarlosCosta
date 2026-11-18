package questao2.observer;

import questao2.model.Bid;
import questao2.model.Lot;

/** Observer: implementacoes recebem eventos sem serem conhecidas pelo Lote. */
public interface LotObserver {
    void onBidAccepted(Lot lot, Bid bid);

    /** Permite a cada observador decidir se recebe o evento; o Lote nao conhece tipos concretos. */
    default boolean shouldReceive(Bid bid) { return true; }
}
