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

    // Board minimaxMove(Board b) {
    //     Path next = b.next();

    // }

    private int randomInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

}



