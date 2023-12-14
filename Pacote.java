// Classe que representa um pacote
public class Pacote {
    private int id;
    private int x;
    private int y;
    private Boolean rotacionado;

    Pacote() {
        this.id = 0;
        this.x = 0;
        this.y = 0;
        this.rotacionado = false;
    }

    Pacote(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.rotacionado = false;
    }

    public int getArea() {
        return this.x * this.y;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public Boolean isRotacionado() {
        return this.rotacionado;
    }
    public void setRotacionado(Boolean rotacionado) {
        this.rotacionado = rotacionado;
    }
}