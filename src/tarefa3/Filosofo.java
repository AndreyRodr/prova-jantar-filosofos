package tarefa3;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Filosofo extends Thread {
    private final int id;
    private final Garfo garfoEsquerdo;
    private final Garfo garfoDireito;
    private final Semaphore semaforoMesa; // O "porteiro" da mesa
    private final Random random = new Random();
    private int refeicoes = 0;

    public Filosofo(int id, Garfo garfoEsquerdo, Garfo garfoDireito, Semaphore semaforoMesa) {
        this.id = id;
        this.garfoEsquerdo = garfoEsquerdo;
        this.garfoDireito = garfoDireito;
        this.semaforoMesa = semaforoMesa;
    }

    public int getRefeicoes() {
        return refeicoes;
    }
    
    public int getIdFilosofo() {
        return id;
    }

    private void log(String mensagem) {
        System.out.println(String.format("[Filósofo %d] %s", id, mensagem));
    }

    private void acao(String verbo) throws InterruptedException {
        int tempo = 1000 + random.nextInt(2000); 
        Thread.sleep(tempo);
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Pensar
                acao("aguardar");
                log("está com fome.");

                // Tentar entrar na mesa (Semáforo)
                // Se já houver 4 pessoas comendo, ele espera aqui.
                semaforoMesa.acquire(); 
                
                try {
                    // Agora que tem permissão para tentar comer, pega os garfos
                    // aqui podemos usar a ordem padrão (Esq -> Dir) sem medo de deadlock
                    // pq garantimos que no máximo 4 tentam fazer isso.
                    synchronized (garfoEsquerdo) {
                        log("pegou o garfo esquerdo (" + garfoEsquerdo.getId() + ").");
                        
                        synchronized (garfoDireito) {
                            // Comer
                            log("pegou o garfo direito (" + garfoDireito.getId() + ") e COMEÇOU A COMER.");
                            refeicoes++;
                            acao("comer");
                            
                            log("terminou de comer. (Total: " + refeicoes + "x)");
                        }
                    }
                } finally {
                    // Sair da mesa (Libera a permissão para outro)
                    semaforoMesa.release();
                    log("saiu da mesa (liberou semáforo).");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}