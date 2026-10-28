import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Server {
  public static void main(String[] args) {
        try {
            ServerSocket serverSocket= new ServerSocket(3333);
            System.out.println("Warte ...");
            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {


                URL url = Server.class.getResource("/termin.txt");
                if (url == null) {
                    throw new IOException("Termin konnte nicht gefunden werden");
                }
                String termin = Files.readString(Path.of(url.toURI())); //resources/termin.txt
                writer.println(termin);
                System.out.println("Termin gesendet");

                String kundenummer = reader.readLine();
                if (kundenummer.equals("007")) {
                    System.out.println("Termin bestätigt");
                } else {
                    System.out.println("Falsche Kundennummer");
                }
            }

        } catch (IOException | RuntimeException | java.net.URISyntaxException e) {
            System.err.println(e.getMessage());
        }
    }
}
