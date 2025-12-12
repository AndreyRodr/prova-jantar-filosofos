package tarefa2;
public class Main {
    public static void main(String[] args) {
        int quantidade = 5;
        long tempoExecucaoSegundos = 120;

        Garfo[] garfos = new Garfo[quantidade];
        Filosofo[] filosofos = new Filosofo[quantidade];

        // Cria garfos
        for (int i = 0; i < quantidade; i++) {
            garfos[i] = new Garfo(i);
        }

        // Cria filósofos
        for (int i = 0; i < quantidade; i++) {
            Garfo garfoEsq = garfos[i];
            Garfo garfoDir = garfos[(i + 1) % quantidade];
            filosofos[i] = new Filosofo(i, garfoEsq, garfoDir);
        }

        System.out.println("Iniciando Jantar por " + tempoExecucaoSegundos + " segundos...");

        for (Filosofo f : filosofos) {
            f.start();
        }

        // Thread auxiliar para mostrar estatísticas a cada 5 segundos
        Thread statsThread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5000);
                    System.out.println("\n--- Estatísticas (últimos 5s) ---");
                    for (Filosofo f : filosofos) {
                        System.out.println("Filósofo " + f.getIdFilosofo() + " comeu " + f.getRefeicoes() + " vezes.");
                    }
                    System.out.println("---------------------------------\n");
                }
            } catch (InterruptedException e) {
                // Thread interrompida, sai do loop
            }
        });
        
        // Define como Daemon: se o main acabar, esta thread morre junto automaticamente
        statsThread.setDaemon(true);
        statsThread.start();

        // --- CONTROLE DE TEMPO ---
        try {
            // A thread main dorme pelo tempo definido, deixando a simulação rodar
            Thread.sleep(tempoExecucaoSegundos * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // --- ENCERRAMENTO ---
        System.out.println("\nTempo de execução esgotado (" + tempoExecucaoSegundos + "s).");
        System.out.println("Encerrando o jantar...");
        
        // Imprime o estado final antes de fechar
        System.out.println("\n--- RESULTADO FINAL ---");
        for (Filosofo f : filosofos) {
            System.out.println("Filósofo " + f.getIdFilosofo() + " terminou com " + f.getRefeicoes() + " refeições.");
        }
        
        // Força o encerramento da JVM e de todas as threads dos filósofos
        System.exit(0);
    }
}