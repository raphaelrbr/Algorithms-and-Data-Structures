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
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Arrays;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item [] a = null;
    private int n;

    // construct an empty randomized queue
    @SuppressWarnings("unchecked")
    public RandomizedQueue(){
        a = (Item[]) new Object[1];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return n;
    }

    private void resize(int max){
        @SuppressWarnings("unchecked")
        Item[] temp = (Item[]) new Object[max];
        System.arraycopy(a,0,temp,0,n);
        a = temp;
    }

    // add the item
    public void enqueue(Item item){
        if(item == null) throw new java.lang.IllegalArgumentException();
        if(n == a.length) resize(2*n);
        a[n++] = item;
    }

    // remove and return a random item
    public Item dequeue(){
        if(n==0) throw new java.util.NoSuchElementException();
        //Sorteia um indice, troca o final da fila com o indice sorteado e remove do final da fila
        int indice = StdRandom.uniform(n);
        Item item = a[indice];
        a[indice] = a[n-1];
        n--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(n==0) throw new java.util.NoSuchElementException();
        int rand = StdRandom.uniform(n);
        return a[rand];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>{
        private Item[] randomQueue = java.util.Arrays.copyOf(a, n);
        private int tam = n;

        public boolean hasNext() {
            return tam > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if(!hasNext()) throw new UnsupportedOperationException();
            int indice = StdRandom.uniform(tam);
            Item item = a[indice];
            a[indice] = a[tam-1];
            tam--;
            return item;
        }

    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> fila = new RandomizedQueue<Integer>();
        StdOut.println("Adicionando alguns elementos");
        StdOut.println("Adicionado 2");
        fila.enqueue(2);
        StdOut.println("Adicionado 6");
        fila.enqueue(6);
        StdOut.println("Adicionado 9");
        fila.enqueue(9);
        StdOut.println("Adicionado 5");
        fila.enqueue(5);
        StdOut.println("Retirando alguns elementos");
        StdOut.println("Removido: " + fila.dequeue());
        StdOut.println("Removido " + fila.dequeue());
        StdOut.println("Adicionando mais alguns elementos");
        StdOut.println("Adicionado 10");
        fila.enqueue(10);
        StdOut.println("Adicionado 20");
        fila.enqueue(20);
        StdOut.println("Adicionado 30");
        fila.enqueue(30);
        StdOut.println("Adicionado 40");
        fila.enqueue(40);
        StdOut.println("Tamanho da fila: " + fila.size() );
        StdOut.println("Verifica se fila esta vazia");
        StdOut.println(fila.isEmpty());

        StdOut.println("Iterando pela fila");
        for(int item: fila){
            StdOut.println(item);
        }

    }


}
