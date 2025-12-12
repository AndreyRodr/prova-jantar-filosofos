package tarefa1;

import java.util.Random;

public class Filosofo extends Thread {
    private final int id;
    private final Garfo garfoEsquerdo;
    private final Garfo garfoDireito;
    private final Random random = new Random();

    public Filosofo(int id, Garfo garfoEsquerdo, Garfo garfoDireito) {
        this.id = id;
        this.garfoEsquerdo = garfoEsquerdo;
        this.garfoDireito = garfoDireito;
    }

    private void log(String mensagem) {
        System.out.println(String.format("[Filósofo %d] %s - %d ms", id, mensagem, System.currentTimeMillis()));
    }

    // Método auxiliar para simular o tempo
    private void acao(String verbo) throws InterruptedException {
        // Se for aguardar, é muito rápido (0 a 10ms) para gerar concorrência logo
        // Se for comer, demora 1 segundo
        int tempo = verbo.equals("pensar") ? random.nextInt(10) : 1000; 
        Thread.sleep(tempo);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Pensar
                log("está pensando.");
                acao("pensar");

                // Tentar pegar os garfos
                log("está com fome e tentando pegar o garfo esquerdo (" + garfoEsquerdo.getId() + ").");
                
                // Bloqueia o garfo esquerdo
                synchronized (garfoEsquerdo) {
                    log("pegou o garfo esquerdo (" + garfoEsquerdo.getId() + ").");
                    
                    // FORÇAR DEADLOCK 
                    // Segura o garfo esquerdo e espera 1 segundos antes de tentar o direito.
                    // Isso garante que todos os outros filósofos também peguem os seus garfos esquerdos.
                    log("está segurando o garfo esquerdo e esperando um pouco (provocando deadlock)..."); 
                    Thread.sleep(1000); 

                    log("tentando pegar o garfo direito (" + garfoDireito.getId() + ").");

                    // Tenta bloquear o garfo direito
                    synchronized (garfoDireito) {
                        // Comer
                        log("pegou o garfo direito (" + garfoDireito.getId() + ") e COMEÇOU A COMER.");
                        acao("comer");
                        
                        // Soltar os garfos
                        log("terminou de comer e soltou os garfos.");
                    }
                }
            }
        } catch (InterruptedException e) {
            log("foi interrompido.");
            Thread.currentThread().interrupt();
        }
    }
}