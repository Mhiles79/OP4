import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Data-Input bzw. Output-Streams: kompaktes binäres Format (byteweise)
public class Spielstand_DataStream {
    public static void main(String[] args) {
        try {
            Files.createDirectories(Path.of("resources"));
            List<Spieler> spieler = Arrays.asList(
                new Spieler("Homer", 3000, 3, "1. Sieg", "Meister"),
                new Spieler("Marge", 4000, 4, "1. Pokal"),
                new Spieler("Lisa", 7000, 5, "1. Endgegner", "Meisterin")
            );
            speichern_DataStream(spieler);
            laden_DataStream();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //Pipeline:
    // 1. FileStreams haben nur read/write Methode
    // 2. BufferedStream fügt Puffern hinzu
    // 3. DataStream fügt Methoden zum Lesen/Schreiben primitiver Datentypen hinzu
    // Ganz wichtig hier: Reihenfolge Schreiben = Lesen !!!
    private static void speichern_DataStream(List<Spieler> spieler) throws IOException {
        Path datei = Path.of("resources/spielstand.bin");
        try (DataOutputStream dos = new DataOutputStream(
            new BufferedOutputStream(new FileOutputStream(datei.toFile()))
        )) {
            dos.writeInt(spieler.size());
            for(Spieler s: spieler) {
                dos.writeUTF(s.name);
                dos.writeInt(s.punkte);
                dos.writeInt(s.level);
                dos.writeInt(s.erfolge.size());
                for(String e: s.erfolge) {
                    dos.writeUTF(e);
                }
            }
        }
    }

    private static void laden_DataStream() throws IOException {
        Path datei = Path.of("resources/spielstand.bin");
        List<Spieler> geladene_spieler = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(
            new BufferedInputStream(new FileInputStream(datei.toFile()))
        )) {
            int anzahl = dis.readInt();
            for(int i=0; i<anzahl; i++) {
                String name = dis.readUTF();
                int punkte = dis.readInt();
                int level = dis.readInt();
                int anzahl_erfolge = dis.readInt();
                List<String> erfolge = new ArrayList<>();
                for(int j = 0; j<anzahl_erfolge; j++) {
                    erfolge.add(dis.readUTF());
                }
                geladene_spieler.add(new Spieler(name, punkte, level, Arrays.toString(erfolge.toArray())));
            }
        }
        geladene_spieler.forEach(System.out::println);
    }
}
