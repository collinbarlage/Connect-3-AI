import java.util.Vector;
import java.util.Collections;


public class Main {

    public static void main(String[] args) {
        IO io = new IO();

        // check for errors 
        if (args.length < 1) {
            io.log("YIKES you didn't give me a command argument");
            io.log("Try running something like  > sh run.sh command\n\n");
            return;
        }

        // get args
        String command = args[0];
        String input     = "";
        if (args.length > 1) { input = args[1]; }

        // create and load game board
        Board newBoard = new Board();  
        if(input == "") { input = "   |   |   |   "; }
		newBoard.load(input);

        // Path next;

        // execute command
        switch (command) {
            case "print": 
                newBoard.display();
                break;

            case "winner": // return True or False if xx car can gtfo
                io.log(newBoard.winner());
                break;

            // case "next": // display all boards of each attempt to move each car +-direction
            //     next = newBoard.next();
            //     next.print();
            //     break;

            // case "random": // display random path
            //     newBoard.random(10);
            //     break;

            // case "minimax": // Breadth first search
            //     next = newBoard.next();
            //     next.bfs(next);
            //     break;

            case "test": 
                newBoard.display();
                if(newBoard.canPlace(2)) {
                    newBoard.place("~",2);
                }
                newBoard.display();
        
                break;

            default:
                io.log("Erk, invalid command argument :(");
                break;
        }

        io.log("\n");
    }

}
