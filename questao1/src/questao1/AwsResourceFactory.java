package questao1;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/** Fabrica concreta AWS; os contadores atomicos evitam IDs repetidos em threads. */
public final class AwsResourceFactory implements CloudResourceFactory {
    private final AtomicInteger vmSequence = new AtomicInteger();
    private final AtomicInteger storageSequence = new AtomicInteger();
    private final AtomicInteger queueSequence = new AtomicInteger();

    @Override public Provider getProvider() { return Provider.AWS; }
    @Override public BigDecimal getVirtualMachineHourlyCost() { return new BigDecimal("0.10"); }
    @Override public BigDecimal getStorageCostPerGbMonthly() { return new BigDecimal("0.023"); }
    @Override public BigDecimal getQueueFixedMonthlyCost() { return new BigDecimal("0.40"); }

    @Override public VirtualMachine createVirtualMachine() {
        return new AwsVirtualMachine("i-" + vmSequence.incrementAndGet());
    }
    @Override public ObjectStorage createObjectStorage(long sizeGb) {
        return new AwsObjectStorage("s3://" + storageSequence.incrementAndGet(), sizeGb);
    }
    @Override public MessageQueue createMessageQueue() {
        return new AwsMessageQueue("sqs-" + queueSequence.incrementAndGet());
    }
}
