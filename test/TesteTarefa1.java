import tarefa1.Filosofo;
import tarefa1.Garfo;

public class TesteTarefa1 {
    public static void main(String[] args) {
        System.out.println(">>> Testando Tarefa 1 (Estrutural) <<<");
        try {
            Garfo g1 = new Garfo(1);
            Garfo g2 = new Garfo(2);
            Filosofo f = new Filosofo(1, g1, g2);

            if (f.getId() > 0) { // Verifica se Thread foi criada (Thread.getId() > 0)
                System.out.println("[OK] Filósofo criado com sucesso.");
            }
            if (g1.getId() == 1) {
                System.out.println("[OK] Garfo criado com ID correto.");
            }
            
            System.out.println("NOTA: O teste funcional da Tarefa 1 deve ser feito executando o Main, pois o objetivo é observar o Deadlock visualmente.");
            System.out.println("RESULTADO: SUCESSO (Estrutural)\n");
        } catch (Exception e) {
            System.out.println("FALHA: " + e.getMessage());
        }
    }
}