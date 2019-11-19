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


public class Solver {
    private Step solution;
    private Stack<Board> solutionsStack;

    private class Step implements Comparable<Step>{
        private int priority;
        private Board board;
        private Step lastStep;

        public Step(Board board, int priority, Step lastStep){
            this.board = board;
            this.priority = priority;
            this.lastStep = lastStep;

        }

        public Board board(){
            return board;
        }

        public int priority(){
            return priority;
        }

        public int moves(){
            return priority-board.manhattan();
        }

        public Step lastStep(){
            return lastStep;
        }

        public int compareTo(Step otherStep){
            if(this.priority < otherStep.priority) return -1;
            if(this.priority > otherStep.priority) return 1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        MinPQ<Step> minPQ = new MinPQ<>();
        Step initialStep = new Step(initial, initial.manhattan(), null);
        solution = initialStep;
        Board anterior = initial;
        minPQ.insert(initialStep);
        while(!minPQ.isEmpty()){
            Step current = minPQ.delMin();
            if(current.board().isGoal()){
                solution = current;
                break;
            }
            for(Board neighbor : current.board().neighbors()){
                if(neighbor.equals(anterior)) continue; //Otimizacao critica
                Step nextStep = new Step(neighbor, neighbor.manhattan() + current.moves() + 1, current);
                minPQ.insert(nextStep);
            }
            anterior = current.board();
        }

        this.solutionsStack = new Stack<>();
        this.solutionsStack.push(solution.board());
        Step current = solution.lastStep();
        while(current != null){
            this.solutionsStack.push(current.board());
            current = current.lastStep();
        }
        StdOut.println(solutionsStack.size());
    }

    // min number of moves to solve initial board
    public int moves(){
        return solution.moves();
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution(){

        return solutionsStack;
    }

    // test client (see below)
    public static void main(String[] args){

        In input = new In(args[0]);
        int n = input.readInt();
        int [][]tiles = new int[n][n];
        for(int i = 0; i<n; i++){
            for(int j = 0; j<n; j++){
                tiles[i][j] = input.readInt();
            }
        }
        Board board = new Board(tiles);


        if(board.isSolvable()){
            Solver solver = new Solver(board);
            StdOut.println("Minimum number of moves = " + solver.moves());
            for(Board step : solver.solution()){
               StdOut.println(step.toString());
            }
        }
        else{
            StdOut.println("Unsolvable puzzle");
        }


    }

}