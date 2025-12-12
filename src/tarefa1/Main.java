package tarefa1;

public class Main {
    public static void main(String[] args) {
        int quantidade = 5;
        Garfo[] garfos = new Garfo[quantidade];
        Filosofo[] filosofos = new Filosofo[quantidade];

        // Criação dos garfos 
        for (int i = 0; i < quantidade; i++) {
            garfos[i] = new Garfo(i);
        }

        // Criação dos filósofos e atribuição dos garfos
        // Lógica: Filósofo i pega garfo i (esquerda) e garfo (i+1)%5 (direita)
        for (int i = 0; i < quantidade; i++) {
            Garfo garfoEsq = garfos[i];
            Garfo garfoDir = garfos[(i + 1) % quantidade];

            filosofos[i] = new Filosofo(i, garfoEsq, garfoDir);
        }

        System.out.println("Iniciando Jantar dos Filósofos (Com Deadlock)...");
        
        // Iniciar as threads
        for (Filosofo f : filosofos) {
            f.start();
        }
    }
}