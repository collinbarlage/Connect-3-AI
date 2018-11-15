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

    Board move(Board b) {
        io.log("DEFAULT MOVE AHH");
        return b;
    }

    private int randomInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

}



