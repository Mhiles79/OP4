import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.List;

//JSON-Bibliotheken:
//GSON (Google), JSON-B (Jakarta), Jackson
//com.fasterxml.jackson.core:jackson-databind:2.20.2
public class Jackson_Produkte {
    static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            //Ausnahme ausschalten, falls leer
            //mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            ProduktListe produkte = new ProduktListe(List.of(
                new Produkt("P1", "Java Buch", 35, true),
                new Produkt("P2", "XML Buch", 25, false),
                new Produkt("P3", "XSL Buch", 14, true)
            ));
            File json_file = new File("resources/produkte_jackson.json");
            mapper.writeValue(json_file, produkte);
            //Deserialisieren
            ProduktListe geladen = mapper.readValue(json_file, ProduktListe.class);
            geladen.produkte.stream()
                .filter(p -> p.isInStock())
                .forEach(System.out::println);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
