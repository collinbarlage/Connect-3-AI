import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class Player implements java.io.Serializable {
    IO io = new IO();

    public String label;

    public Player() {
        // default does nothing
    }

    public Player(String l) {
        label = l;
    }

    Board randomMove(Board b) {
        Path next = b.next();
        return next.get(randomInt(next.size()));
    }

    Board minimaxMove(Board b) {
        long startTime = System.nanoTime();
        Path next = b.next();
        if(b.canWin()) {
            return b.block();
        }
        for(int i=0; i<next.size(); i++) {
            next.get(i).parentIndex = i;
        }
        Board r = next.get(b.minimaxSearch(next));
        long endTime = System.nanoTime();
        io.log("Duration of minimax move: " + (endTime - startTime));
        return r;
    }

    private int randomInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

}



