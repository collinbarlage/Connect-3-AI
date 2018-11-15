import java.io.*;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Board implements java.io.Serializable {
    IO io = new IO();

    private String boardArray[][] = new String[4][3];

    public Vector<Board> childBoards = new Vector<Board>();
    public Board parent;

    public boolean xTurn = true;
    public int parentIndex = 0;
    public int xIndex = 0;
    public int oIndex = 0;

    public Board() {
        // default does nothing
    }

    public Board(Board og) { // copy constuctor
        this.parentIndex = og.parentIndex;
        this.parent = og.parent;
        this.xTurn = og.xTurn;

        String newBoardArray [][] = new String[4][3];
        for (int j=0; j<3; j++) {
            for (int i=0; i<4; i++) { 
                newBoardArray[i][j] = og.tile(i,j);
            }
        }
        this.boardArray = newBoardArray;
    }

    public void load(String init) {
        Vector<String> input = new Vector<String>();
        for (int i=0; i<init.length(); i++) {
            if (init.charAt(i) != '|') input.add("" + init.charAt(i));
        }
        // check if error
        if(input.size() != 12) {
            io.log("Oh geez, something went wrong. Check your argument string"); return;
        }
        // load input onto board
        int c = 0;
        for (int i=0; i<4; i++) {
            for (int j=2; j>=0; j--) { 
                boardArray[i][j] = input.get(c);
                c++;
            }
        }
    }

    public boolean hasParent() {
        return (parent != null);
    }

    public String tile(int x, int y) {
        return boardArray[x][y];
    }

    public String getLine(int y) {
        String str = "|"; // left wall
        if (y == 0 || y == 4) { return " ---- "; } // top and bottom
        for (int i=0; i<4; i++) { str += tile(i, y-1); }
        str += "|"; // right wall
        return str;
    }    

    public void display() {
        for (int y=0; y<5; y++) {
            io.log(getLine(y));
        }
    }

    public String winner() {
        if(isTie()) { return "TIE"; }
        //test horz
        for(int j=0; j<3; j++) {
            if(isWinner(0,j,1,0)) { return tile(0,j); }
            if(isWinner(1,j,1,0)) { return tile(1,j); }
        }
        //test vert
        for(int i=0; i<4; i++) {
            if(isWinner(i,0,0,1)) { return tile(i,0); }
        }
        //test diag
        if(isWinner(0,0,1,1))  { return tile(0,0); }
        if(isWinner(1,0,1,1))  { return tile(1,0); }
        if(isWinner(2,0,-1,1)) { return tile(2,0); }
        if(isWinner(3,0,-1,1)) { return tile(3,0); }

        return "None";
    }

    private boolean isWinner(int x, int y, int dx, int dy) {
        String winner = tile(x,y);
        for(int i=0; i<2; i++) {
            x += dx;
            y += dy;
            if(!winner.equals(tile(x,y)) || tile(x,y).equals(" ")) {
                return false;
            }
        }
        return true;
    }

    private boolean isTie() {
        for (int i=0; i<4; i++) {
            for (int j=0; j<3; j++) { 
                if(tile(i,j).equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canPlace(int x) {
        return(tile(x,0).equals(" "));
    }

    public void place(String chip, int x) {
        int j = nextFreeSpace(x);
        boardArray[x][j] = chip;
    }

    public void endTurn() {
        xTurn = !xTurn;
    }

    private int nextFreeSpace(int x) {
        for(int j=2; j>=0; j--) {
            if(tile(x,j).equals(" ")) {
                return j;
            }            
        }
        return -1;
    }

    public Path next() {
        Path path = new Path();
        String player = "O";
        if(xTurn) { player = "X"; }
        for (int i=0; i<4; i++) {
            Board b = new Board(this);
            if(b.canPlace(i)) {
                b.place(player,i);
                b.parent = new Board(this);
                b.parentIndex = this.parentIndex;
                b.countBoard();
                path.add(new Board(b));
            }       
        }
        return path;
    }

    private int randomInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

    public void random() {
        Path path = new Path(); // random path to construct
        Board b = this;
        path.add(b);
        while(b.winner().equals("None")) {
            Path next = b.next();
            b = next.get(randomInt(next.size()));
            path.add(new Board(b));
            b.endTurn();
        }
        path.print();
    }

    public boolean equals(Board b) {
        for (int j=0; j<3; j++) {
            for (int i=0; i<4; i++) { 
                if(!this.tile(i,j).equals(b.tile(i,j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canWin() {
        Board board = new Board(this);
        board.endTurn();
        Path next = board.next();
        for (int i=0; i<next.size(); i++) {
            if(next.get(i).winner().equals("X")) {
                return true;
            }
        }
        return false;
    }

    public Board block() {
        Path next = this.next();
        for (int i=0; i<next.size(); i++) {
            if(!next.get(i).canWin()) {
                return next.get(i);
            }
        }
        return next.get(0);
    }

    private void count(int x, int y, int dx, int dy) {
        int iX = 0; int iO = 0;
        for(int i=0; i<2; i++) {
            x += dx; y += dy;
            if(tile(x,y).equals("X")) {
                if(iO<2) { iO = 0; }
                iX++;
            } else if(tile(x,y).equals("O")) {
                if(iX<2) { iX = 0; }
                iO++;
            } else {
                if(iX<2) { iX = 0; }
                if(iO<2) { iO = 0; }
            }
        }
        if(xIndex < iX) { xIndex = iX; }
        if(oIndex < iO) { oIndex = iO; }
    }

    public void countBoard() {
        //count horz
        for(int j=0; j<3; j++) {
            count(0,j,1,0);
            count(1,j,1,0);
        }
        //count vert
        for(int i=0; i<4; i++) { count(i,0,0,1); }
        //count diag
        count(0,0,1,1); count(1,0,1,1);
        count(2,0,-1,1); count(3,0,-1,1);
    }

    public void minimax() {
        Player x = new Player("X");
        Player o = new Player("O");
        Path path = new Path();
        Board b = this;
        b.countBoard();
        path.add(b);
        while(b.winner().equals("None")) {
            if(b.xTurn) {
                b = x.randomMove(b);
            } else {
                long startTime = System.nanoTime();
                b = o.minimaxMove(b);
                long endTime = System.nanoTime();
                io.log("Duration of minimax move: " + (float(endTime - startTime)/1000000) + " ms");
            }
            path.add(new Board(b));
            b.endTurn();
        }
        path.print();
    }

    public int minimaxSearch(Path path) {
        Path nextLevel = new Path();

        for (int i=0; i<path.size(); i++) {
            Board b = path.get(i);
            if(b.winner().equals("O")) { //max
                return b.parentIndex;
            }
            nextLevel.append(min(b.next())); //min
        }
        if(nextLevel.size() == 0) {
            io.log("It's a tie :(");
            return 0; //tie
        }
        return path.get(minimaxSearch(nextLevel)).parentIndex;
    }

    public Path min(Path og) {
        Path minPath = new Path();
        for(int i=0; i<og.size(); i++) {
            if(!og.get(i).winner().equals("X")) {
                minPath.add(og.get(i));
            }
        }
        return minPath;
    }
}
