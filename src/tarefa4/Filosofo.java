package tarefa4;

import java.util.Random;

public class Filosofo extends Thread {
    private int id;
    private Mesa mesa;
    private int contaRefeicoes = 0; 
    private boolean rodando = true;
    private Random random = new Random();

    public Filosofo(int id, Mesa mesa) {
        this.id = id;
        this.mesa = mesa;
    }

    @Override
    public void run() {
        try {
            while (rodando) {
                pensar();
                
                // Logging de tentativa
                imprimirLog("está FAMINTO e a tentar pegar garfos.");
                
                // A Mesa bloqueia aqui se não for possível comer
                mesa.pegarGarfos(id);
                
                comer();
                
                mesa.soltarGarfos(id);
            }
        } catch (InterruptedException e) {
            imprimirLog("foi interrompido.");
        }
    }

    private void pensar() throws InterruptedException {
        imprimirLog("está a PENSAR.");
        Thread.sleep(random.nextInt(2000) + 1000); 
    }

    private void comer() throws InterruptedException {
        imprimirLog("pegou os garfos e está a COMER.");
        contaRefeicoes++;
        
        Thread.sleep(random.nextInt(2000) + 1000); 
        
        imprimirLog("terminou de comer e soltou os garfos.");
    }

    // Método auxiliar para formatar o log
    private void imprimirLog(String acao) {
        long tempo = System.currentTimeMillis();
        System.out.printf("[%d] Filósofo %d %s%n", tempo, id, acao);
    }
    
    // Método para parar a thread gentilmente no fim do teste
    public void parar() {
        this.rodando = false;
    }

    public int getContaRefeicoes() {
        return contaRefeicoes;
    }
    
    public int getIdentificador() {
        return id;
    }
}