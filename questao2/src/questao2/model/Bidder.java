package questao2.model;

/** Papel minimo de quem pode ofertar um lance; desacopla Lot de Participant. */
public interface Bidder {
    String getId();
    String getName();
}
