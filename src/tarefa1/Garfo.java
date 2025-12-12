package tarefa1;

public class Garfo { // O Garfo é o recurso compartilhado pelo qual os filósofos competem.
    private final int id;

    public Garfo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}