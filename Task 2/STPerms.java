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
 *  Compilation:  javac-algs4 STPerms.java
 *  Execution:    java STPerms n s t opcao
 *  
 *  Enumera todas as (s,t)-permutações das n primeiras letras do alfabeto.
 *  As permutações devem ser exibidas em ordem lexicográfica.
 *  Sobre o papel da opcao, leia o enunciado do EP.
 *
 *  % java STPerms 4 2 2 0
 *  badc
 *  bdac
 *  cadb
 *  cdab
 *  % java STPerms 4 2 2 1
 *  4
 *  % java STPerms 4 2 2 2
 *  badc
 *  bdac
 *  cadb
 *  cdab
 *  4
 *   
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

public class STPerms {
    private static int num = 0;
    private static int[] palavras;
    private static int tamanho;
    private static int[] tabelaS = new int[26];
    private static int lenS = 0;
    private static int [] tabelaT = new int[26];
    private static int lenT = 0;
    private static int opcao;

    //Devolve uma posicao j, tal que tabelaT[j-1] > k e tabelaT[j] < k
    //Ou devolve 0, caso tabelaS[0] < k
    //Note que tabelaT é um vetor decrescente
    private static int dec(int x){
        int p, q;
        p = 0;
        q = lenT-1;



        while(true){
            int m = (p+q)/2;
            if(tabelaT[p] < x) return p;
            else if(tabelaT[m] < x){
                if(m-1 >= p && tabelaT[m-1] > x) return m;
                else q=m-1;
            }
            else{
                if(m+1 <= q && tabelaT[m+1] < x) return m+1;
                else p=m+1;
            }


        }
    }


    //Devolve uma posicao j, tal que tabelaS[j-1] < k e tabelaS[j] > k
    //Ou devolve 0, caso tabelaS[0] > k
    //Note que tabelaS é um vetor crescente
    private static int cresc(int x){
        int p, q;
        p = 0;
        q = lenS-1;
        while(true){


            int m = (p+q)/2;
            if(x < tabelaS[p]) return p;
            if(tabelaS[m] < x){
                if(m+1 <= q && x < tabelaS[m+1]){
                    return m+1;
                }
                else{
                    p = m+1;
                }
            }
            else{
                if(m-1 >= q && x > tabelaS[m-1]) {
                    return m;
                }
                else{
                    q = m-1;
                }
            }
        }
    }

    private static void perm(String substring, int n, int s, int t){
        if(n == 0){
            if(opcao != 1)
                StdOut.println(substring);
            num++;
        }
        int [] backupTabelaS;
        int [] backupTabelaT;
        int backuplenS;
        int backuplenT;

        //Para cada letra
        for(int i = 0; i<tamanho; i++){
            backupTabelaS = tabelaS.clone();
            backupTabelaT = tabelaT.clone();
            backuplenS = lenS;
            backuplenT = lenT;
            int j;
            //Se a letra nao foi escolhida
            if(palavras[i] == 0){
                //Escolhe a letra
                char letra = (char)(i + 'a');
                palavras[i] = 1;
                substring = substring + "" + letra;
                //Verifica se é a primeira recursao
                if (lenS == 0){
                    tabelaS[0] = i;
                    tabelaT[0] = i;
                    lenS = lenT = 1;
                }
                else {
                    //Processamento sequencia crescente
                    if (i < tabelaS[0]) tabelaS[0] = i;
                    else if (i > tabelaS[lenS - 1]) {
                        tabelaS[lenS] = i;
                        lenS++;
                    } else {
                        j = cresc(i);
                        tabelaS[j] = i;
                    }
                    //Processamento sequencia decrescente
                    if (i > tabelaT[0]) tabelaT[0] = i;
                    else if (i < tabelaT[lenT - 1]) {
                        tabelaT[lenT] = i;
                        lenT++;
                    } else {
                        j = dec(i);
                        tabelaT[j] = i;
                    }
                }
                //Verifica se os limites S ou T nao foram extrapolados
                if(lenS <= s && lenT <= t) {
                    perm(substring, n - 1, s, t);
                }
                palavras[i] = 0;
                substring = substring.substring(0, substring.length()-1);
                tabelaS = backupTabelaS;
                tabelaT = backupTabelaT;
                lenS = backuplenS;
                lenT = backuplenT;
            }
        }
    }


    public static void main(String[] args) {

        
        int n = Integer.parseInt(args[0]);
        int s = Integer.parseInt(args[1]);
        int t = Integer.parseInt(args[2]);
        opcao = Integer.parseInt(args[3]);
        palavras = new int[n];
        tamanho = n;
        perm("", n, s, t);
        if(opcao != 0)
            StdOut.println(num);

    }
}
