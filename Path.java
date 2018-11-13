import java.io.*;
import java.util.Vector;
import java.util.Collections;

public class Path implements java.io.Serializable {
    IO io = new IO();

    private int pathIndex = 0;
    private int xIndex = 0; 
    private Vector<Board> boards = new Vector<Board>();
    public Path parent;

    public Path() {
        // default does nothing
    }

    public Path(Path og) { // copy constuctor
        // this.pathIndex = og.pathIndex;
        // for (int i=0; i<og.size(); i++) {
        //     this.add(og.get(i));
        // }
        // if(og.hasParent()) {
        //     this.parent = og.parent;
        // }
    }

    public Board last() {
        return boards.get(boards.size() - 1);
    }


    public void add(Board b) {
        boards.add(b);
    }

    public Board get(int i) {
        return boards.get(i);
    }

    public int size() {
        return boards.size();
    }

    public void print() {
        String lines[] = {"", "", "", "", "", "", "", ""};

        for (int b=0; b<boards.size(); b++) {
            for (int l=0; l<8; l++) {
                lines[l] += boards.get(b).getLine(l) + " ";
            }
            if ((b+1)%6 == 0 || b == boards.size()-1) {
                for (int l=0; l<8; l++) { 
                    io.log(lines[l]); 
                    lines[l] = "";
                }
            }
        }
    }

    public void reverse() {
        Collections.reverse(this.boards);
    }

    public void append(Path p) {
        for(int i=0; i<p.size(); i++) {
            boards.add(p.get(i));
        }
    }

    private Path ommit(Path og, Path anti) {
        Path newPath = new Path();
        boolean isEqual;
        for(int i=0; i<og.size(); i++) {
            isEqual = false;
            for(int j=0; j<anti.size(); j++) {
                if(og.get(i).equals(anti.get(j))) {
                    isEqual = true;
                    break;
                }
            }
            if(!isEqual) { newPath.add(og.get(i)); }
        }
        return newPath;
    }



    public void bfs(Path path) {
        Path nextLevel = new Path();

        for (int i=0; i<path.size(); i++) {
            Board b = path.get(i);
            pathIndex++;
            //print parents
            Path route = new Path();
            route.add(b);
            while (b.hasParent()) {
                b = b.parent;
                route.add(b);
            }
            route.reverse();
            route.print();
            b = path.get(i);

            if(b.isDone()) {
                io.log(""+pathIndex);
                return;
            } else {
                //build next level
                nextLevel.append(ommit(ommit(b.next(), route), nextLevel));
            }
        }
        bfs(nextLevel);
        return;
    }

    public void astar(Path path) {
        Path nextLevel = new Path();

        for (int i=0; i<path.size(); i++) {
            Board b = path.get(i);
            pathIndex++;
            //print parents
            Path route = new Path();
            route.add(b);
            while (b.hasParent()) {
                b = b.parent;
                route.add(b);
            }
            route.reverse();
            route.print();
            b = path.get(i);

            //if solution is in next, jump to that solution
            if(b.canBeDone()) {
                Board bMove = new Board(b);
                while(!b.isDone()) {
                    bMove.move(bMove.getX(), 1);
                    bMove.parent = b;
                    b = bMove;
                }
                route.add(new Board(b));
                route.print();
            }


            if(b.isDone()) {
                io.log(""+pathIndex);
                return;
            } else {
                //build next level
                Path potentialNextLevel = ommit(ommit(b.next(), route), nextLevel);
                nextLevel.append(reduce(potentialNextLevel));
            }
        }
        astar(nextLevel);
        return;
    }

    public Path reduce(Path og) {

        Path reducedPath = new Path();
        reducedPath.parent = og.parent;
        for(int i=0; i<og.size(); i++) {
            //check x index
            if(og.get(i).xIndex > xIndex) {
                xIndex = og.get(i).xIndex;
            }
            // io.log("current x: " +  og.get(i).xIndex + "... over all: "+ xIndex);
            if(og.get(i).xIndex == 2 && xIndex == 3 ) {
                //do nothing lol
            }  else if(og.get(i).xIndex > 0 || xIndex == 0 ) {
                // io.log("it's lit");
                reducedPath.add(og.get(i));
            }
        }
        return reducedPath;
    }






}












/*


*/





