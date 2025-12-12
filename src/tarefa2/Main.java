public class Main {
    public static void main(String[] args) {
        int quantidade = 5;
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

        System.out.println("Iniciando Jantar...");

        for (Filosofo f : filosofos) {
            f.start();
        }

        // Thread auxiliar para mostrar estatísticas a cada 5 segundos
        new Thread(() -> {
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
                e.printStackTrace();
            }
        }).start();
    }
}