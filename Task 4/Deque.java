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


import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;


public class Deque<Item> implements Iterable<Item>
{

    private class Node
    {
        private Item item;
        private Node prox, ant;

        public Node(Item item)
        {
            this.item = item;
            this.prox = null;
            this.ant = null;
        }
    }


    private Node cabeca, cauda;


    private int size;


    public Deque()
    {
        cauda = null; cabeca = null; size = 0;
    }


    public boolean isEmpty()
    {
        return size == 0;
    }


    public int size()
    {
        return size;
    }

    public void addFirst(Item item)
    {
        if (item == null) throw new NullPointerException();

        Node novaCabeca = new Node(item);
        novaCabeca.prox = cabeca;
        if (cabeca != null) cabeca.ant = novaCabeca;
        cabeca = novaCabeca;


        if (cauda == null) cauda = cabeca;

        size++;
    }


    public void addLast(Item item)
    {
        if (item == null) throw new NullPointerException();

        Node novaCauda = new Node(item);
        if (cauda != null) cauda.prox = novaCauda;
        novaCauda.ant = cauda;
        cauda = novaCauda;

        if (cabeca == null) cabeca = cauda;

        size++;
    }


    public Item removeFirst()
    {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = cabeca.item;

        Node novaCabeca = cabeca.prox;
        cabeca.prox = null;
        if (novaCabeca != null) novaCabeca.ant = null;
        cabeca = novaCabeca;


        if (cabeca == null) cauda = cabeca;

        size--;
        return item;
    }


    public Item removeLast()
    {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = cauda.item;

        Node novaCauda = cauda.ant;
        cauda.ant = null;
        if (novaCauda != null) novaCauda.prox = null;
        cauda = novaCauda;

        // Removing last element updates head
        if (cauda == null)
            cabeca = cauda;

        size--;
        return item;
    }

    /** Return an iterator over items in order from front to end */
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    /** Inner class representing deque iterator */
    private class DequeIterator implements Iterator<Item>
    {
        private Node atual = cabeca;

        public boolean hasNext()
        {
            return atual != null;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if (atual == null)
                throw new NoSuchElementException();

            Item item = atual.item;
            atual = atual.prox;
            return item;
        }
    }

        // unit testing (required)
        public static void main(String[] args){
            Deque<Integer> deque = new Deque<Integer>();

            //Adiciona alguns itens no inicio do deque
            StdOut.println("Adicionando alguns itens no inicio");
            StdOut.println("Adicionando 2 ao inicio");
            deque.addFirst(2);
            StdOut.println("Adicionando 4 ao inicio");
            deque.addFirst(4);
            StdOut.println("Adicionando 5 ao inicio");
            deque.addFirst(5);
            StdOut.println("Tamanho do deque");
            //Mostra o tamanho do deque
            StdOut.println(deque.size());
            StdOut.println("Iterando pelos itens");
            //Itera pelos itens do deque
            for(int item : deque){
                StdOut.println(item);
            }
            StdOut.println("Adicionando alguns itens no final do deque");
            //Adiciona alguns itens no final do deque
            StdOut.println("Adicionando 1 ao final");
            deque.addLast(1);
            StdOut.println("Adicionando 4 ao final");
            deque.addLast(4);
            //Mostra o tamanho do deque
            StdOut.println("Tamanho do deque");
            StdOut.println(deque.size());
            //Itera pelos itens do deque
            StdOut.println("Iterando pelos itens");
            for(int item : deque){
                StdOut.println(item);
            }
            //Remove o primeiro do deque
            StdOut.println("Removido do inicio: " + deque.removeFirst());
            //Remove o ultimo do deque
            StdOut.println("Removido do final: " + deque.removeLast());
            StdOut.println("Iterando pelos itens");
            //Itera pelos elementos do deque
            for(int item: deque){
                StdOut.println(item);
            }
            StdOut.println("Verifica se o deque este vazio");
            StdOut.println(deque.isEmpty());
            StdOut.println("Remove tres itens do final");
            StdOut.println("Removido do final: " +deque.removeLast());
            StdOut.println("Removido do final: " +deque.removeLast());
            StdOut.println("Removido do final: " +deque.removeLast());
            StdOut.println("Verifica se o deque esta vazio");
            StdOut.println("Verifica se o deque este vazio");
            StdOut.println(deque.isEmpty());


        }

}


