package unterricht;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

//Alle relativen Pfade starten im "Working Directory" (Standard=Projektpfad)
//java.io.File (klassisch)
//java.nio.file.* (ab Java 7)
public class Files_Directories {
    public static void main(String[] args) {
        pfade();
        datei_operationen();
    }

    private static void pfade() {
        //relativ: ohne /, relativ zu Modul
        //absolut: mit /
        System.out.println("Working-Directory: " + System.getProperty("user.dir"));
        System.out.println("User-Home: " + System.getProperty("user.home"));
        System.out.println("Spezifischer-Trenner: " + File.separator);

        //Relativen Pfad bauen zu datei in ressources
        Path relativ_pfad = Path.of("resources", "test.txt");
        System.out.println(relativ_pfad);
        System.out.println(relativ_pfad.toAbsolutePath());

        //Path Operationen nutzen File.separator
        Path zusammengesetzt = Path.of("resources", "unterordner", "test.txt");
        System.out.println(zusammengesetzt);
        System.out.println("Elternordner: " + zusammengesetzt.getParent());
        System.out.println("Dateiname: " + zusammengesetzt.getFileName());
    }

    private static void datei_operationen() {
        try {
            //Kein Fehler, wenn schon da
            ordner_anlegen("resources");
            datei_erstellen_old("resources/test.txt");
            datei_erstellen_neu("resources/test_neu.txt");
            datei_kopieren("resources/test.txt", "resources/test_kopie.txt");
            ordner_anlegen("resources/archiv");
            datei_verschieben("resources/test.txt", "resources/archiv/test.txt");
            datei_loeschen("resources/test_kopie.txt");
            ordnerinhalt_auflisten("resources", "txt");
            laufwerke_auflisten();
            ordner_traversieren("resources", 0);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void ordner_traversieren(String pfad, int tiefe) {
        File ordner = new File(pfad);
        File[] eintraege = ordner.listFiles();
        if(eintraege == null) {
            return;
        }
        for(File e: eintraege) {
            String einrueckung = " ".repeat(tiefe);
            if(e.isDirectory()) {
                System.out.println(einrueckung + "[Ordner] " + e.getName());
                ordner_traversieren(pfad + File.separator + e.getName(), tiefe + 1);
            } else {
                System.out.println(einrueckung + "[Datei] " + e.getName());
            }
        }
    }

    private static void laufwerke_auflisten() {
        for (File f : File.listRoots()) {
            System.out.println(f);
        }
    }

    private static void ordnerinhalt_auflisten(String pfad, String endung) {
        System.out.println("+".repeat(20));
        System.out.println("Ordnerinhalt:");
        File[] dateien = new File(pfad).listFiles(f -> f.getName().endsWith("." + endung));
        if(dateien != null) {
            for(File f: dateien) {
                //Oder: if(f.isFile() && f.getName().endsWith("." + endung))
                if(f.isFile()) {
                    System.out.println(f.getName());
                }
            }
        }
    }

    private static void datei_loeschen(String pfad) {
        try {
            if(Files.deleteIfExists(Path.of(pfad))) {
                System.out.println("Gelöscht: " + pfad);
            } else {
                System.out.println("Nicht gefunden: " + pfad);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void datei_verschieben(String quelle, String ziel) throws IOException {
        Files.move(Path.of(quelle), Path.of(ziel), StandardCopyOption.REPLACE_EXISTING);
    }

    private static void datei_kopieren(String quelle, String ziel) throws IOException {
        Files.copy(Path.of(quelle), Path.of(ziel), StandardCopyOption.REPLACE_EXISTING);
    }

    private static void datei_erstellen_neu(String pfad) {
        //Variante 2 (java.nio)
        //Wenn Datei schon existiert > FileAlreadyExistsException,
        //ist Kindklasse von IOException
        try {
            Files.createFile(Path.of(pfad));
        } catch (IOException e) {
            System.err.println("Existiert schon: " + e.getMessage());
        }
    }

    private static void datei_erstellen_old(String pfad) throws IOException {
        //Variante 1 (java.io)
        File datei = new File(pfad);
        if(datei.createNewFile()) {
            System.out.println("Neu erstellt: " + datei.getName());
        } else {
            System.out.println("Existiert schon: " + datei.getName());
        }
    }

    private static void ordner_anlegen(String pfad) throws IOException {
        Files.createDirectories(Path.of(pfad));
    }
}
