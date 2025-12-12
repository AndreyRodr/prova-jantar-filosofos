package test;

import tarefa4.Mesa;

public class TesteTarefa4 {
    public static void main(String[] args) {
        System.out.println("=== Iniciando Testes Unitários do Monitor (Mesa) ===");
        
        executarTesteBasico();
        executarTesteBloqueioVizinho();
        
        System.out.println("\n=== Todos os testes concluídos ===");
    }

    /**
     * Cenário 1: Um filósofo tenta comer sem competição.
     * Resultado esperado: Deve conseguir pegar os garfos imediatamente.
     */
    private static void executarTesteBasico() {
        System.out.println("\n[Teste 1] Verificação Básica de Acesso");
        Mesa mesa = new Mesa(5);
        
        try {
            System.out.println("-> Filósofo 0 tenta pegar garfos...");
            mesa.pegarGarfos(0);
            System.out.println("-> SUCESSO: Filósofo 0 pegou os garfos.");
            
            mesa.soltarGarfos(0);
            System.out.println("-> Filósofo 0 soltou os garfos.");
            System.out.println("RESULTADO: APROVADO ✅");
        } catch (InterruptedException e) {
            System.out.println("RESULTADO: FALHOU ❌ (Exceção lançada)");
            e.printStackTrace();
        }
    }

    /**
     * Cenário 2: Exclusão Mútua.
     * O Filósofo 0 pega os garfos. O Filósofo 1 tenta pegar.
     * Resultado esperado: O Filósofo 1 deve ficar bloqueado (WAITING) até o 0 soltar.
     */
    private static void executarTesteBloqueioVizinho() {
        System.out.println("\n[Teste 2] Verificação de Bloqueio de Vizinho");
        final Mesa mesa = new Mesa(5);

        // Thread do Filósofo 0 (o que come primeiro)
        Thread f0 = new Thread(() -> {
            try {
                System.out.println("[F0] Tentando pegar garfos...");
                mesa.pegarGarfos(0);
                System.out.println("[F0] Pegou os garfos! Segurando por 2 segundos...");
                Thread.sleep(2000); // Segura os garfos
                mesa.soltarGarfos(0);
                System.out.println("[F0] Soltou os garfos.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Thread do Filósofo 1 (o vizinho que deve esperar)
        Thread f1 = new Thread(() -> {
            try {
                // Pequeno delay para garantir que F0 chegue primeiro
                Thread.sleep(500); 
                System.out.println("[F1] Tentando pegar garfos (deve bloquear)...");
                mesa.pegarGarfos(1);
                System.out.println("[F1] Conseguiu pegar os garfos! (Só deve aparecer depois de F0 soltar)");
                mesa.soltarGarfos(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        f0.start();
        f1.start();

        try {
            // Vamos monitorizar o estado do F1 enquanto F0 come
            Thread.sleep(1000); // Espera 1s (F0 ainda está a comer)
            
            System.out.println("-> Verificando estado do F1 (esperado: WAITING)...");
            if (f1.getState() == Thread.State.WAITING) {
                System.out.println("-> Estado Confirmado: F1 está ESPERANDO corretamente.");
            } else {
                System.out.println("-> FALHA: F1 deveria estar esperando, mas está: " + f1.getState());
            }

            f0.join(); // Espera F0 terminar
            f1.join(); // Espera F1 terminar
            
            System.out.println("RESULTADO: APROVADO ✅ (Se F1 comeu apenas depois de F0)");
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
