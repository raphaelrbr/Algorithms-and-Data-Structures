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


 Descrição de ajuda ou indicação de fonte:



 Se for o caso, descreva a seguir 'bugs' e limitações do seu programa:
 ****************************************************************/

/******************************************************************************
 *  Compilation:  javac-algs4 Combinacao.java
 *  Execution:    java Combinacao n k [opcao]
 *
 *  Enumera todas as combinações dos números em {1,2,...,n} k a k.
 *  Se opcao = 0 (defaul), gera e exibe todas as permutações em ordem
 *  lexicográfica
 *  Se opcao = 1 apenas, __gera todas__ as combinações, mas __não__ as
 *  exibe; apenas exibe o total de combinações.
 *
 * % java Combinacao 5 3 1
 * 10
 * elapsed time = 0.002
 * % java Combinacao 5 3
 * 1 2 3
 * 1 2 4
 * 1 2 5
 * 1 3 4
 * 1 3 5
 * 1 4 5
 * 2 3 4
 * 2 3 5
 * 2 4 5
 * 3 4 5
 * 10
 * elapsed time = 0.002
 * % java Combinacao 100 3 1
 * 161700
 * elapsed time = 0.004
 * % java Combinacao 1000 3 1
 * 166167000
 * elapsed time = 0.726
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Combinacao {
    private static int count = 0; // contador de combinações
    private static int opcao = 0;
    private static int num[];
    private static boolean parou = false;
    // 0 imprimir apenas o número de combinações (default)
    // 1 imprimir as combinações e o número de combinações

    public static void imprime(int k) {
        //percorre o vetor num e imprime todos os numeros cujo valor no vetor é 1

        int ant = num[0];
        for (int i = 1; i < k; i++) {
            if (num[i] < ant) return;
            ant = num[i];
        }

        if (opcao == 1) {
            count++;
            return;
        }
        for (int i = 0; i < k; i++) {
            StdOut.print(num[i] + " ");
        }
        StdOut.println("");
        count++;
    }

    //Wrapper
    public static void combinacao(int n, int k) {
        num = new int[k];
        for (int i = 0; i < k; i++) num[i] = -1;

        combinacao_rec(0, n, k);
    }

    //Verifica se todas as permutacoes foram impressas
    private static boolean acabou(int n, int k) {
        int c = n;
        for (int i = k - 1; i >= 0; i--) {
            if (num[i] != c) return false;
            c--;
        }
        return true;
    }

    //Escolhe um numero e chama recursivamente para escolher
    //os proximos numeros da sequencia.
    //i é a posicao atual na permutacao.
    private static void combinacao_rec(int i, int n, int k) {
        if (i == k) {
            imprime(k);
            return;
        }
        //Ja imprimiu todas as combinacoes
        if (acabou(n, k)) {
            imprime(k);
            parou = true;
            return;
        } else {
            //Para cada numero
            for (int j = 1; j <= n && !parou; j++) {
                //Verifica se o numero ja foi escolhido
                boolean jaEscolheu = false;
                for (int z = 0; z < i; z++) {
                    if (num[z] == j) {
                        jaEscolheu = true;
                        break;
                    }


                }
                //Se o numero ainda nao foi escolhido
                if (!jaEscolheu) {
                    //Escolhe o numero
                    num[i] = j;
                    //Chamada recursiva
                    combinacao_rec(i + 1, n, k);
                }
            }

        }
    }


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);
        if (args.length == 3) {
            opcao = Integer.parseInt(args[2]);
        }
        Stopwatch timer = new Stopwatch();
        combinacao(n, k);
        StdOut.println(count);
        StdOut.println("elapsed time = " + timer.elapsedTime());
    }
}
