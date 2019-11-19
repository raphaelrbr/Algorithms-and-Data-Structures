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

public class Percolation {
    private int [][] grade;
    private int n;
    private int numOpen;
    private boolean percolate;
    private UF uf; //Union-find
    private int[] referencias; //Guarda as referencias dos ids que representam blocos cheios
    private int[] potenciais; //Guarda potenciais ids a colatar o sistema
    private class UF {
        private int[] pai;
        private int[] sz;
        private int count;

        // Initializes an empty union-find data structure with n
        // isolated components 0 through n-1
        private UF(int n) {
            count = n;
            pai = new int[n];
            sz = new int[n];
            for (int i = 0; i < n; i++) {
                pai[i] = i;
                sz[i] = 1;
            }
        }

        // Return the number of components.
        private int count() {
            return count;
        }

        // Are the two sites p and q in the same component?
        private boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        // Return the component identifier for the component
        // containing site p.
        private int find(int p) {
            while (p != pai[p]) {
                pai[p] = pai[pai[p]];  // diminui o tamanho do caminho a metade
                p = pai[p];
            }
            return p;
        }

        // Merge the component containing site p with the the
        // component containing site q.
        private void union(int p, int q) {
            int pRoot = find(p);
            int qRoot = find(q);
            if (pRoot == qRoot) return ;
            if(potenciais[pRoot] == 1 || potenciais[qRoot] == 1)
                potenciais[pRoot] = potenciais[qRoot] = 1;
            if(referencias[pRoot] == 1 || referencias[qRoot] == 1) {
                if(potenciais[pRoot] == 1 || potenciais[qRoot] == 1)
                    percolate = true;
                referencias[pRoot] = referencias[qRoot] = 1;

            }

            if (sz[pRoot] < sz[qRoot]) {
                pai[pRoot] = qRoot;
                sz[qRoot] += sz[pRoot];
            }
            else {
                pai[qRoot] = pRoot;
                sz[pRoot] += sz[qRoot];
            }
            count--;
        }

    }

    public Percolation(int n){
        grade = new int[n][n];
        referencias = new int[n*n];
        potenciais = new int[n*n];
        numOpen = 0;
        this.n = n;
        uf = new UF(n*n);
        percolate = false;

    }

    public void open(int row, int col){
        if(row < 0 || row > n-1 || col < 0 || col > n-1) throw new IllegalArgumentException();
        if(!isOpen(row,col)){
            int indice = uf.find(n*row+col);
            numOpen++;
            grade[row][col] = 1;
            //Se o elemento está na primeira linha, entao ele é um bloco cheio
            if(row == 0) {
                referencias[indice] = 1;
            }
            //Se o elemento esta na ultima linha, entao seu id é potencial
            //para fazer o sistema colatar
            if(row == n-1)
                potenciais[indice] = 1;

            //Se conecta com os blocos abertos adjacentes

            //Baixo
            if(row+1 < n && grade[row+1][col] == 1)
                uf.union(n*row+col, n*(row+1)+col );
            //Cima
            if(row-1 >= 0 && grade[row-1][col] == 1)
                uf.union(n*row+col, n*(row-1)+col);
            //Direita
            if(col+1 < n && grade[row][col+1] == 1)
                uf.union(n*row+col,n*row+col+1 );
            //Esquerda
            if(col-1 >= 0 && grade[row][col-1] == 1)
                uf.union(n*row+col,n*row+col-1 );


        }

    }

    public boolean isOpen(int row, int col){
        return grade[row][col] == 1;
    }

    public boolean isFull(int row, int col){
        return referencias[uf.find(row*n + col)] == 1;
    }

    public int numberOfOpenSites(){
        return numOpen;
    }

    public boolean percolates(){
        return percolate;
    }

    public static void main(String args[]){
        Percolation percolation = new Percolation(5);
        StdOut.println("Verifica se o bloco 0,0 esta aberto");
        StdOut.println(percolation.isOpen(0,0));
        StdOut.println("Abre o bloco 0,0");
        percolation.open(0,0);
        StdOut.println("Verifica se o bloco 0,0 esta aberto");
        StdOut.println(percolation.isOpen(0,0));
        StdOut.println("Abre o bloco 1,1");
        percolation.open(1,1);
        StdOut.println("Verifica se o bloco 1,1 esta cheio");
        StdOut.println(percolation.isFull(1,1));
        StdOut.println("Abre o bloco 1,0");
        percolation.open(1,0);
        StdOut.println("Verifica se o bloco 1,1 esta cheio");
        StdOut.println(percolation.isFull(1,1));
        StdOut.println("Abre o bloco 3,0");
        percolation.open(3,0);
        StdOut.println("Abre o bloco 4,0");
        percolation.open(4,0);
        StdOut.println("Verifica se o sistema percolata");
        StdOut.println(percolation.percolates());
        StdOut.println("Abre o bloco 2,0");
        percolation.open(2,0);
        StdOut.println("Verifica se o sistema percolata");
        StdOut.println(percolation.percolates());
        StdOut.println("Numero de blocos abertos: " + percolation.numberOfOpenSites());

    }






}
