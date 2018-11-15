import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPlayer extends Player implements java.io.Serializable {

    public RandomPlayer(String l) {
        label = l;
    }

    Board move(Board b) {
        Path next = b.next();
        return next.get(randomInt(next.size()));
    }

    private int randomInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

}



