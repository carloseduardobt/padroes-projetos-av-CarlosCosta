package questao2.model;

import questao2.observer.LotObserver;
import questao2.observer.Money;
import questao2.strategy.CommissionStrategy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/** Subject do Observer; nao depende de classes concretas de interessados. */
public final class Lot {
    private static final BigDecimal MINIMUM_INCREMENT_RATE = new BigDecimal("1.05");

    private final String name;
    private final String category;
    private final BigDecimal startingPrice;
    private final BigDecimal reservePrice;
    private final List<LotObserver> observers = new CopyOnWriteArrayList<>();
    private final List<Bid> bids = new ArrayList<>();
    private BigDecimal currentBid;
    private Bidder highestBidder;
    private CommissionStrategy commissionStrategy;
    private boolean closed;

    public Lot(String name, String category, BigDecimal startingPrice,
               BigDecimal reservePrice, CommissionStrategy commissionStrategy) {
        if (startingPrice == null || reservePrice == null || commissionStrategy == null) {
            throw new IllegalArgumentException("Preco e estrategia sao obrigatorios.");
        }
        if (startingPrice.signum() < 0 || reservePrice.signum() < 0
                || startingPrice.scale() > 2 || reservePrice.scale() > 2) {
            throw new IllegalArgumentException("Precos devem ser nao negativos e ter no maximo duas casas decimais.");
        }
        this.name = name;
        this.category = category;
        this.startingPrice = startingPrice;
        this.reservePrice = reservePrice;
        this.currentBid = startingPrice;
        this.commissionStrategy = commissionStrategy;
    }

    public void addObserver(LotObserver observer) {
        if (observer != null) observers.add(observer);
    }
    public void removeObserver(LotObserver observer) { observers.remove(observer); }

    public synchronized boolean placeBid(Bidder participant, BigDecimal amount) {
        if (closed) {
            System.out.println("Lance recusado: o lote '" + name + "' ja esta encerrado.");
            return false;
        }
        if (participant == null || amount == null || amount.scale() > 2 || amount.signum() <= 0) {
            System.out.println("Lance recusado: participante ou valor invalido.");
            return false;
        }

        BigDecimal minimumAccepted = currentBid.multiply(MINIMUM_INCREMENT_RATE)
            .setScale(2, RoundingMode.CEILING);
        if (amount.compareTo(minimumAccepted) < 0) {
            System.out.println("Lance de " + participant.getName() + " recusado para '" + name
                + "': o minimo e " + Money.format(minimumAccepted) + ".");
            return false;
        }

        Bid bid = new Bid(participant, amount, LocalDateTime.now());
        bids.add(bid);
        currentBid = amount;
        highestBidder = participant;
        notifyObservers(bid);
        return true;
    }

    private void notifyObservers(Bid bid) {
        for (LotObserver observer : observers) {
            if (observer.shouldReceive(bid)) observer.onBidAccepted(this, bid);
        }
    }

    public synchronized void changeCommissionStrategy(CommissionStrategy newStrategy) {
        if (closed) throw new IllegalStateException("Nao e possivel alterar a comissao de um lote encerrado.");
        if (newStrategy == null) throw new IllegalArgumentException("A estrategia e obrigatoria.");
        commissionStrategy = newStrategy;
        System.out.println("[Campanha] Comissao do lote '" + name + "' alterada para: " + newStrategy.getName());
    }

    public synchronized AuctionResult close() {
        if (closed) throw new IllegalStateException("O lote ja foi encerrado.");
        closed = true;
        boolean sold = highestBidder != null && currentBid.compareTo(reservePrice) >= 0;
        if (sold) {
            BigDecimal commission = commissionStrategy.calculate(currentBid);
            System.out.println("Lote '" + name + "' VENDIDO por " + Money.format(currentBid)
                + " para " + highestBidder.getName() + ".");
            return new AuctionResult(true, currentBid, highestBidder, commission);
        }
        System.out.println("Lote '" + name + "' NAO VENDIDO (preco de reserva nao atingido).");
        return new AuctionResult(false, currentBid, null, BigDecimal.ZERO.setScale(2));
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public BigDecimal getStartingPrice() { return startingPrice; }
    public BigDecimal getReservePrice() { return reservePrice; }
    public synchronized BigDecimal getCurrentBid() { return currentBid; }
    public synchronized int getBidCount() { return bids.size(); }
    public synchronized boolean isClosed() { return closed; }
    public synchronized CommissionStrategy getCommissionStrategy() { return commissionStrategy; }
    public synchronized List<Bid> getBids() { return List.copyOf(bids); }
}
