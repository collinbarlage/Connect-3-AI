import java.io.*;

public class MinimaxAlphaBetaPlayer extends Player implements java.io.Serializable {

    Board move(Board b) {
        Path next = b.next();
        if(b.canWin()) {
            return b.block();
        }
        for(int i=0; i<next.size(); i++) {
            next.get(i).parentIndex = i;
        }
        return next.get(b.alphabetaSearch(next));
    }

}



