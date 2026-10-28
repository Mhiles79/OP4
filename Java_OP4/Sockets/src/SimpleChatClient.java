import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleChatClient {
    static void main(String[] args) {
        //Verbindung zu Server herstellen
        try (Socket server = new Socket("127.0.0.1", 1234);
             PrintWriter out = new PrintWriter(server.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            System.out.println("Verbunden mit Server ...");
            String eingabe;
            while (true) { // besser: in.readLine() != null
                System.out.println("Client schreibt: ");
                eingabe = scanner.nextLine();
                out.println(eingabe);
                if(eingabe.equalsIgnoreCase("exit")) {
                    break;
                }
                String antwort = in.readLine();
                System.out.println("Client hat empfangen: " + antwort);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
