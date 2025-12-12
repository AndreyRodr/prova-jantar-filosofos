package tarefa2;
import java.util.Random;

public class Filosofo extends Thread {
    private final int id;
    private final Garfo garfoEsquerdo;
    private final Garfo garfoDireito;
    private final Random random = new Random();
    private int refeicoes = 0; // Contador de refeições 

    public Filosofo(int id, Garfo garfoEsquerdo, Garfo garfoDireito) {
        this.id = id;
        this.garfoEsquerdo = garfoEsquerdo;
        this.garfoDireito = garfoDireito;
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
                acao("pensar");

                log("está com fome.");

                Garfo primeiroGarfo;
                Garfo segundoGarfo;

                // PREVENÇÃO DE DEADLOCK
                // O Filósofo 4 pega os garfos na ordem inversa (Direita -> Esquerda) 
                // Os outros (0, 1, 2, 3) pegam na ordem padrão (Esquerda -> Direita)
                if (id == 4) {
                    primeiroGarfo = garfoDireito;
                    segundoGarfo = garfoEsquerdo;
                } else {
                    primeiroGarfo = garfoEsquerdo;
                    segundoGarfo = garfoDireito;
                }

                synchronized (primeiroGarfo) {
                    log("pegou o primeiro garfo (" + primeiroGarfo.getId() + ").");
                    
                    synchronized (segundoGarfo) {
                        // Comer
                        log("pegou o segundo garfo (" + segundoGarfo.getId() + ") e COMEÇOU A COMER.");
                        refeicoes++; // Incrementa estatística
                        acao("comer");
                        
                        // Soltar
                        log("terminou de comer. (Total: " + refeicoes + "x)");
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}