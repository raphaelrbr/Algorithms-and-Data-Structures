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


import edu.princeton.cs.algs4.QuickX;
import edu.princeton.cs.algs4.StdOut;

public class CircularSuffixArray {
    private final int n;
    private final String s;
    private int[] index;


    private class CircularSuffix implements Comparable<CircularSuffix> {
        private int rot;
        public CircularSuffix(int rot) {
            this.rot = rot;
        }

        public char charAt(int pos)
        {
            return s.charAt((pos + rot) % n);
        }
        @Override
        public int compareTo(CircularSuffix o) {
            if (this == o)   return 0;
            for (int i = 0; i < n; i++)
            {
                if (charAt(i) < o.charAt(i))
                    return -1;
                if (charAt(i) > o.charAt(i))
                    return 1;
            }
            return 0;
        }
    }
    public CircularSuffixArray(String s)
    {
        if (s == null)
            throw new IllegalArgumentException();
        n = s.length();
        this.s = s;
        index = new int[n];
        // for (int i = 0; i < n; i++)
        //     index[i] = i;
        CircularSuffix[] circulares = new CircularSuffix[n];
        for (int i = 0; i < n; i++)
            circulares[i] = new CircularSuffix(i);
        QuickX.sort(circulares);

        for (int i = 0; i < n; i++)
        {
            index[i] = circulares[i].rot;
        }
    }
    public int length()
    {
        return n;
    }
    public int index(int i)
    {
        if (i < 0 || i >= n)
            throw new IllegalArgumentException();
        return index[i];

    }
    public static void main(String[] args)
    {
        CircularSuffixArray test = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < test.n; i++)
            StdOut.print(test.index(i) + " ");
    }
}
