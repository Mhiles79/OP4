
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

//Reader/Writer arbeiten auf Zeichen-Ebene (16-Bit Unicode, UTF-8)
public class Reader_Writer {
    public static void main(String[] args) {
        try {
            //java.io
            Files.createDirectories(Path.of("resources"));
            Path datei = Path.of("resources/normal.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(datei.toFile(), StandardCharsets.UTF_8));
                 BufferedWriter bw = new BufferedWriter(new FileWriter("resources/gross.txt", StandardCharsets.UTF_8))) {
                String zeile;
                while((zeile = br.readLine()) != null) { //null = Dateiende
                    bw.write(zeile.toUpperCase());
                    bw.newLine(); //bw.write("\n"); weniger gut
                }
            }

            //java.nio
            String inhalt = Files.readString(datei, StandardCharsets.UTF_8);
            Files.writeString(Path.of("resources/gross_nio.txt"), inhalt.toUpperCase(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
