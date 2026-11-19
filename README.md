# Avaliacao Semestral - Padroes de Projetos

Implementacao em Java usando somente a biblioteca padrao, organizada conforme o enunciado da Universidade Positivo.

## Requisitos

- JDK 17 ou superior (nao ha dependencias externas).
- Para os diagramas, os arquivos PlantUML estao em `questao1/diagrama` e `questao2/diagrama`.

## Questao 1 - Provisionamento Multinuvem

**Padroes aplicados**

- **Abstract Factory:** `CloudResourceFactory` define a familia de recursos (maquina virtual, armazenamento e fila). `AwsResourceFactory` e `AzureResourceFactory` criam familias coerentes, impedindo mistura de provedores no montador.
- **Singleton - configuracao:** `AppConfig` carrega `config.properties` uma unica vez. O carregador procura o arquivo na pasta atual e, se a execucao partir da raiz do repositorio, em `questao1/config.properties`.
- **Singleton thread-safe - auditoria:** `AuditLog` e unico e sincroniza a inclusao/copia do historico. Cada registro recebe sequencia durante a mesma secao critica, sem sobrescrita entre threads.
- **Selecao de provedor:** `ProviderFactorySelector` e o unico componente que conhece as fabricas concretas; `Provider` e um objeto de valor extensivel, nao um enum que precisaria ser alterado para cadastrar outro nome de provedor. `EnvironmentProvisioner` recebe uma `CloudResourceFactory` e nao usa `if`/`switch` para decidir provedor. As interfaces dos tres produtos identificam o provedor, e `Environment` rejeita explicitamente uma familia misturada. A regiao e consultada diretamente por `Environment` no Singleton de configuracao.

Os custos sao calculados antes de criar recursos. Uma solicitacao acima do limite e recusada e auditada sem instanciar recursos. A `Main` dispara quatro solicitacoes em um pool de threads, incluindo duas solicitacoes AWS concorrentes.

### Executar

Abra a raiz do repositorio no VS Code. O arquivo `.vscode/settings.json` ja configura `questao1/src` e `questao2/src` como pastas de codigo-fonte; o `.vscode/launch.json` disponibiliza as duas configuracoes de execucao.

No Windows (PowerShell), a partir da raiz do repositorio:

```powershell
New-Item -ItemType Directory -Force .\out\q1 | Out-Null
$files = Get-ChildItem -Path .\questao1\src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d .\out\q1 $files
java -cp .\out\q1 questao1.Main
```

No Linux/macOS, a partir da raiz do repositorio:

```bash
mkdir -p out/q1
javac -encoding UTF-8 -d out/q1 $(find questao1/src -name '*.java')
java -cp out/q1 questao1.Main
```

O arquivo `questao1/config.properties` define a regiao e o orcamento. A leitura ocorre uma unica vez por execucao.

## Questao 2 - Casa de Leiloes Online

**Padroes aplicados**

- **Observer:** `Lot` e o Subject e conhece somente `LotObserver` e a abstracao `Bidder` (nao a classe concreta `Participant`). Participantes, painel e auditoria sao observadores independentes. `Participant.shouldReceive` evita que o autor receba a notificacao do proprio lance.
- **Strategy:** `CommissionStrategy` encapsula cada regra de comissao. A estrategia de um lote pode ser substituida antes do encerramento, sem recriar o lote e sem decisao por categoria via `if`/`switch`.
- **Decorator:** `InvoiceComponent` e envolvido por decorators de servico. Qualquer servico pode ser repetido e combinado em qualquer ordem. `InvoicePrinter` trata uma fatura simples ou decorada da mesma forma.

As tres funcionalidades ficam em pacotes separados (`observer`, `strategy` e `invoice`), atendendo a independencia exigida pelo RNF05.

### Executar

No Windows (PowerShell), a partir da raiz do repositorio:

```powershell
New-Item -ItemType Directory -Force .\out\q2 | Out-Null
$files = Get-ChildItem -Path .\questao2\src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d .\out\q2 $files
java -cp .\out\q2 questao2.Main
```

No Linux/macOS, a partir da raiz do repositorio:

```bash
mkdir -p out/q2
javac -encoding UTF-8 -d out/q2 $(find questao2/src -name '*.java')
java -cp out/q2 questao2.Main
```

## Estrutura

```text
padroes-projetos-av-estudante/
├── questao1/
│   ├── config.properties
│   ├── diagrama/questao1.puml
│   └── src/questao1/*.java
└── questao2/
    ├── diagrama/questao2.puml
    └── src/questao2/{model,observer,strategy,invoice}/*.java
```

## Antes de entregar

1. Renomeie o repositorio para `padroes-projetos-av-seu-nome`, conforme o padrao do enunciado.
2. Confira os codigos e execute as duas `Main` na sua maquina.
3. Crie o repositorio publico no GitHub ou GitLab e envie os commits locais (`git push`). O professor considera somente commits enviados ao repositorio remoto.

Os commits deste projeto usam os prefixos solicitados: `q1:` e `q2:`.
