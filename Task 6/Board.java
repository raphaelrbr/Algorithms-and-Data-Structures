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



import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board  {
    private int[][] tiles;
    private int hamming;
    private int manhattan;
    private int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        this.hamming = 0;
        this.manhattan = 0;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                this.tiles[i][j] = tiles[i][j];
                if(this.tiles[i][j] == 0) continue;

                this.hamming += ( (i*n+j+1) == this.tiles[i][j]) ? 0:1;

                //Distancia entre colunas
                int h = Math.abs(i*n + j + 1)%n;
                int t = this.tiles[i][j]%n;
                if(h == 0) h = n;
                if(t == 0) t = n;
                this.manhattan += Math.abs(h-t);
                //Distancia entre linhas
                this.manhattan += Math.abs(i - (this.tiles[i][j]-1)/n);

            }
        }
    }

    // string representation of this board
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append("\n");
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                sb.append(" ");
                sb.append(tiles[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col){
        if(row >= n || row < 0 || col < 0 || col>=n) throw new java.lang.IllegalArgumentException();
        return tiles[row][col];
    }

    // board size n
    public int size(){
        return n;
    }

    // number of tiles out of place
    public int hamming(){
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y){
        Board aux = (Board) y;
        if(aux.size() != this.size()) return false;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(tiles[i][j] != aux.tileAt(i,j)) return false;
            }
        }
        return true;

    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        int [][] d = {
                {0,1},
                {0,-1},
                {1,0},
                {-1,0}
        };
        Stack<Board> stack = new Stack<Board>();
        int i = 0,j = 0;
        for(i = 0; i<n; i++) {
            for (j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {

                    for (int h = 0; h < 4; h++) {
                        int a = i + d[h][0];
                        int b = j + d[h][1];
                        if (a >= 0 && b >= 0 && a < n && b < n) {
                            this.tiles[i][j] = this.tiles[a][b];
                            this.tiles[a][b] = 0;
                            stack.push(new Board(this.tiles.clone()));
                            this.tiles[a][b] = this.tiles[i][j];
                            this.tiles[i][j] = 0;

                        }
                    }
                }
            }
        }
        return stack;
    }

    // is this board solvable?
    public boolean isSolvable(){
        int inversions = 0;
        int blankRow = 0;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                if(tiles[i][j] == 0){
                    blankRow = i;
                    continue;
                }
                for(int i2 = i; i2<n; i2++){
                    for(int j2 = 0; j2<n; j2++){
                        if(i2 == i && j2<=j) continue;
                        if(tiles[i2][j2] == 0) continue;
                        if(tiles[i][j] > tiles[i2][j2]) inversions++;
                    }
                }
            }
        }
        if(n%2 == 1) return inversions%2 == 0;
        else return (inversions+blankRow)%2 == 1;
    }



    // unit testing (required)
    public static void main(String[] args){
        int [][] testTiles = {{2,3,5},{1,0,4},{7,8,6}};
        Board boardTeste = new Board(testTiles);
        int man = boardTeste.manhattan();
        int ham = boardTeste.hamming;
        StdOut.println(boardTeste.toString());
        StdOut.println("Manhattan = " + man);
        StdOut.println("Hamming = " + ham);
        StdOut.println("Solvable? " + boardTeste.isSolvable());

        StdOut.println("Imprimindo vizinhos");
        for(Board neigh : boardTeste.neighbors()){
            StdOut.println(neigh.toString());
        }

        StdOut.println("Segundo teste");
        int[][] segundoTeste = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,15,14,0}}; //Board impossivel
        Board segundoBoard = new Board(segundoTeste);
        StdOut.println(segundoBoard.toString());
        StdOut.println("Solvable? " + segundoBoard.isSolvable());
        StdOut.println("Is goal? " + segundoBoard.isGoal());
        StdOut.println("Equals to first board? " + segundoBoard.equals(boardTeste));
        StdOut.println("Tile at [1][1]? " + segundoBoard.tileAt(1,1));

    }

}