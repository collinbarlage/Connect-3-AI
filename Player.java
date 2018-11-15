import java.io.*;

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
}



