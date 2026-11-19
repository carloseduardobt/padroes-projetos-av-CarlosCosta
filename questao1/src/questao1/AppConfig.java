package questao1;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Singleton: carrega config.properties uma unica vez por execucao. */
public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private final String region;
    private final BigDecimal monthlyBudget;

    private AppConfig() {
        Properties properties = new Properties();
        // Permite executar tanto a partir da raiz do repositorio quanto de questao1/.
        Path configPath = Path.of("config.properties");
        if (!Files.isRegularFile(configPath)) {
            configPath = Path.of("questao1", "config.properties");
        }
        try (InputStream input = Files.newInputStream(configPath)) {
            properties.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException(
                "Nao foi possivel ler " + configPath.toAbsolutePath(), exception
            );
        }

        String configuredRegion = properties.getProperty("regiao");
        String configuredBudget = properties.getProperty("orcamento.mensal.maximo");
        if (configuredRegion == null || configuredRegion.isBlank()
                || configuredBudget == null || configuredBudget.isBlank()) {
            throw new IllegalStateException("config.properties deve definir regiao e orcamento.mensal.maximo");
        }

        this.region = configuredRegion.trim();
        this.monthlyBudget = new BigDecimal(configuredBudget.trim());
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public String getRegion() {
        return region;
    }

    public BigDecimal getMonthlyBudget() {
        return monthlyBudget;
    }
}
