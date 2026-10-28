import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Server {
    public static void main(String[] args) {
        System.out.println("Server gestartet. Warte auf Geheimagenten...");

        try (ServerSocket serverSocket = new ServerSocket(3333)) {

            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                // Auftrag laden
                URL url = Server.class.getResource("/Auftrag.txt");
                if (url == null) {
                    throw new IllegalStateException("Auftrag.txt nicht gefunden!");
                }

                String auftrag = Files.readString(Path.of(url.toURI()), StandardCharsets.UTF_8);

                // Auftrag an Client senden
                writer.println(auftrag);
                System.out.println("Auftrag an Agent gesendet.");

                // Code vom Client empfangen
                String code = reader.readLine();

                if ("008".equals(code)) {
                    writer.println("Code korrekt. Viel Erfolg, Agent!");
                    System.out.println("Agent authentifiziert.");
                } else {
                    writer.println("Falscher Code. Zugriff verweigert.");
                    System.out.println("Falscher Code erhalten.");
                }

            }

        } catch (Exception e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }
}
