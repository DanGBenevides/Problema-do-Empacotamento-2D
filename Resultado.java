import java.util.List;

public class Resultado {
    int qtdCaixas;
    List<Integer> order;
    List<Caixa> caixas;

    Resultado() {
        this.qtdCaixas = 0;
        this.order = null;
        this.caixas = null;
    }

    Resultado(List<Caixa> caixas) {
        this.caixas = caixas;
        this.qtdCaixas = caixas.size();
    }

    Resultado(int qtdCaixas, List<Integer> order, List<Caixa> caixas) {
        this.qtdCaixas = qtdCaixas;
        this.order = order;
        this.caixas = caixas;
    }

    public int getqtdCaixas() {
        return this.qtdCaixas;
    }
    public void setqtdCaixas(int qtdCaixas) {
        this.qtdCaixas = qtdCaixas;
    }

    public List<Caixa> getCaixas() {
        return this.caixas;
    }
    public void setCaixas(List<Caixa> caixas) {
        this.caixas = caixas;
    }

    public List<Integer> getOrder() {
        return this.order;
    }
    public void setOrder(List<Integer> order) {
        this.order = order;
    }


    public void printAllMatriz() {
        for (Caixa caixa : this.caixas) {
            caixa.printMatriz();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }
}
