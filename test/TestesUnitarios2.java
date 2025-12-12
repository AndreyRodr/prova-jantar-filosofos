package test;

import tarefa2.Filosofo;
import tarefa2.Garfo;

public class TestesUnitarios2 {

    // Cores para facilitar a leitura no terminal
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {
        System.out.println("=== Iniciando Testes Unitários (Tarefa 2) ===\n");

        executarTeste("Teste Criação de Garfo", () -> testeGarfoId());
        executarTeste("Teste Criação de Filósofo", () -> testeFilosofoCriacao());
        executarTeste("Teste Inicialização da Mesa", () -> testeMesaConfiguracao());
        
        System.out.println("\n=== Fim dos Testes ===");
    }

    // Método auxiliar para rodar os testes e tratar erros
    private static void executarTeste(String nome, Runnable teste) {
        System.out.print("Executando " + nome + "... ");
        try {
            teste.run();
            System.out.println(ANSI_GREEN + "SUCESSO" + ANSI_RESET);
        } catch (AssertionError e) {
            System.out.println(ANSI_RED + "FALHA: " + e.getMessage() + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(ANSI_RED + "ERRO INESPERADO: " + e.getMessage() + ANSI_RESET);
            e.printStackTrace();
        }
    }

    // --- TESTES ---

    private static void testeGarfoId() {
        Garfo g = new Garfo(10);
        if (g.getId() != 10) {
            throw new AssertionError("ID do garfo incorreto. Esperado: 10, Atual: " + g.getId());
        }
    }

    private static void testeFilosofoCriacao() {
        Garfo esq = new Garfo(1);
        Garfo dir = new Garfo(2);
        Filosofo f = new Filosofo(5, esq, dir);

        if (f.getIdFilosofo() != 5) {
            throw new AssertionError("ID do filósofo incorreto.");
        }
        if (f.getRefeicoes() != 0) {
            throw new AssertionError("Filósofo deve começar com 0 refeições.");
        }
    }

    private static void testeMesaConfiguracao() {
        // Simula a lógica do Main para ver se os garfos vizinhos coincidem
        int quantidade = 5;
        Garfo[] garfos = new Garfo[quantidade];
        for (int i = 0; i < quantidade; i++) {
            garfos[i] = new Garfo(i);
        }

        Filosofo[] filosofos = new Filosofo[quantidade];
        for (int i = 0; i < quantidade; i++) {
            Garfo garfoEsq = garfos[i];
            Garfo garfoDir = garfos[(i + 1) % quantidade];
            filosofos[i] = new Filosofo(i, garfoEsq, garfoDir);
        }

        // Verifica se o filósofo 0 tem o garfo 0 e 1
        // Nota: Como os garfos são privados no Filosofo, testamos a consistência lógica da criação
        if (filosofos[4].getIdFilosofo() != 4) {
            throw new AssertionError("Erro na ordem dos filósofos.");
        }
        
        // Teste simples de fumaça (Smoke Test): Inicia e para rapidamente para ver se não explode
        try {
            filosofos[0].start();
            Thread.sleep(100);
            filosofos[0].interrupt();
        } catch (InterruptedException e) {
            // Ignorar
        }
    }
}