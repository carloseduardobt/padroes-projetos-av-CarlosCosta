package questao1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class AuditRecord {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS");
    private final long sequence;
    private final LocalDateTime timestamp;
    private final String environmentName;
    private final Provider provider;
    private final String result;

    public AuditRecord(long sequence, LocalDateTime timestamp, String environmentName,
                       Provider provider, String result) {
        this.sequence = sequence;
        this.timestamp = timestamp;
        this.environmentName = environmentName;
        this.provider = provider;
        this.result = result;
    }

    @Override public String toString() {
        return String.format("%02d | %s | ambiente=%s | provedor=%s | %s",
            sequence, timestamp.format(FORMATTER), environmentName,
            provider.getDisplayName(), result);
    }
}
