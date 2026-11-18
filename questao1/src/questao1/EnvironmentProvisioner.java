package questao1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** Monta um ambiente somente por meio da Abstract Factory recebida no construtor. */
public final class EnvironmentProvisioner {
    private final CloudResourceFactory factory;

    public EnvironmentProvisioner(CloudResourceFactory factory) {
        if (factory == null) throw new IllegalArgumentException("A fabrica e obrigatoria.");
        this.factory = factory;
    }

    public Environment provision(ProvisioningRequest request) {
        AuditLog audit = AuditLog.getInstance();
        if (request.getProvider() != factory.getProvider()) {
            String reason = "RECUSADO: a fabrica nao corresponde ao provedor solicitado";
            audit.register(request.getEnvironmentName(), request.getProvider(), reason);
            System.out.println(reason + " - " + request.getEnvironmentName());
            return null;
        }

        AppConfig config = AppConfig.getInstance();
        BigDecimal estimatedCost = estimateCost(request);
        if (estimatedCost.compareTo(config.getMonthlyBudget()) > 0) {
            String reason = "RECUSADO: custo " + Money.format(estimatedCost)
                + " excede o orcamento de " + Money.format(config.getMonthlyBudget());
            audit.register(request.getEnvironmentName(), request.getProvider(), reason);
            System.out.println("\nSolicitacao recusada: " + request.getEnvironmentName());
            System.out.println(reason);
            return null;
        }

        // Recursos so sao criados depois da validacao do orcamento.
        List<VirtualMachine> machines = new ArrayList<>();
        for (int i = 0; i < request.getVirtualMachineCount(); i++) {
            machines.add(factory.createVirtualMachine());
        }
        ObjectStorage storage = factory.createObjectStorage(request.getStorageGb());
        MessageQueue queue = factory.createMessageQueue();

        Environment environment = new Environment(request.getEnvironmentName(),
            request.getProvider(), machines, storage, queue);
        audit.register(request.getEnvironmentName(), request.getProvider(),
            "ACEITO: custo mensal estimado " + Money.format(environment.getMonthlyEstimatedCost()));
        environment.printSummary();
        return environment;
    }

    private BigDecimal estimateCost(ProvisioningRequest request) {
        BigDecimal vmCost = factory.getVirtualMachineHourlyCost()
            .multiply(BigDecimal.valueOf(730))
            .multiply(BigDecimal.valueOf(request.getVirtualMachineCount()));
        BigDecimal storageCost = factory.getStorageCostPerGbMonthly()
            .multiply(BigDecimal.valueOf(request.getStorageGb()));
        return vmCost.add(storageCost).add(factory.getQueueFixedMonthlyCost());
    }
}
