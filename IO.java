import java.util.Scanner;

public class IO implements java.io.Serializable {
    private String response;

    IO() {
        response = "NO_RESPONSE_YET";
    }

    public String getInput() {
        return response;
    }

    public void log(String x) {
        System.out.println(x);
    }

    public void output(String x) {
        System.out.print(x);
    }

    public void prompt(String p) {
        Scanner in = new Scanner(System.in);

        System.out.println(p);
        System.out.print("\t-> ");
        response = in.nextLine();
    }

    public void prompt() {
        Scanner in = new Scanner(System.in);

        System.out.print("\t-> ");
        response = in.nextLine();
    }
}
