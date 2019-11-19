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

// excessões pedidas
import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;
import java.util.NoSuchElementException;

// pode ser útil
import java.util.Arrays; // Arrays.sort(), Arrays.copyOf(), ...

import java.util.Iterator; // passo 0 para criarmos um iterador

import edu.princeton.cs.algs4.StdOut;

public class Arrangements implements Iterable<String> {
	private String str;
    private int tamanho;

	public Arrangements(String s){
		if( s == null) throw new IllegalArgumentException();
        //Salva a string s ordenada
        char temp[] = s.toCharArray();
        Arrays.sort(temp);
        str = new String(temp);
        //Salva o tamanho da string
        tamanho = s.length();
    }

    public Iterator<String> iterator() {
        return new ArrangementIterator();
    }

    private class ArrangementIterator implements Iterator<String> {
        private String s = str;
        private boolean primeiroArranjo = true;


        public boolean hasNext(){
            int first = -1;
            for(int i = 0; i<tamanho-1; i++){
                if(s.charAt(i) < s.charAt(i+1)){
                    first = i;
                }
            }
            if (first == -1) return false;
            return true;
        }

        public String next() {
            char first = 'a', second='a';
            int first_i = -1;
            int second_i = -1;
            if(primeiroArranjo){
                primeiroArranjo= false;
                return s;
            }

            for(int i = 0; i<tamanho-1; i++){
                if(s.charAt(i) < s.charAt(i+1)){
                    first = s.charAt(i);
                    first_i = i;
                }
            }
            if (first_i == -1){
                throw new NoSuchElementException();
            }
            for(int i = first_i+1; i<tamanho; i++){
                if(s.charAt(i) > first && (second_i == -1 || s.charAt(i) <= second)){
                    second = s.charAt(i);
                    second_i = i;
                }
            }
            //Troca o primeiro caracter com o segundo e inverte a string
            //Da posicao f+1 a tamanho-1; onde f é o indice do primeiro caracter.
            StringBuilder sba = new StringBuilder(s.substring(0,first_i));
            sba.append(second);
            StringBuilder sbb = new StringBuilder(s.substring(first_i+1, second_i));
            sbb.append(first).append(s.substring(second_i+1,tamanho)).reverse();
            s = sba.append(sbb.toString()).toString();
            return s;

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    // Unit test
    public static void main(String[] args) {
        String s = args[0];
        Arrangements arr = new Arrangements(s);
        
        StdOut.println("Teste 1: imprime no máximo os 10 primeiros arranjos");
        Iterator<String> it = arr.iterator();
        for (int i = 0; it.hasNext() && i < 10; i++) {
            StdOut.println(i + " : " + it.next());
        }
        
        StdOut.println("Teste 2: imprime todos os arranjos");
        int i = 0;
        for (String arranjo: arr) {
            StdOut.println(i + " : " + arranjo);
            i++;
        }
    }
}
