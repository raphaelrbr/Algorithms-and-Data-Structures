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



import edu.princeton.cs.algs4.*;


public class PointST<Value> {
    private RedBlackBST<Point2D, Value> redb;

    // construct an empty symbol table of points
    public PointST(){
        this.redb = new RedBlackBST<>();

    }

    // is the symbol table empty?
    public boolean isEmpty(){
        return this.redb.isEmpty();
    }

    // number of points
    public int size(){
        return this.redb.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val){
        if(p == null || val == null) throw new IllegalArgumentException();
        this.redb.put(p,val);
    }

    // value associated with point p
    public Value get(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        return this.redb.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        return this.redb.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points(){
        return this.redb.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect){
        if(rect == null) throw new IllegalArgumentException();
        Queue<Point2D> fila = new Queue<>();
        for(Point2D point : redb.keys()){
            if(rect.contains(point)) fila.enqueue(point);
        }
        return fila;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        Point2D minPoint = null;
        double min = Double.MAX_VALUE;
        for(Point2D point : redb.keys()){
            if(p.equals(point)) continue;
            if (p.distanceSquaredTo(point) < min){
                min = p.distanceSquaredTo(point);
                minPoint = point;
            }
        }
        return minPoint;
    }

    public Iterable<Point2D> nearest(Point2D p, int k){
        if(p == null) throw new java.lang.IllegalArgumentException();
        if(k >= redb.size()) return points(); //Corner case
        int i = 0;
        PointST<Value> aux = new PointST<>();
        Queue<Point2D> resposta = new Queue<>();
        //Copia a arvore rubro negra para a tabela auxiliar
        for(Point2D point : redb.keys())
            aux.put(point,redb.get(point));
        while(i<k){
            //pega o ponto mais proximo, salva e apaga
            Point2D pontoMaisProximo = aux.nearest(p);
            resposta.enqueue(pontoMaisProximo);
            aux.redb.delete(pontoMaisProximo);
            i++;
        }
        return resposta;
    }

    // unit testing (required)
    public static void main(String[] args){
        PointST<String> st = new PointST<String>();
        StdOut.println(st.isEmpty());
        Point2D p1 = new Point2D(1,0);
        Point2D p2 = new Point2D(2, 1);
        Point2D p3 = new Point2D(4,5);
        Point2D p4 = new Point2D(178.4, -350.2);
        st.put(p1, "Ponto 1");
        st.put(p2, "Ponto 2");
        st.put(p3, "Ponto 3");
        st.put(p4, "Ponto 4");
        StdOut.println(st.isEmpty());
        StdOut.println(st.contains(p1));
        StdOut.println(st.nearest(p3).toString());
        StdOut.println(st.get(p4));
        for(Point2D point : st.points()) {
            StdOut.println(st.get(point));

        }
        RectHV rect = new RectHV(1,-100,3,100);
        for(Point2D point : st.range(rect)){
            StdOut.println(point.toString());
        }

        StdOut.println();
        //Testa o metodo nearest com mais de um ponto
        Point2D pontoTeste = new Point2D(1,-1);
        for(Point2D point : st.nearest(pontoTeste, 2)){
            StdOut.println(point.toString());
        }
    }



}