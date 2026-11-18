package questao1;

import java.util.Objects;

/** Valor extensivel: novos provedores nao exigem alterar esta classe. */
public final class Provider {
    public static final Provider AWS = new Provider("AWS", "AWS");
    public static final Provider AZURE = new Provider("AZURE", "Azure");

    private final String name;
    private final String displayName;

    public Provider(String name, String displayName) {
        if (name == null || name.isBlank() || displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Nome e identificacao do provedor sao obrigatorios.");
        }
        this.name = name.trim().toUpperCase();
        this.displayName = displayName.trim();
    }

    public String getName() { return name; }
    public String getDisplayName() { return displayName; }

    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Provider)) return false;
        Provider provider = (Provider) other;
        return name.equals(provider.name);
    }
    @Override public int hashCode() { return Objects.hash(name); }
    @Override public String toString() { return displayName; }
}
