import java.util.ArrayList;
import java.util.List;

public class Caixa {
    private int x;
    private int y;
    private int qtdPacotes;
    private int areaDisponivel;
    private int contador;
    private int[][] matriz;
    private Boolean podeInserir;
    private Boolean continuarInserindo;

    Caixa(int x, int y) {
        this.x = x;
        this.y = y;
        this.qtdPacotes = 0;
        this.matriz = new int[y][x];
        this.podeInserir = true;
        this.continuarInserindo = false;
        this.areaDisponivel = x * y;
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

    public Boolean getPodeInserir() {
        return this.podeInserir;
    }

    public void setPodeInserir(Boolean podeInserir) {
        this.podeInserir = podeInserir;
    }

    public Boolean getContinuarInserindo() {
        return this.continuarInserindo;
    }
    public void setContinuarInserindo(Boolean continuarInserindo) {
        this.continuarInserindo = continuarInserindo;
    }

    public int getQtdPacotes() {
        return this.qtdPacotes;
    }
    public void setQtdPacotes(int qtdPacotes) {
        this.qtdPacotes = qtdPacotes;
    }

    public int getAreaDisponivel() {
        return this.areaDisponivel;
    }
    public void subtraiAreaDisponivel(int areaInserida) {
        this.areaDisponivel -= areaInserida;
    }

    public void printContador() {
        System.out.println("Contador: " + this.contador);
    }

    public void insereUm(int id, int x, int y) {
        this.matriz[y][x] = id;
    }

    public void inserePacote(Pacote pacote, int x, int y) {
        int posX = x;
        int posY = y;
        while (posX < pacote.getX() + x) {
            while (posY > y - pacote.getY()) {
                insereUm(pacote.getId(), posX, posY);
                posY--;
            }
            posY = y;
            posX++;
        }
        this.qtdPacotes++;
        this.subtraiAreaDisponivel(pacote.getArea());
    }

    public void printMatriz() {
        for (int i = 0; i < this.y; i++) {
            System.out.print("[");
            for (int j = 0; j < this.x; j++) {
                System.out.print(" " + this.matriz[i][j] + " ");
            }
            System.out.println("]");
        }
    }

    public void printAllMatriz(List<Caixa> caixas) {
        for (Caixa caixa : caixas) {
            caixa.printMatriz();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    public void limpaMatriz() {
        for (int i = 0; i < this.y; i++) {
            for (int j = 0; j < this.x; j++) {
                this.matriz[i][j] = 0;
            }
        }
    }

    // Início Força Bruta Primeira Solução
    public Resultado forcaBruta(List<Pacote> pacotes) {
        Resultado resultado = new Resultado();
        List<Caixa> caixas = new ArrayList<Caixa>();
        Caixa caixa = new Caixa(this.x, this.y);
        caixas.add(caixa);
        this.setContinuarInserindo(false);

        List<Pacote> copiaPacotes = new ArrayList<Pacote>();
        copiaPacotes.addAll(pacotes);

        int i = 0;
        int j = 0;
        while (copiaPacotes.size() > 0) {
            this.contador += 2;
            if (this.getContinuarInserindo() == true) {
                j--;
                this.setContinuarInserindo(false);
            }

            while (caixas.get(i).getPodeInserir() == false) {
                i++;
            }
            int min = Math.min(j, pacotes.size() - 1);
            int min2 = Math.min(i, pacotes.size() - 1);

            if (insereForcaBruta(pacotes.get(min), caixas.get(min2), caixas)) {
                copiaPacotes.remove(0);
            }

            i = 0;
            j++;
        }

        resultado.setCaixas(caixas);
        resultado.setqtdCaixas(caixas.size());
        return resultado;
    }

    public Boolean insereForcaBruta(Pacote pacote, Caixa caixa, List<Caixa> todasCaixas) {
        Boolean inserido = false;
        for (int i = this.y - 1; i >= 0; i--) {
            for (int j = 0; j <= this.x - 1; j++) {
                this.contador++;
                if (caixa.possoInserir(pacote, j, i)) {
                    caixa.inserePacote(pacote, j, i);
                    i = 0;
                    j = this.x;
                    inserido = true;
                    for (Caixa box : todasCaixas) {
                        box.setPodeInserir(true);
                    }
                    return true;

                } else {
                    Pacote pacoteRotacionado = new Pacote(pacote.getId(), pacote.getY(), pacote.getX());
                    this.contador++;
                    if (caixa.possoInserir(pacoteRotacionado, j, i)) {
                        caixa.inserePacote(pacoteRotacionado, j, i);
                        i = 0;
                        j = this.x;
                        inserido = true;
                        for (Caixa box : todasCaixas) {
                            box.setPodeInserir(true);
                        }
                        return true;
                    }
                }
            }
        }
        if (inserido == false && todasCaixas.indexOf(caixa) == todasCaixas.size() - 1) {
            Caixa novaCaixa = new Caixa(this.x, this.y);
            todasCaixas.add(novaCaixa);
            this.setContinuarInserindo(true);
            caixa.setPodeInserir(false);

            this.contador++;
        } else if (inserido == false) {
            this.setContinuarInserindo(true);
            caixa.setPodeInserir(false);

            this.contador += 2;
        }
        return false;
    }

    public Boolean possoInserir(Pacote pacote, int x, int y) {
        for (int i = 0; i < pacote.getY(); i++) {
            for (int j = 0; j < pacote.getX(); j++) {
                this.contador += 2;
                if (y - i < 0 || x + j >= this.x) {
                    return false;
                }
                if (this.matriz[y - i][x + j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }
    // Fim Força Bruta Primeira Solução

    // Início Força Bruta Melhor Solução

    public Resultado bruteForce(List<Pacote> pacotes) {
        List<List<Integer>> permutations = generatePermutations(pacotes.size());

        int maxCaixas = Integer.MAX_VALUE;
        Resultado resultado = new Resultado();
        Resultado resultadoFinal = new Resultado();
        List<Pacote> todosPacotes = new ArrayList<Pacote>();

        for (List<Integer> permutation : permutations) {
            for (int i = 0; i < permutation.size(); i++) {
                todosPacotes.add(pacotes.get(permutation.get(i)));
            }

            // System.out.println("Permutacao: " + permutation);
            resultado = forcaBruta(todosPacotes);

            this.contador++;
            if (resultado.getqtdCaixas() < maxCaixas) {
                maxCaixas = resultado.getqtdCaixas();

                todosPacotes.clear();
                limpaMatriz();
                this.setQtdPacotes(0);
                resultadoFinal = resultado;
            }
            todosPacotes.clear();
            limpaMatriz();
            this.setQtdPacotes(0);
        }

        return resultadoFinal;
    }

    public List<List<Integer>> generatePermutations(int n) {
        List<List<Integer>> permutations = new ArrayList<>();
        List<Integer> original = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            original.add(i);
        }
        generatePermutationsHelper(n, original, permutations);
        return permutations;
    }

    public void generatePermutationsHelper(int n, List<Integer> original, List<List<Integer>> result) {
        this.contador++;
        if (n == 0) {
            result.add(new ArrayList<>(original));
            return;
        }

        for (int i = 0; i < n; i++) {
            swap(original, i, n - 1);
            generatePermutationsHelper(n - 1, original, result);
            swap(original, i, n - 1);
        }
    }

    static void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
    // Fim Força Bruta Melhor Solução

    // Início Guloso Ordenado
    public void quickSort(ArrayList<Pacote> pacotes, int inicio, int fim) {
        this.contador++;
        if (inicio < fim) {
            int posicaoPivo = separar(pacotes, inicio, fim);
            quickSort(pacotes, inicio, posicaoPivo - 1);
            quickSort(pacotes, posicaoPivo + 1, fim);
        }
    }

    public int separar(ArrayList<Pacote> pacotes, int inicio, int fim) {
        Pacote pivo = new Pacote(pacotes.get(inicio).getId(), pacotes.get(inicio).getX(), pacotes.get(inicio).getY());
        int i = inicio + 1, f = fim;
        while (i <= f) {
            if (pacotes.get(i).getArea() > pivo.getArea()) {
                i++;

                this.contador++;
            } else if (pivo.getArea() >= pacotes.get(f).getArea()) {
                f--;

                this.contador += 2;
            } else {
                Pacote troca = new Pacote(pacotes.get(i).getId(), pacotes.get(i).getX(), pacotes.get(i).getY());
                pacotes.get(i);
                pacotes.get(i).setId(pacotes.get(f).getId());
                pacotes.get(i).setX(pacotes.get(f).getX());
                pacotes.get(i).setY(pacotes.get(f).getY());

                pacotes.get(f).setId(troca.getId());
                pacotes.get(f).setX(troca.getX());
                pacotes.get(f).setY(troca.getY());
                i++;
                f--;
            }
        }
        pacotes.get(inicio).setId(pacotes.get(f).getId());
        pacotes.get(inicio).setX(pacotes.get(f).getX());
        pacotes.get(inicio).setY(pacotes.get(f).getY());

        pacotes.get(f).setId(pivo.getId());
        pacotes.get(f).setX(pivo.getX());
        pacotes.get(f).setY(pivo.getY());

        return f;
    }

    public Resultado gulosoOrdenado(ArrayList<Pacote> pacotes, ArrayList<Pacote> pacotesOriginais) {
        Resultado resultado = new Resultado();
        List<Caixa> caixas = new ArrayList<Caixa>();
        Caixa caixa = new Caixa(this.x, this.y);
        caixas.add(caixa);

        List<Pacote> copiaPacotes = new ArrayList<Pacote>();
        copiaPacotes.addAll(pacotes);

        int i = 0;
        int j = 0;
        while (copiaPacotes.size() > 0) {
            
            this.contador += 2;
            if (this.getContinuarInserindo() == true) {
                j--;
                this.setContinuarInserindo(false);
            }

            while (caixas.get(i).getPodeInserir() == false) {
                i++;
            }
            int min = Math.min(j, pacotes.size() - 1);
            int min2 = Math.min(i, pacotes.size() - 1);

            if (insereGuloso(pacotes.get(min), pacotesOriginais, caixas.get(min2), caixas)) {
                copiaPacotes.remove(0);
            }

            i = 0;
            j++;
        }

        resultado.setCaixas(caixas);
        resultado.setqtdCaixas(caixas.size());
        return resultado;
    }

    public Boolean insereGuloso(Pacote pacote, ArrayList<Pacote> pacotesOriginais, Caixa caixa, List<Caixa> todasCaixas) {
        Boolean inserido = false;
        int limite = caixa.getY() - 1;

        for (int i = limite; i >= 0; i--) {
            for (int j = 0; j <= caixa.getX() - 1; j++) {
                int valorAtual = caixa.matriz[i][j];
                while (valorAtual != 0) {
                    Pacote pacoteAtual = pacotesOriginais.get(valorAtual - 1);
                    int larguraAtual = 0;

                    this.contador += 2;
                    if (pacoteAtual.isRotacionado()) {
                        larguraAtual = pacoteAtual.getY();
                    } else {
                        larguraAtual = pacoteAtual.getX();
                    }

                    if (larguraAtual + j <= caixa.getX() - 1) {
                        j += larguraAtual;
                    } else {
                        limite = i - 1;
                        break;
                    }
                    valorAtual = caixa.matriz[i][j];
                }

                this.contador++;
                if (caixa.possoInserir(pacote, j, i)) {
                    caixa.inserePacote(pacote, j, i);
                    i = -1;
                    j = caixa.getX() + 1;

                    inserido = true;
                    for (Caixa box : todasCaixas) {
                        box.setPodeInserir(true);
                    }
                    return true;
                } else {
                    Pacote pacoteRotacionado = new Pacote(pacote.getId(), pacote.getY(), pacote.getX());
                    this.contador++;
                    if (caixa.possoInserir(pacoteRotacionado, j, i)) {
                        pacotesOriginais.get(pacote.getId() - 1).setRotacionado(true);
                        caixa.inserePacote(pacoteRotacionado, j, i);

                        i = -1;
                        j = caixa.getX() + 1;

                        inserido = true;
                        for (Caixa box : todasCaixas) {
                            box.setPodeInserir(true);
                        }
                        return true;
                    }
                }

            }
        }
        if (inserido == false && todasCaixas.indexOf(caixa) == todasCaixas.size() - 1) {
            Caixa novaCaixa = new Caixa(this.x, this.y);
            todasCaixas.add(novaCaixa);
            this.setContinuarInserindo(true);
            caixa.setPodeInserir(false);

            this.contador++;
        } else if (inserido == false) {
            this.setContinuarInserindo(true);
            caixa.setPodeInserir(false);

            this.contador += 2;
        }
        return false;

    }
    // Fim Guloso Ordenado

    // Início Guloso First-Fit
    public Resultado gulosoFirstFit(ArrayList<Pacote> pacotes, ArrayList<Pacote> pacotesOriginais) {
        Resultado resultado = new Resultado();
        List<Caixa> caixas = new ArrayList<Caixa>();
        Caixa caixa = new Caixa(this.x, this.y);
        caixas.add(caixa);

        List<Pacote> copiaPacotes = new ArrayList<Pacote>();
        copiaPacotes.addAll(pacotes);

        int i = 0;
        int j = 0;
        while (copiaPacotes.size() > 0) {
            this.contador++;
            if (this.getContinuarInserindo() == true) {
                j--;
                this.setContinuarInserindo(false);
            }

            while (caixas.get(i).getPodeInserir() == false) {
                i++;
            }
            int min = Math.min(j, pacotes.size() - 1);
            int min2 = Math.min(i, pacotes.size() - 1);

            this.contador++;
            if (insereGulosoFF(pacotes.get(min), pacotesOriginais, caixas.get(min2), caixas)) {
                copiaPacotes.remove(0);
            }

            i = 0;
            j++;
        }

        resultado.setCaixas(caixas);
        resultado.setqtdCaixas(caixas.size());
        return resultado;
    }

    public Boolean insereGulosoFF(Pacote pacote, ArrayList<Pacote> pacotesOriginais, Caixa caixa, List<Caixa> todasCaixas) {
        Boolean inserido = false;
        int limite = caixa.getY() - 1;

        for (int i = limite; i >= 0; i--) {
            for (int j = 0; j <= caixa.getX() - 1; j++) {
                int valorAtual = caixa.matriz[i][j];
        
                while (valorAtual != 0) {
                    Pacote pacoteAtual = pacotesOriginais.get(valorAtual - 1);
                    int larguraAtual = 0;

                    this.contador++;
                    if (pacoteAtual.isRotacionado()) {
                        larguraAtual = pacoteAtual.getY();
                    } else {
                        larguraAtual = pacoteAtual.getX();
                    }

                    if (larguraAtual + j <= caixa.getX() - 1) {
                        j += larguraAtual;
                    } else {
                        limite = i - 1;
                        break;
                    }
                    valorAtual = caixa.matriz[i][j];
                }

                this.contador++;
                if (caixa.possoInserir(pacote, j, i)) {
                    caixa.inserePacote(pacote, j, i);
                    i = -1;
                    j = caixa.getX() + 1;

                    inserido = true;
                    for (Caixa box : todasCaixas) {
                        box.setPodeInserir(true);
                    }
                    return true;
                } else {
                    Pacote pacoteRotacionado = new Pacote(pacote.getId(), pacote.getY(), pacote.getX());
                    this.contador++;
                    if (caixa.possoInserir(pacoteRotacionado, j, i)) {
                        pacotesOriginais.get(pacote.getId() - 1).setRotacionado(true);
                        caixa.inserePacote(pacoteRotacionado, j, i);

                        i = -1;
                        j = caixa.getX() + 1;

                        inserido = true;
                        for (Caixa box : todasCaixas) {
                            box.setPodeInserir(true);
                        }
                        return true;
                    }
                }

            }
        }
        if (inserido == false && todasCaixas.indexOf(caixa) == todasCaixas.size() - 1) {
            Caixa novaCaixa = new Caixa(this.x, this.y);
            todasCaixas.add(novaCaixa);
            this.setContinuarInserindo(true);
            caixa.setPodeInserir(false);

            this.contador++;
        } else if (inserido == false) {
            this.setContinuarInserindo(true);
            caixa.setPodeInserir(false);

            this.contador += 2;
        }
        return false;
    }
    // Fim Guloso First-Fit

    // Inicio Guloso Best-Fit
    public Resultado gulosoBestFit(ArrayList<Pacote> pacotes, ArrayList<Pacote> pacotesOriginais) {
        Resultado resultado = new Resultado();
        List<Caixa> caixas = new ArrayList<Caixa>();
        Caixa caixa = new Caixa(this.x, this.y);
        caixas.add(caixa);

        List<Pacote> copiaPacotes = new ArrayList<Pacote>();
        copiaPacotes.addAll(pacotes);

        int areaDisp = this.x * this.y;
        int i = 0;
        int j = 0;
        while (copiaPacotes.size() > 0) {
            this.contador++;
            if (this.getContinuarInserindo() == true) {
                j--;
                this.setContinuarInserindo(false);
            }

            areaDisp = 0;
            for (int k = 0; k < caixas.size(); k++) {
                this.contador++;
                if (caixas.get(k).getPodeInserir() == true && caixas.get(k).getAreaDisponivel() >= areaDisp) {
                    areaDisp = caixas.get(k).getAreaDisponivel();
                    i = k;
                }
            }
            
            int min = Math.min(j, pacotes.size() - 1);
            int min2 = Math.min(i, pacotes.size() - 1);

            this.contador++;
            if (insereGulosoBF(pacotes.get(min), pacotesOriginais, caixas.get(min2), caixas)) {
                copiaPacotes.remove(0);
            }

            i = 0;
            j++;
        }

        resultado.setCaixas(caixas);
        resultado.setqtdCaixas(caixas.size());
        return resultado;
    }

    public Boolean insereGulosoBF(Pacote pacote, ArrayList<Pacote> pacotesOriginais, Caixa caixa, List<Caixa> todasCaixas) {
        Boolean inserido = false;
        int limite = caixa.getY() - 1;

        for (int i = limite; i >= 0; i--) {
            for (int j = 0; j <= caixa.getX() - 1; j++) {
                int valorAtual = caixa.matriz[i][j];
                
                this.contador += 2;
                while (valorAtual != 0) {
                    Pacote pacoteAtual = pacotesOriginais.get(valorAtual - 1);
                    int larguraAtual = 0;

                    if (pacoteAtual.isRotacionado()) {
                        larguraAtual = pacoteAtual.getY();
                    } else {
                        larguraAtual = pacoteAtual.getX();
                    }

                    if (larguraAtual + j <= caixa.getX() - 1) {
                        j += larguraAtual;
                    } else {
                        limite = i - 1;
                        break;
                    }
                    valorAtual = caixa.matriz[i][j];
                }

                this.contador++;
                if (caixa.possoInserir(pacote, j, i)) {
                    caixa.inserePacote(pacote, j, i);
                    i = -1;
                    j = caixa.getX() + 1;

                    inserido = true;
                    for (Caixa box : todasCaixas) {
                        box.setPodeInserir(true);
                    }
                    return true;
                } else {
                    Pacote pacoteRotacionado = new Pacote(pacote.getId(), pacote.getY(), pacote.getX());

                    this.contador++;
                    if (caixa.possoInserir(pacoteRotacionado, j, i)) {
                        pacotesOriginais.get(pacote.getId() - 1).setRotacionado(true);
                        caixa.inserePacote(pacoteRotacionado, j, i);

                        i = -1;
                        j = caixa.getX() + 1;

                        inserido = true;
                        for (Caixa box : todasCaixas) {
                            box.setPodeInserir(true);
                        }
                        return true;
                    }
                }

            }
        }    
        if (inserido == false && todasCaixas.indexOf(caixa) == todasCaixas.size() - 1) {
            Caixa novaCaixa = new Caixa(this.x, this.y);
            todasCaixas.add(novaCaixa);
            this.setContinuarInserindo(true);
            caixa.setPodeInserir(false);

            this.contador++;
        } else if (inserido == false) {
            this.setContinuarInserindo(true);
            caixa.setPodeInserir(false);

            this.contador += 2;
        }
        return false;
    }
}
