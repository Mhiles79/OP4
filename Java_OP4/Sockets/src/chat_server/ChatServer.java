package chat_server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//Verwendet Threads, damit parallele Verarbeitung der Nachrichten
public class ChatServer {
    private static List<PrintWriter> clients = new CopyOnWriteArrayList<>(); //threadsichere Liste

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(4444)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Neuer Client: " + clientSocket.getRemoteSocketAddress());
                //Thread will entweder Methode oder Klasse, die run implementiert
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.setDaemon(true); //Wenn Hauptthread beendet, stoppt JVM Hintergrundthreads
                clientThread.start();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    static void broadcast(String msg) {
        for(PrintWriter pw: clients) {
            pw.println(msg);
        }
    }

    //Es kommt Nachricht rein, aufbereiten und broadcasten
    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        //Zentrale Aufgabe: Lese Nachricht, verteile an Alle
        //PrintWriter pw nur zum sauberen Entfernen aus Client-Liste (schreibt nix)
        @Override
        public void run() {
            try(PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                out = pw;
                clients.add(out);
                String nachricht;
                while ((nachricht = br.readLine()) != null) { //null: Client weg
                    //"user;nachricht"
                    String[] teile = nachricht.split(";");
                    System.out.println("User: " + teile[0] + ": " + teile[1]);
                    broadcast(nachricht);
                }
            } catch (Exception e) {
                System.out.println("Client getrennt: " + socket.getRemoteSocketAddress());
            } finally {
                clients.remove(out);
                System.out.println("Noch " + clients.size() + " Clients");
            }
        }
    }
}
