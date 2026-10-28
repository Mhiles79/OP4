import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static void main(String[] args) {
        try (Socket clientSocket = new Socket("127.0.0.1", 3333);
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            System.out.println("Verbindung hergestellt ");
            do {
                String termin = reader.readLine();
                System.out.println(termin);
            } while (reader.ready());
            System.out.println("Mit Kundennummer bestätigen: ");
            String kdnr = new Scanner(System.in).nextLine();
            writer.println(kdnr);
            System.out.println(reader.readLine());

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
