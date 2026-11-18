package questao1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class Main {
    private Main() { }

    public static void main(String[] args) throws Exception {
        System.out.println("PROVISIONAMENTO MULTINUVEM - DEMONSTRACAO");
        System.out.println("Regiao padrao: " + AppConfig.getInstance().getRegion());
        System.out.println("Orcamento mensal: " + Money.format(AppConfig.getInstance().getMonthlyBudget()));

        ProvisioningRequest awsApi = new ProvisioningRequest("ambiente-api", Provider.AWS, 2, 100);
        ProvisioningRequest awsWorker = new ProvisioningRequest("ambiente-worker", Provider.AWS, 1, 50);
        ProvisioningRequest azureAnalytics = new ProvisioningRequest("ambiente-analytics", Provider.AZURE, 1, 500);
        ProvisioningRequest rejected = new ProvisioningRequest("ambiente-fora-orcamento", Provider.AZURE, 8, 1000);

        ExecutorService executor = Executors.newFixedThreadPool(4);
        try {
            // Os quatro provisionamentos sao disparados em tarefas separadas;
            // os dois primeiros usam a mesma fabrica AWS compartilhada.
            Future<?> first = executor.submit(() -> provision(awsApi));
            Future<?> second = executor.submit(() -> provision(awsWorker));
            Future<?> third = executor.submit(() -> provision(azureAnalytics));
            Future<?> fourth = executor.submit(() -> provision(rejected));
            first.get();
            second.get();
            third.get();
            fourth.get();
        } finally {
            executor.shutdown();
        }

        AuditLog.getInstance().printHistory();
        if (AuditLog.getInstance().getRecords().size() != 4) {
            throw new IllegalStateException("O historico deveria conter exatamente quatro registros.");
        }
    }

    private static void provision(ProvisioningRequest request) {
        CloudResourceFactory factory = ProviderFactorySelector.select(request.getProvider());
        new EnvironmentProvisioner(factory).provision(request);
    }
}
