import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GSON_Produkte {
    public static void main(String[] args) {
        try {
            var produkte = List.of(
                    new Produkt("P1", "Java Buch", 35, true),
                    new Produkt("P2", "XML Buch", 25, false),
                    new Produkt("P3", "XSL Buch", 14, true)
            );

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(produkte);
            Files.writeString(Path.of("resources/produkte_gson.json"), json);
            //Deserialisieren
            Produkt[] geladen = gson.fromJson(json, Produkt[].class);
            for(Produkt p: geladen) {
                System.out.println(p);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
