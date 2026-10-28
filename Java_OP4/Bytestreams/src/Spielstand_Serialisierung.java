import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Spielstand_Serialisierung {
    public static void main(String[] args) {
        try {
            Files.createDirectories(Path.of("resources"));
            List<Spieler> spieler = Arrays.asList(
                new Spieler("Homer", 3000, 3, "1. Sieg", "Meister"),
                new Spieler("Marge", 4000, 4, "1. Pokal"),
                new Spieler("Lisa", 7000, 5, "1. Endgegner", "Meisterin")
            );
            speichern_ObjectStream(spieler);
            laden_ObjectStream();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void speichern_ObjectStream(List<Spieler> spieler) throws IOException {
        Path datei = Path.of("resources/spielstand.ser");
        try (ObjectOutputStream oos = new ObjectOutputStream(
            new BufferedOutputStream(new FileOutputStream(datei.toFile()))
        )) {
            //Datenklasse muss Serializable implementieren
            oos.writeObject(spieler);
        }
    }

    //Annotation unterdrücken: denn ich weiß, dass mein Cast richtig ist
    @SuppressWarnings("unchecked")
    private static void laden_ObjectStream() throws IOException, ClassNotFoundException {
        Path datei = Path.of("resources/spielstand.ser");
        List<Spieler> geladene_spieler;
        try (ObjectInputStream ois = new ObjectInputStream(
            new BufferedInputStream(new FileInputStream(datei.toFile()))
        )) {
            //Deserialisierung
            geladene_spieler = (List<Spieler>) ois.readObject();
        }
        geladene_spieler.forEach(s -> System.out.println(s));
    }
}
