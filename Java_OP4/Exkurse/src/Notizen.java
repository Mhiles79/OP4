import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Notizen {
    private static final Path NOTIZEN_ORDNER = Path.of("resources/notizen");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH:mm:ss");

    static void main(String[] args) {
        try {
            Files.createDirectories(NOTIZEN_ORDNER);
            speichern("Streams lernen");
            Thread.sleep(1000);
            speichern("Einkaufen");
            speichern("Children");
            auflisten();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    private static void auflisten() throws IOException{

        try(var stream = Files.list(NOTIZEN_ORDNER)) {
            stream
                    .forEach(System.out::println);

        }


    }

    private static void speichern() throws IOException {
        String dateiname = LocalDate.now().format(FORMATTER) + ".txt";
        Path datei = NOTIZEN_ORDNER.resolve(dateiname);
        Files.writeString(datei, inhalt);
    }
}