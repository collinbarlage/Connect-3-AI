import java.io.*;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Board implements java.io.Serializable {
    IO io = new IO();

    private String boardArray[][] = new String[4][3];

    public Vector<Board> childBoards = new Vector<Board>();
    public Board parent;

    public boolean xTurn = true;

    public Board() {
        // default does nothing
    }

    public Board(Board og) { // copy constuctor
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
            // for each car
            Board b = new Board(this);
            while (b.canPlace(i)) {
                b.place(player,i);
                b.parent = new Board(this);
                path.add(new Board(b));
            }       
        }
        return path;
    }

    private int randomInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

    // public void random(int n) {
    //     Path path = new Path(); // random path to construct
    //     Board b = this;
    //     path.add(b);
    //     for (int i=0; i<n-1; i++) {
    //         // check for solution
    //         if(b.isDone()) {
    //             break;
    //         }
    //         Path next = b.next();
    //         b = next.get(randomInt(next.size()));
    //         path.add(new Board(b));
    //     }
    //     path.print();
    // }

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

}
