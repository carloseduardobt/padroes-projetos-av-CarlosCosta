package questao1;

public final class ProvisioningRequest {
    private final String environmentName;
    private final Provider provider;
    private final int virtualMachineCount;
    private final long storageGb;

    public ProvisioningRequest(String environmentName, Provider provider,
                               int virtualMachineCount, long storageGb) {
        if (environmentName == null || environmentName.isBlank()) {
            throw new IllegalArgumentException("O nome do ambiente e obrigatorio.");
        }
        if (provider == null) {
            throw new IllegalArgumentException("O provedor e obrigatorio.");
        }
        if (virtualMachineCount < 0 || storageGb < 0) {
            throw new IllegalArgumentException("As quantidades nao podem ser negativas.");
        }
        this.environmentName = environmentName;
        this.provider = provider;
        this.virtualMachineCount = virtualMachineCount;
        this.storageGb = storageGb;
    }

    public String getEnvironmentName() { return environmentName; }
    public Provider getProvider() { return provider; }
    public int getVirtualMachineCount() { return virtualMachineCount; }
    public long getStorageGb() { return storageGb; }
}
