package questao1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Environment {
    private final String name;
    private final Provider provider;
    private final List<VirtualMachine> virtualMachines;
    private final ObjectStorage storage;
    private final MessageQueue queue;

    public Environment(String name, Provider provider,
                       List<VirtualMachine> virtualMachines,
                       ObjectStorage storage, MessageQueue queue) {
        if (provider == null || storage == null || queue == null || virtualMachines == null) {
            throw new IllegalArgumentException("Provedor e recursos sao obrigatorios.");
        }
        if (!provider.equals(storage.getProvider()) || !provider.equals(queue.getProvider())
                || virtualMachines.stream().anyMatch(vm -> !provider.equals(vm.getProvider()))) {
            throw new IllegalArgumentException("Um ambiente nao pode misturar recursos de provedores diferentes.");
        }
        this.name = name;
        this.provider = provider;
        this.virtualMachines = Collections.unmodifiableList(new ArrayList<>(virtualMachines));
        this.storage = storage;
        this.queue = queue;
    }

    public BigDecimal getMonthlyEstimatedCost() {
        BigDecimal vmTotal = virtualMachines.stream()
            .map(VirtualMachine::getMonthlyCost)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return vmTotal.add(storage.getMonthlyCost()).add(queue.getFixedMonthlyCost());
    }

    public void printSummary() {
        synchronized (System.out) {
            System.out.println("\n--- AMBIENTE PROVISIONADO ---");
            System.out.println("Nome: " + name);
            System.out.println("Provedor: " + provider.getDisplayName());
            System.out.println("Regiao: " + AppConfig.getInstance().getRegion());
            System.out.println("Maquinas virtuais:");
            if (virtualMachines.isEmpty()) {
                System.out.println("  (nenhuma)");
            }
            for (VirtualMachine vm : virtualMachines) {
                System.out.println("  " + vm.getId());
            }
            System.out.println("Armazenamento: " + storage.getId() + " (" + storage.getSizeGb() + " GB)");
            System.out.println("Fila: " + queue.getId());
            System.out.println("Custo mensal estimado: " + Money.format(getMonthlyEstimatedCost()));
        }
    }

    public String getName() { return name; }
    public Provider getProvider() { return provider; }
}
