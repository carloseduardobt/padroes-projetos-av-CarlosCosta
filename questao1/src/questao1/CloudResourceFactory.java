package questao1;

import java.math.BigDecimal;

/** Abstract Factory: cria a familia completa de recursos de um provedor. */
public interface CloudResourceFactory {
    Provider getProvider();
    BigDecimal getVirtualMachineHourlyCost();
    BigDecimal getStorageCostPerGbMonthly();
    BigDecimal getQueueFixedMonthlyCost();
    VirtualMachine createVirtualMachine();
    ObjectStorage createObjectStorage(long sizeGb);
    MessageQueue createMessageQueue();
}
