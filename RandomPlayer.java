import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPlayer extends Player implements java.io.Serializable {

    Board move(Board b) {
        Path next = b.next();
        return next.get(randomInt(next.size()));
    }

}



