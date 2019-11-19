/****************************************************************
 Nome: Raphael Ribeiro da Costa e Silva
 NUSP: 10281601

 Ao preencher esse cabeçalho com o meu nome e o meu número USP,
 declaro que todas as partes originais desse exercício programa (EP)
 foram desenvolvidas e implementadas por mim e que portanto não
 constituem desonestidade acadêmica ou plágio.
 Declaro também que sou responsável por todas as cópias desse
 programa e que não distribui ou facilitei a sua distribuição.
 Estou ciente que os casos de plágio e desonestidade acadêmica
 serão tratados segundo os critérios divulgados na página da
 disciplina.
 Entendo que EPs sem assinatura devem receber nota zero e, ainda
 assim, poderão ser punidos por desonestidade acadêmica.

 Abaixo descreva qualquer ajuda que você recebeu para fazer este
 EP.  Inclua qualquer ajuda recebida por pessoas (inclusive
 monitoras e colegas). Com exceção de material de MAC0323, caso
 você tenha utilizado alguma informação, trecho de código,...
 indique esse fato abaixo para que o seu programa não seja
 considerado plágio ou irregular.

 Exemplo:

 A monitora me explicou que eu devia utilizar a função xyz().

 O meu método xyz() foi baseada na descrição encontrada na
 página https://www.ime.usp.br/~pf/algoritmos/aulas/enumeracao.html.

 Descrição de ajuda ou indicação de fonte:



 Se for o caso, descreva a seguir 'bugs' e limitações do seu programa:

 ****************************************************************/


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private double [] estimates;
    private int trials;



    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        this.trials = trials;
        estimates = new double[trials];
        for(int i = 0; i<trials; i++) {

            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                while (true) {

                    int linhaSorteada = StdRandom.uniform(n);

                    int colunaSorteada = StdRandom.uniform(n);

                    if (!percolation.isOpen(linhaSorteada, colunaSorteada)) {
                        percolation.open(linhaSorteada, colunaSorteada);
                        break;
                    }
                }

            }

            estimates[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }

    }

    // sample mean of percolation threshold
    public double mean(){
        double mean = 0;
        for(int i = 0; i<trials;i++)
            mean += estimates[i];
        return mean/trials;

    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(estimates);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow(){
        return mean()-((1.96*stddev())/java.lang.Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh(){
        return mean()+((1.96*stddev())/java.lang.Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        Stopwatch stopwatch = new Stopwatch();
        PercolationStats percStats = new PercolationStats(n, trials);
        StdOut.println("mean() = " + percStats.mean());
        StdOut.println("stddev() = " + percStats.stddev());
        StdOut.println("confidenceLow() = " + percStats.confidenceLow());
        StdOut.println("confidenceHigh() = " + percStats.confidenceHigh());
        StdOut.println("elapsed time  = " + stopwatch.elapsedTime());


    }
}
