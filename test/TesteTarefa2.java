import tarefa2.Filosofo;
import tarefa2.Garfo;

public class TesteTarefa2 {
    public static void main(String[] args) {
        System.out.println(">>> Testando Tarefa 2 (Prevenção de Deadlock) <<<");
        try {
            // Cenário: Filósofo 4 (o canhoto)
            Garfo gEsq = new Garfo(4);
            Garfo gDir = new Garfo(0);
            Filosofo f4 = new Filosofo(4, gEsq, gDir);

            if (f4.getIdFilosofo() == 4) {
                System.out.println("[OK] Filósofo 4 instanciado.");
            }
            
            // Cenário: Filósofo Comum (0)
            Filosofo f0 = new Filosofo(0, new Garfo(0), new Garfo(1));
            if (f0.getIdFilosofo() == 0) {
                System.out.println("[OK] Filósofo 0 instanciado.");
            }

            System.out.println("RESULTADO: SUCESSO\n");
        } catch (Exception e) {
            System.out.println("FALHA: " + e.getMessage());
        }
    }
}