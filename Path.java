import java.io.*;
import java.util.Vector;
import java.util.Collections;

public class Path implements java.io.Serializable {
    IO io = new IO();

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
        String lines[] = {"", "", "", "", ""};

        for (int b=0; b<boards.size(); b++) {
            for (int l=0; l<5; l++) {
                lines[l] += boards.get(b).getLine(l) + " ";
            }
            if ((b+1)%6 == 0 || b == boards.size()-1) {
                for (int l=0; l<5; l++) { 
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




}



