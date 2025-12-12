package tarefa4;

public class Mesa {
    private static final int PENSANDO = 0;
    private static final int FAMINTO = 1;
    private static final int COMENDO = 2;

    private int[] estados;
    private int quantidadeFilosofos;

    // Construtor
    public Mesa(int quantidadeFilosofos) {
        this.quantidadeFilosofos = quantidadeFilosofos;
        this.estados = new int[quantidadeFilosofos];
        
        // Todos começam pensando
        for (int i = 0; i < quantidadeFilosofos; i++) {
            this.estados[i] = PENSANDO;
        }
    }

    /**
     * Método sincronizado para tentar pegar os garfos.
     * se não conseguir, a thread entra em estado de espera (wait).
     */
    public synchronized void pegarGarfos(int idFilosofo) throws InterruptedException {
        // Indica intenção de comer
        estados[idFilosofo] = FAMINTO;
        
        // Tenta adquirir os garfos (verifica vizinhos)
        testar(idFilosofo);

        // Enquanto não conseguir comer, espera.
        while (estados[idFilosofo] != COMENDO) {
            wait(); // Liberta o lock e dorme até ser notificado
        }
    }

    /**
     * Método sincronizado para soltar os garfos.
     */
    public synchronized void soltarGarfos(int idFilosofo) {
        // Muda estado para pensando
        estados[idFilosofo] = PENSANDO;

        // ao terminar, verifica se os vizinhos (que podem estar bloqueados)
        // agora podem comer. Isso previne starvation.
        int esquerda = (idFilosofo + quantidadeFilosofos - 1) % quantidadeFilosofos;
        int direita = (idFilosofo + 1) % quantidadeFilosofos;

        testar(esquerda);
        testar(direita);
    }

    /**
     * Verifica se o filósofo pode comer.
     * Para comer, ele deve estar FAMINTO e os vizinhos NÃO podem estar COMENDO.
     */
    private void testar(int idFilosofo) {
        int esquerda = (idFilosofo + quantidadeFilosofos - 1) % quantidadeFilosofos;
        int direita = (idFilosofo + 1) % quantidadeFilosofos;

        if (estados[idFilosofo] == FAMINTO && 
            estados[esquerda] != COMENDO && 
            estados[direita] != COMENDO) {
            
            estados[idFilosofo] = COMENDO;
            
            // Avisa todas as threads que o estado mudou. 
            // Aquelas que estavam no wait() acordam e verificam o while novamente.
            notifyAll(); 
        }
    }
}