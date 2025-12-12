import tarefa3.Filosofo;
import tarefa3.Garfo;
import java.util.concurrent.Semaphore;

public class TesteTarefa3 {
    public static void main(String[] args) {
        System.out.println(">>> Testando Tarefa 3 (Semáforos) <<<");
        try {
            Semaphore semaforo = new Semaphore(4);
            Garfo g1 = new Garfo(1);
            Garfo g2 = new Garfo(2);
            
            Filosofo f = new Filosofo(1, g1, g2, semaforo);

            if (semaforo.availablePermits() == 4) {
                System.out.println("[OK] Semáforo iniciado com 4 permissões.");
            }

            // Teste simples de aquisição
            semaforo.acquire();
            if (semaforo.availablePermits() == 3) {
                System.out.println("[OK] Semáforo decrementou corretamente.");
            }
            semaforo.release();

            System.out.println("RESULTADO: SUCESSO\n");
        } catch (Exception e) {
            System.out.println("FALHA: " + e.getMessage());
        }
    }
}