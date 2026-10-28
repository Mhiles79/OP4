import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class JSONClient {
    static void main(String[] args) {
        try(Socket server = new Socket("127.0.0.1", 5555);
            PrintWriter out = new PrintWriter(server.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Id eingeben");
                String id = scanner.nextLine();
                if(id.equalsIgnoreCase("q")) {
                    break;
                }
                out.println(id);
                String antwort = in.readLine(); // wartet ...
                System.out.println("Antwort: " + antwort);
                Files.writeString(Path.of("resources/person.json"), antwort);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
