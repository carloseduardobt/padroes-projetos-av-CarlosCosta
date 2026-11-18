package questao1;

import java.util.HashMap;
import java.util.Map;

/** Ponto autorizado a escolher e instanciar as fabricas concretas de provedor. */
public final class ProviderFactorySelector {
    private static final Map<String, CloudResourceFactory> FACTORIES = new HashMap<>();

    private ProviderFactorySelector() { }

    public static synchronized CloudResourceFactory select(Provider provider) {
        if (provider == null) {
            throw new IllegalArgumentException("Provedor nao informado.");
        }
        CloudResourceFactory existing = FACTORIES.get(provider.getName());
        if (existing != null) return existing;

        // Ao incluir um provedor, a escolha e registrada somente neste componente.
        CloudResourceFactory created;
        switch (provider.getName()) {
            case "AWS": created = new AwsResourceFactory(); break;
            case "AZURE": created = new AzureResourceFactory(); break;
            default: throw new IllegalArgumentException("Provedor sem fabrica: " + provider.getDisplayName());
        }
        FACTORIES.put(provider.getName(), created);
        return created;
    }
}
