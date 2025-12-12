package tarefa4;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Início da Tarefa 4: Solução com Monitores ===");
        
        // --- CONFIGURAÇÕES ---
        int numFilosofos = 5;
        long tempoExecucaoSegundos = 120;
        // ---------------------
        System.out.println("Executando jantar por " + tempoExecucaoSegundos + " segundos...");
        
        Mesa mesa = new Mesa(numFilosofos);
        Filosofo[] filosofos = new Filosofo[numFilosofos];
        
        // Inicialização e Start
        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i] = new Filosofo(i, mesa);
            filosofos[i].start();
        }


        // Thread auxiliar para mostrar estatísticas a cada 5 segundos (Opcional, mas útil)
        Thread statsThread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5000);
                    System.out.println("\n--- Status (Monitores) ---");
                    for (Filosofo f : filosofos) {
                        System.out.printf("Filósofo %d: %d refeições.%n", 
                            f.getIdentificador(), f.getContaRefeicoes());
                    }
                    System.out.println("--------------------------");
                }
            } catch (InterruptedException e) {
                // Thread interrompida, encerra silenciosamente
            }
        });
        statsThread.setDaemon(true);
        statsThread.start();

        // --- CONTROLE DE TEMPO ---
        try {
            // Converte segundos para milissegundos
            Thread.sleep(tempoExecucaoSegundos * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // --- PARAGEM CONTROLADA ---
        System.out.println("\n=== Tempo Esgotado. Terminando execução... ===");
        
        // Solicita a paragem de todos os filósofos
        for (Filosofo f : filosofos) {
            f.parar(); // Sinaliza para sair do loop while
            f.interrupt(); // Acorda a thread se ela estiver dormindo (wait/sleep)
        }
        
        // Aguarda todas as threads terminarem de fato (Join)
        for (Filosofo f : filosofos) {
            try {
                f.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // --- RESULTADO FINAL ---
        System.out.println("\n=== Estatísticas Finais (Monitores) ===");
        for (Filosofo f : filosofos) {
            System.out.printf("Filósofo %d comeu %d vezes.%n", 
                f.getIdentificador(), f.getContaRefeicoes());
        }
    }
}