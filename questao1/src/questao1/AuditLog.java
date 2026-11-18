package questao1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Singleton thread-safe: cada inclusao recebe sequencia dentro da mesma secao critica. */
public final class AuditLog {
    private static final AuditLog INSTANCE = new AuditLog();
    private final List<AuditRecord> records = new ArrayList<>();
    private long nextSequence = 1;

    private AuditLog() { }
    public static AuditLog getInstance() { return INSTANCE; }

    public synchronized void register(String environmentName, Provider provider, String result) {
        records.add(new AuditRecord(nextSequence++, LocalDateTime.now(), environmentName, provider, result));
    }

    public synchronized List<AuditRecord> getRecords() {
        return Collections.unmodifiableList(new ArrayList<>(records));
    }

    public synchronized void printHistory() {
        System.out.println("\n========== HISTORICO DE AUDITORIA ==========");
        if (records.isEmpty()) {
            System.out.println("Nenhum registro.");
        }
        for (AuditRecord record : records) {
            System.out.println(record);
        }
        System.out.println("Total de registros: " + records.size());
    }
}
