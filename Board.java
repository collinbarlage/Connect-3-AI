import java.io.*;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Board implements java.io.Serializable {
    IO io = new IO();

    private String boardArray[][] = new String[4][3];

    public Vector<Car> cars = new Vector<Car>();
    public Vector<Board> childBoards = new Vector<Board>();
    public Board parent;

    public boolean tie = false; 

    public Board() {
        // default does nothing
    }

    public Board(Board og) { // copy constuctor
        this.parent = og.parent;
        this.tie = og.tie;

        String newBoardArray [][] = new String[6][6];
        for (int j=0; j<6; j++) {
            for (int i=0; i<6; i++) { 
                newBoardArray[i][j] = og.tile(i,j);
            }
        }
        this.boardArray = newBoardArray;

        Vector<Car> newCars = new Vector<Car>();
        for (int i=0; i<og.cars.size(); i++) {
            Car newCar = new Car(og.cars.get(i));
            newCars.add(newCar);
        }
        this.cars = newCars;
    }

    public void load(String init) {
        Vector<String> input = new Vector<String>();
        for (int i=0; i<init.length(); i++) {
            if (init.charAt(i) != '|') input.add("" + init.charAt(i));
        }
        // check if error
        if(input.size() != 15) {
            io.log("Oh geez, something went wrong. Check your argument string"); return;
        }
        // load input onto board
        for (int i=0; i<4; i++) {
            for (int j=2; j>=0; j--) { 
                boardArray[i][j] = input.get(j*4 + i);
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

    public boolean isVacant(int x, int y) {
        if (isInBounds(x, y) && tile(x,y).equals(" ")) { return true ; }
        return false;
    } 

    public boolean isInBounds(int x, int y) {
        if (x < 0 || y < 0 || x > 3 || y > 2) { return false; }
        return true;
    }


    public String winner() {
        if(tie) { return "TIE"; }

        //test horz
        for(int j=0; j<4; j++) {
            if(isWinner(0,j,1,0)) { return tile(0,j); }
            if(isWinner(1,j,1,0)) { return tile(1,j); }
        }

        //test vert
        for(int i=0; i<4; i++) {
            if(isWinner(i,0,0,1)) { return tile(i,0); }
        }

        //test diag
        if(isWinner(0,0,1,1))   { return tile(0,0); }
        if(isWinner(1,0,1,1))   { return tile(1,0); }
        if(isWinner(2,0,-1,-1)) { return tile(2,0); }
        if(isWinner(3,0,-1,-1)) { return tile(3,0); }
    }

    private String isWinner(int x, int y, int dx, int dy) {
        String winner = tile(x,y);
        for(int i=0; i<2; i++) {
            x += dx;
            y += dy;
            if(!winner.equals(tile(x,y))) {
                return false;
            }
        }
        return true;
    }

    // public boolean canBeDone() {
    //     String line = getLine(3);
    //     char[] lineChars = line.toCharArray();
    //     int x = 0;
    //     for (int i=7; i>=0; i--) {
    //         if(x == 2) { return true; }
    //         if(lineChars[i] == 'x') { x++; } 
    //         else if(lineChars[i] != ' ') { return false; }
    //     }
    //     return false;
    // }

    // public Path next() {
    //     Path path = new Path();
    //     for (int c=0; c<this.cars.size(); c++) {
    //         // for each car
    //         Board forward = new Board(this);
    //         while (forward.canMove(forward.cars.get(c), 1)) {
    //             forward.move(forward.cars.get(c), 1);
    //             forward.parent = new Board(this);
    //             path.add(new Board(forward));
    //         }      
    //         Board backward = new Board(this);
    //         while (backward.canMove(backward.cars.get(c), -1)) {
    //             backward.move(backward.cars.get(c), -1);
    //             backward.parent = new Board(this);
    //             path.add(new Board(backward));
    //         }       
    //     }
    //     return path;
    // }

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
