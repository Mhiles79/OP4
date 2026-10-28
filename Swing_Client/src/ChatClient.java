import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//Export-Jar: Projektstruktur > Artifact > New > Jar > from modules with dependencies
//Danach Build > Build Artifacts
public class ChatClient extends JFrame {
    private JTextArea txtChat;
    private JTextField txtEingabe;
    private PrintWriter out;
    private String benutzername;
    private final List<Nachricht> lstNachrichten = new ArrayList<>();
    private static final File PROJEKTORDNER = new File(System.getProperty("user.dir"));

    public ChatClient(String benutzername) {
        super("Chat: " + benutzername);
        this.benutzername = benutzername;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(480, 380);
        setJMenuBar(erstelleMenue());

        txtChat = new JTextArea();
        txtChat.setEditable(false);
        //Automatischer Zeilenumbruch
        txtChat.setLineWrap(true);

        txtEingabe = new JTextField();
        JButton btnSenden = new JButton("Senden");

        txtEingabe.addActionListener(e -> senden());
        btnSenden.addActionListener(e -> senden());

        JPanel unten = new JPanel(new BorderLayout());
        unten.add(txtEingabe, BorderLayout.CENTER);
        unten.add(btnSenden, BorderLayout.EAST);

        add(new JScrollPane(txtChat), BorderLayout.CENTER);
        add(unten, BorderLayout.SOUTH);

        setVisible(true);
        txtEingabe.requestFocus();
        verbinden();
    }

    //Aufgabe: Fügt bitte Speichern/Öffnen Items ActionListener hinzu
    //mit Methoden, die die Nachrichten im Chat als Json im Resources-Ordner
    //speichern. 'Laden' lädt den ganzen Chatverlauf wieder aus der Json (GSON oder andere)
    private JMenuBar erstelleMenue() {
        JMenuBar menuBar = new JMenuBar();
        JMenu mnuDatei = new JMenu("Datei");
        JMenuItem miSpeichern = new JMenuItem("Speichern");
        JMenuItem miOeffnen = new JMenuItem("Öffnen");
        mnuDatei.add(miSpeichern);
        mnuDatei.add(miOeffnen);
        menuBar.add(mnuDatei);
        return menuBar;
    }

    private void verbinden() {
        try {
            Socket socket = new Socket("127.0.0.1", 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Thread thEmpfang = new Thread(() -> empfangen(in));
            thEmpfang.setDaemon(true);
            thEmpfang.start();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Verbindungsfehler: " + ex.getMessage());
            System.exit(1);
        }
    }

    private void senden() {
        String text = txtEingabe.getText().trim();
        if(text.isBlank()) {
            return;
        }
        out.println(benutzername + ";" + text);
        txtEingabe.setText("");
    }

    private void empfangen(BufferedReader in) {
        try {
            String zeile;
            while ((zeile = in.readLine()) != null) {
                String[] teile = zeile.split(";");
                if(teile.length == 2) {
                    Nachricht msg = new Nachricht(teile[0], teile[1]);
                    //Wenn aus eigenem Thread Zugriff auf Swing-Elemente (= EDT Thread)
                    SwingUtilities.invokeLater(() -> nachrichtAnzeigen(msg));
                }
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> nachrichtAnzeigen(new Nachricht("System", e.getMessage())));
        }
    }

    private void nachrichtAnzeigen(Nachricht msg) {
        lstNachrichten.add(msg);
        txtChat.append(msg + "\n");
    }

    public static void main(String[] args) {
        //EDT-Thread: Zugriff auf Swing-Elemente in eigenem Thread
        //PLAIN_MESSAGE: kein Icon
        SwingUtilities.invokeLater(() -> {
            String name = JOptionPane.showInputDialog(null,
                    "Benutzername: ", "Chat", JOptionPane.PLAIN_MESSAGE);
            if(name == null || name.isBlank()) {
                System.exit(0);
            }
            new ChatClient(name);
        });
    }
}
