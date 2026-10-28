import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//V.a. TCP/UDP
//Kein MultiThreading, nur ein Client
public class SimpleChatServer {
    static void main(String[] args) {
        //Öffnet den Port und wartet auf eingehende Verbindungen
        try (ServerSocket serverSocket = new ServerSocket(1234);
             BufferedReader konsole = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Warte auf Client ...");
            //Accept blockiert bis sich Client verbindet
            try (Socket client = serverSocket.accept();
                 //PrintWriter erstellt intern OutputStreamWriter (Bytes)
                 PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                 //InputStreamReader = Brücke von Bytes zu Zeichendaten
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                String empfangen;
                while((empfangen = in.readLine()) != null)  { //reagiert, wenn Daten vom Client ankommen
                    System.out.println("Server hat empfangen: " + empfangen);
                    if(empfangen.equalsIgnoreCase("exit")) {
                        System.out.println("Verbindung zu Client geschlossen");
                        break;
                    }
                    System.out.println("Server schreibt: ");
                    String antwort = konsole.readLine();
                    out.println(antwort);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
