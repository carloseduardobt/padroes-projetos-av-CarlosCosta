package questao1;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/** Fabrica concreta Azure; os contadores atomicos evitam IDs repetidos em threads. */
public final class AzureResourceFactory implements CloudResourceFactory {
    private final AtomicInteger vmSequence = new AtomicInteger();
    private final AtomicInteger storageSequence = new AtomicInteger();
    private final AtomicInteger queueSequence = new AtomicInteger();

    @Override public Provider getProvider() { return Provider.AZURE; }
    @Override public BigDecimal getVirtualMachineHourlyCost() { return new BigDecimal("0.12"); }
    @Override public BigDecimal getStorageCostPerGbMonthly() { return new BigDecimal("0.02"); }
    @Override public BigDecimal getQueueFixedMonthlyCost() { return new BigDecimal("0.50"); }

    @Override public VirtualMachine createVirtualMachine() {
        return new AzureVirtualMachine("vm-" + vmSequence.incrementAndGet());
    }
    @Override public ObjectStorage createObjectStorage(long sizeGb) {
        return new AzureObjectStorage("blob://" + storageSequence.incrementAndGet(), sizeGb);
    }
    @Override public MessageQueue createMessageQueue() {
        return new AzureMessageQueue("sb-" + queueSequence.incrementAndGet());
    }
}
