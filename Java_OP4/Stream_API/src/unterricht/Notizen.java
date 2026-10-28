package unterricht;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Notizen {
    private static final Path NOTIZEN_ORDNER = Path.of("resources/notizen");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");

    public static void main(String[] args) {
        try {
            Files.createDirectories(NOTIZEN_ORDNER);
            /*
            speichern("Streams lernen");
            Thread.sleep(1000);
            speichern("Einkaufen");
            Thread.sleep(1000);
            speichern("Chillen");
            */
            auflisten();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void auflisten() throws IOException {
        //Liefert nicht wie File.listFiles File[] zurück, sondern Stream
        try(var stream = Files.list(NOTIZEN_ORDNER)) {
            stream
                .sorted(Comparator.reverseOrder())
                .forEach(f -> {
                   try {
                       System.out.println(f.getFileName() + ": " + Files.readString(f));
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               });
        }
    }

    private static void filtern() throws IOException {
        try (var stream = Files.list(NOTIZEN_ORDNER)) {
            stream
                .filter(f -> f.getFileName().toString().endsWith(".txt"))
                .forEach(f -> {
                    try {
                        System.out.println(f.getFileName() + ": " + Files.readString(f));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        }
    }
    private static void speichern(String inhalt) throws IOException {
        String dateiname = LocalDateTime.now().format(FORMATTER) + ".txt";
        Path datei = NOTIZEN_ORDNER.resolve(dateiname);
        Files.writeString(datei, inhalt);
    }
}
