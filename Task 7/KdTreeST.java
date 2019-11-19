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


public class KdTreeST<Value> {
    private Node raiz;



    private class Node {
        private Point2D p;
        private Value value;
        private int size; //Tamanho
        private RectHV rect; //O retangulo correspondente a esse no
        private Node left; //A subarvore da esquerda
        private Node right; //A subarvore da direita


        public Node(Point2D p, Value value, double minX, double minY, double maxX, double maxY){
            this.p = p;
            this.value = value;
            this.rect = new RectHV(minX, minY, maxX, maxY);
            this.left = null;
            this.right = null;
            size = 1;
        }
    }

    private class PointEspecial implements Comparable<PointEspecial>{
        private Point2D query;
        private Point2D p;
        private double distancia;

        public PointEspecial(double px, double py, Point2D query){
            this.query = query;
            this.p = new Point2D(px,py);
            this.distancia = p.distanceSquaredTo(query);
        }

        public double distancia() {
            return this.distancia;
        }

        public Point2D p(){
            return this.p;
        }
        public int compareTo(PointEspecial other){
            if(this.distancia < other.distancia) return -1;
            if(this.distancia > other.distancia) return 1;
            return 0;
        }


    }


    // construct an empty symbol table of points
    public KdTreeST(){
        raiz = null;
    }

    // is the symbol table empty?
    public boolean isEmpty(){
        return size()==0;
    }

    // number of points
    public int size(){
        return size(raiz);
    }
    private int size(Node raiz){
        if(raiz == null) return 0;
        return raiz.size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val){
        if( p == null || val == null) throw new IllegalArgumentException();
        double menosInf = Double.NEGATIVE_INFINITY;
        double maisInf = Double.POSITIVE_INFINITY;
        raiz = putX(raiz, p, val, menosInf, menosInf, maisInf, maisInf);
        raiz.size = 1 + size(raiz.left) + size(raiz.right);
    }

    private int comparaX(Point2D a, Point2D b){
        if(a.x() < b.x()) return -1;
        if(a.x() > b.x()) return 1;
        return 0;
    }

    private int comparaY(Point2D a, Point2D b){
        if(a.y() < b.y()) return -1;
        if(a.y() > b.y()) return 1;
        return 0;
    }

    private Node putX(Node x, Point2D p, Value val, double minX, double minY, double maxX, double maxY){
        if(x == null) return new Node(p, val, minX, minY, maxX, maxY);
        int cmp = comparaX(x.p, p);
        if(cmp == 0){
            if(comparaY(x.p, p) == 0) x.value = val;
            else x.right = putY(x.right, p, val, x.p.x(), minY, maxX, maxY);
        }
        else if(cmp < 0){
            x.right = putY(x.right, p, val, x.p.x(), minY, maxX, maxY);
        }
        else{
            x.left = putY(x.left, p, val, minX, minY, x.p.x(), maxY);
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;

    }

    private Node putY(Node x, Point2D p, Value val, double minX, double minY, double maxX, double maxY){
        if(x == null) return new Node(p, val, minX, minY, maxX, maxY);
        int cmp = comparaY(x.p, p);
        if(cmp == 0){
            if(comparaX(x.p, p) == 0) x.value = val;
            else x.right = putX(x.right,p,val, minX, x.p.y(), maxX, maxY);
        }
        else if(cmp < 0){
            x.right = putX(x.right,p,val, minX, x.p.y(), maxX, maxY);
        }
        else{
            x.left = putX(x.left,p,val, minX, minY, maxX, x.p.y());
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;

    }

    // value associated with point p
    public Value get(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        return get(raiz, p, false);
    }

    private Value get(Node raiz, Point2D p, boolean comparacao){
        if( raiz == null) return null;
        int cmp;
        if(!comparacao){
            cmp = comparaX(raiz.p, p);
        }
        else{
            cmp = comparaY(raiz.p, p);
        }
        if(cmp == 0){
            if(comparaY(raiz.p, p) == 0 && comparaX(raiz.p, p) == 0) return raiz.value;
            else return get(raiz.right,p, !comparacao);

        }
        if(cmp < 0) return get(raiz.right, p, !comparacao);
        else return get(raiz.left, p, !comparacao);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p){
        if (p == null) throw new IllegalArgumentException();
        return get(p) != null;
    }

    // all points in the symbol table
    public Iterable<Point2D> points(){
        Node atual;
        Queue<Node> fila = new Queue<>();
        Queue<Point2D> resposta = new Queue<>();
        fila.enqueue(raiz);
        while(!fila.isEmpty()) {
            atual = fila.dequeue();
            if (atual != null) {
                resposta.enqueue(atual.p);
                fila.enqueue(atual.left);
                fila.enqueue(atual.right);
            }
        }
        return resposta;
    }


    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect){
        if(rect == null) throw new IllegalArgumentException();
        Node atual;
        Queue<Node> fila = new Queue<>();
        Queue<Point2D> resposta = new Queue<>();
        fila.enqueue(raiz);
        while(!fila.isEmpty()){
            atual = fila.dequeue();
            if(atual != null && rect.intersects(atual.rect)){
                if(rect.contains(atual.p)) resposta.enqueue(atual.p);
                fila.enqueue(atual.left);
                fila.enqueue(atual.right);

            }
        }
        return resposta;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        return nearest(p,1).iterator().next();
    }

    public Iterable<Point2D> nearest(Point2D p, int k){
        if(p == null) throw new IllegalArgumentException();
        MaxPQ<PointEspecial> maxpq = new MaxPQ<>();
        Queue<Point2D> resposta = new Queue<>();

        maxpq = nearestX(raiz, p,k,maxpq);
        for(PointEspecial point : maxpq){
            resposta.enqueue(point.p());
        }
        return resposta;

    }

    private MaxPQ<PointEspecial> nearestX(Node x, Point2D p, int k, MaxPQ<PointEspecial> maxpq){
        if(x == null) return maxpq;
        if(maxpq.size() < k) maxpq.insert(new PointEspecial(x.p.x(),x.p.y(),p));
        else if (p.distanceSquaredTo(maxpq.max().p()) > p.distanceSquaredTo(x.p)){
            maxpq.delMax();
            maxpq.insert(new PointEspecial(x.p.x(),x.p.y(),p));
        }
        //So realiza a busca se existe um ponto em potencial mais perto que os pontos atuais
        if(p.distanceSquaredTo(maxpq.max().p()) > x.rect.distanceSquaredTo(p)){
            int cmp = comparaX(x.p,p);
            if(cmp > 0){
                maxpq = nearestY(x.left,p,k,maxpq);
                maxpq = nearestY(x.right,p,k,maxpq);

            }
            else{
                maxpq = nearestY(x.right,p,k,maxpq);
                maxpq = nearestY(x.left,p,k,maxpq);
            }
        }
        return maxpq;
    }

    private MaxPQ<PointEspecial> nearestY(Node x, Point2D p, int k, MaxPQ<PointEspecial> maxpq){
        if(x == null) return maxpq;
        if(maxpq.size() < k) maxpq.insert(new PointEspecial(x.p.x(),x.p.y(),p));
        else if (p.distanceSquaredTo(maxpq.max().p()) > p.distanceSquaredTo(x.p)){
            maxpq.delMax();
            maxpq.insert(new PointEspecial(x.p.x(),x.p.y(),p));
        }
        //So realiza a busca se existe um ponto em potencial mais perto que os pontos atuais
        if(p.distanceSquaredTo(maxpq.max().p()) > x.rect.distanceSquaredTo(p)){
            int cmp = comparaY(x.p,p);
            if(cmp > 0){
                maxpq = nearestX(x.left,p,k,maxpq);
                maxpq = nearestX(x.right,p,k,maxpq);

            }
            else{
                maxpq = nearestX(x.right,p,k,maxpq);
                maxpq = nearestX(x.left,p,k,maxpq);
            }
        }
        return maxpq;
    }





    // unit testing (required)
    public static void main(String[] args){
        KdTreeST<String> arvore = new KdTreeST<>();

        //Cria alguns pontos
        Point2D pontoA = new Point2D(.7,.2);
        Point2D pontoB = new Point2D(.5,.4);
        Point2D pontoC = new Point2D(.2,.3);
        Point2D pontoD = new Point2D(.4,.7);
        Point2D pontoE = new Point2D(.9,.6);

        //Insere pontos na arvore
        arvore.put(pontoA,"A");
        arvore.put(pontoB,"B");
        arvore.put(pontoC,"C");
        arvore.put(pontoD,"D");
        arvore.put(pontoE,"E");

        //Verifica o metodo get
        StdOut.println(arvore.get(pontoC));

        StdOut.println();
        //Itera pelos pontos
        for(Point2D ponto : arvore.points()){
            StdOut.println(Double.toString(ponto.x()) + " " + Double.toString(ponto.y()));
        }

        StdOut.println();
        RectHV retangulo = new RectHV(.2,.3,.5,.7);
        //Itera pelos pontos dentro do retangulo
        for(Point2D ponto : arvore.range(retangulo)){
            StdOut.println(Double.toString(ponto.x()) + " " + Double.toString(ponto.y()));
        }

        StdOut.println();
        //Verifica tamanho da arvore
        StdOut.println(arvore.size());

        StdOut.println();
        //Testa o metodo do ponto mais proximo
        Point2D pontoTeste = new Point2D(0.5, 0.5);
        Point2D resposta = arvore.nearest(pontoTeste);
        StdOut.println(Double.toString(resposta.x()) + " " + Double.toString(resposta.y()));

        StdOut.println();
        //Testa o metodo contains
        StdOut.println(arvore.contains(pontoTeste));
        StdOut.println(arvore.contains(pontoA));

        StdOut.println();
        //Testa o metodo nearest com k > 1
        for(Point2D ponto : arvore.nearest(pontoTeste,2)){
            StdOut.println(Double.toString(ponto.x()) + " " + Double.toString(ponto.y()));
        }

    }
}
