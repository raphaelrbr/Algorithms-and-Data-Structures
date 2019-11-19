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



import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static int[] list = new int[256];


    private static void init(){
        for(int i = 0; i<256; i++) {
            list[i] = i;

        }


    }

    private static void move(int pos){

        int t = list[pos];


        for(int i = pos; i>0; i--){
            list[i] = list[i-1];
        }

        list[0] = t;

    }


    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode(){
        init();

        while(!BinaryStdIn.isEmpty()){
            int carac = (int)BinaryStdIn.readChar();
            for(int i = 0; i<256; i++){
                if(list[i] == carac){
                    BinaryStdOut.write(i,8);
                    move(i);
                    break;
                }
            }

        }
        BinaryStdOut.close();
    }



    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode(){
        init();

        while(!BinaryStdIn.isEmpty()){
            int currCode = (int) BinaryStdIn.readChar();
            char carac = (char)list[currCode];
            BinaryStdOut.write(carac,8);

            move(currCode);
        }
        BinaryStdOut.close();

    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args){

        if(args[0].equals("-")) encode();
        else decode();
    }
}
