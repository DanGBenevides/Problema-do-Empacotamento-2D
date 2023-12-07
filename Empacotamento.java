import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Empacotamento {
    public static void main(String[] args) throws Exception {
        FileReader arq = new FileReader("Testes/teste10.txt");
        BufferedReader lerArq = new BufferedReader(arq);

        String linha = lerArq.readLine();
        String[] dadosCaixa = linha.split(" ");
        int x = Integer.parseInt(dadosCaixa[0]);
        int y = Integer.parseInt(dadosCaixa[1]);
        long tempoInicial;
        long tempoFinal;
        
        ArrayList<Pacote> pacotes = new ArrayList<Pacote>();
        ArrayList<Pacote> pacotesOriginais = new ArrayList<Pacote>();
        
        while((linha = lerArq.readLine()) != null) {
            String[] dados = linha.split(" ");
            Pacote pacote = new Pacote(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]), Integer.parseInt(dados[2]));
            Pacote pacoteOriginal = new Pacote(Integer.parseInt(dados[0]), Integer.parseInt(dados[1]), Integer.parseInt(dados[2]));
            pacotes.add(pacote);
            pacotesOriginais.add(pacoteOriginal);
        }
        arq.close();

        System.out.println("Guloso ordenado: ");
        Caixa caixaGuloso = new Caixa(x, y);
        caixaGuloso.quickSort(pacotes, 0, pacotes.size() - 1);
        Resultado respGulosoOrdenado = new Resultado();

        tempoInicial = System.nanoTime();
        respGulosoOrdenado = caixaGuloso.gulosoOrdenado(pacotes, pacotesOriginais);
        tempoFinal = System.nanoTime();

        respGulosoOrdenado.printAllMatriz();
        System.out.println("Quantidade de caixas utilizadas: " + respGulosoOrdenado.getqtdCaixas());
        System.out.println("Tempo de execucao: " + (tempoFinal - tempoInicial) / 1000000.0 + " ms");
        caixaGuloso.printContador();
        System.out.println("--------------------------------------------------");
        
        
        System.out.println("Guloso First Fit: ");
        Caixa gulosoFirstFit = new Caixa(x, y);
        Resultado respGulosoFirstFit = new Resultado();

        tempoInicial = System.nanoTime();
        respGulosoFirstFit = gulosoFirstFit.gulosoFirstFit(pacotesOriginais, pacotesOriginais);
        tempoFinal = System.nanoTime();
        
        respGulosoFirstFit.printAllMatriz();
        System.out.println("Quantidade de caixas utilizadas: " + respGulosoFirstFit.getqtdCaixas());
        System.out.println("Tempo de execucao: " + (tempoFinal - tempoInicial) / 1000000.0 + " ms");
        gulosoFirstFit.printContador();
        System.out.println("--------------------------------------------------");

        System.out.println("Guloso Best Fit: ");
        Caixa gulosoBestFit = new Caixa(x, y);
        Resultado respGulosoBestFit = new Resultado();

        tempoInicial = System.nanoTime();
        respGulosoBestFit = gulosoBestFit.gulosoBestFit(pacotesOriginais, pacotesOriginais);
        tempoFinal = System.nanoTime();

        respGulosoBestFit.printAllMatriz();
        System.out.println("Quantidade de caixas utilizadas: " + respGulosoBestFit.getqtdCaixas());
        System.out.println("Tempo de execucao: " + (tempoFinal - tempoInicial) / 1000000.0 + " ms");
        gulosoBestFit.printContador();
        System.out.println("--------------------------------------------------");
    
        
        System.out.println("Forca Bruta para a primeira solucao: ");
        Caixa caixaForcaBruta = new Caixa(x, y);
        Resultado respForcaBrutaPS = new Resultado();

        tempoInicial = System.nanoTime();
        respForcaBrutaPS = caixaForcaBruta.forcaBruta(pacotesOriginais);
        tempoFinal = System.nanoTime();

        respForcaBrutaPS.printAllMatriz();
        System.out.println("Quantidade de caixas utilizadas: " + respForcaBrutaPS.getqtdCaixas());
        System.out.println("Tempo de execucao: " + (tempoFinal - tempoInicial) / 1000000.0 + " ms");
        caixaForcaBruta.printContador();
        System.out.println("--------------------------------------------------");


        System.out.println("Forca Bruta para a melhor solucao: ");
        Caixa caixaFB2 = new Caixa(x, y);
        Resultado respForcaBrutaMS = new Resultado();

        tempoInicial = System.nanoTime();
        respForcaBrutaMS = caixaFB2.bruteForce(pacotesOriginais);
        tempoFinal = System.nanoTime();

        respForcaBrutaMS.printAllMatriz();
        System.out.println("Quantidade de caixas utilizadas: " + respForcaBrutaMS.getqtdCaixas());
        System.out.println("Tempo de execucao: " + (tempoFinal - tempoInicial) / 1000000.0 + " ms");
        caixaFB2.printContador();
        System.out.println("--------------------------------------------------");


        
    }
}
