import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.List;

public class Jackson_Vererbung {
    static void main(String[] args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            ProduktListe produkte = new ProduktListe(List.of(
                new Produkt("P1", "Java Buch", 35, true),
                new Produkt("P2", "XML Buch", 25, false),
                new Produkt("P3", "XSL Buch", 14, true),
                new DigitalProdukt("P4", "EBook", 5, true, "https://irgendwo.de", 12),
                new DigitalProdukt("P5", "Game", 65, false, "https://irgendwas.de", 120)
            ));
            File json_file = new File("resources/vererbung_jackson.json");
            mapper.writeValue(json_file, produkte);

            //Deserialisierung
            ProduktListe geladen = mapper.readValue(json_file, ProduktListe.class);
            //Nur digitale Produkte auf Lager
            geladen.produkte.stream()
                .filter(p -> p.isInStock() && p instanceof DigitalProdukt)
                .forEach(System.out::println);
            //Nur physische Produkte deren Preis > 30 ist
            geladen.produkte.stream()
                .filter(p -> p.getPrice() > 30 && !(p instanceof DigitalProdukt))
                .forEach(System.out::println);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
