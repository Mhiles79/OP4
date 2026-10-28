import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        try (Socket socket = new Socket("127.0.0.1", 3333);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            // Auftrag vom Server empfangen
            System.out.println("=== Auftrag erhalten ===");
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                System.out.println(line);
                if (!reader.ready()) break;
            }

            // Code eingeben
            System.out.print("\nBitte geben Sie Ihren Agenten-Code ein: ");
            String code = console.readLine();
            writer.println(code);

            // Antwort vom Server
            String antwort = reader.readLine();
            System.out.println("\nServer: " + antwort);

        } catch (IOException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }
}
