package tarefa3;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        int quantidadeFilosofos = 5;
        // SEMÁFORO: Permite apenas 4 filósofos tentando comer simultaneamente 
        Semaphore semaforo = new Semaphore(4); 
        
        Garfo[] garfos = new Garfo[quantidadeFilosofos];
        Filosofo[] filosofos = new Filosofo[quantidadeFilosofos];

        for (int i = 0; i < quantidadeFilosofos; i++) {
            garfos[i] = new Garfo(i);
        }

        for (int i = 0; i < quantidadeFilosofos; i++) {
            Garfo garfoEsq = garfos[i];
            Garfo garfoDir = garfos[(i + 1) % quantidadeFilosofos];
            
            // Passamos o semáforo para todos
            filosofos[i] = new Filosofo(i, garfoEsq, garfoDir, semaforo);
        }

        System.out.println("Iniciando Jantar (Solução com Semáforos - Tarefa 3)...");

        for (Filosofo f : filosofos) {
            f.start();
        }

        // Estatísticas
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5000);
                    System.out.println("\n--- Estatísticas (Semáforos) ---");
                    for (Filosofo f : filosofos) {
                        System.out.println("Filósofo " + f.getIdFilosofo() + " comeu " + f.getRefeicoes() + " vezes.");
                    }
                    System.out.println("--------------------------------\n");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}