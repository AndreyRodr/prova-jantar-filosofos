package tarefa4;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Início da Tarefa 4: Solução com Monitores ===");
        
        int numFilosofos = 5;
        Mesa mesa = new Mesa(numFilosofos);
        Filosofo[] filosofos = new Filosofo[numFilosofos];

        // Inicialização 
        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i] = new Filosofo(i, mesa);
            filosofos[i].start();
        }

        // Execução pelo tempo determinado
        // 120000 (2 min) ou 30000 (30s) para testes rápidos.
        long tempoExecucao = 30000; // Exemplo: 30 segundos para teste rápido
        
        try {
            Thread.sleep(tempoExecucao);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Paragem e Estatísticas
        System.out.println("\n=== Tempo Esgotado. Terminando execução... ===");
        
        for (Filosofo f : filosofos) {
            f.parar(); // Encerra o loop while
            f.interrupt(); // Interrompe se estiver no sleep/wait
        }
        
        // Aguarda threads morrerem para imprimir stats finais
        for (Filosofo f : filosofos) {
            try {
                f.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n=== Estatísticas Finais ===");
        for (Filosofo f : filosofos) {
            System.out.printf("Filósofo %d comeu %d vezes.%n", 
                f.getIdentificador(), f.getContaRefeicoes());
        }
    }
}