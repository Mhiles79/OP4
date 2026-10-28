import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Bildkopie {
    public static void main(String[] args) {
        try {
            //Files.copy wäre kürzer, hier aber byteweises Kopieren
            Files.createDirectories(Path.of("resources"));
            Path quelle = Path.of("resources/pipeline.png");
            Path ziel = Path.of("resources/pipeline_kopie.png");
            byte[] puffer = new byte[8192];
            int gesamt = 0;
            try(FileInputStream fis = new FileInputStream(quelle.toFile());
                FileOutputStream fos = new FileOutputStream(ziel.toFile())) {
                int gelesen; //Anzahl gelesener Bytes
                while((gelesen = fis.read(puffer)) != -1) {
                    fos.write(puffer, 0, gelesen);
                    gesamt += gelesen;
                }
                fos.flush();
            }
            System.out.printf("Kopiert: %d Bytes", gesamt);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
