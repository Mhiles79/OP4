package unterricht;

import jakarta.xml.bind.*;

import java.io.File;
import java.util.List;

//Library from maven: com.sun.xml.bind:jaxb-impl:4.0.4
public class Serialisierung_Produkte {
    public static void main(String[] args) {
        ProduktListe produkte = new ProduktListe(List.of(
                new Produkt("P1", "Java Buch", 35, true),
                new Produkt("P2", "XML Buch", 25, false),
                new Produkt("P3", "XSL Buch", 14, true)
        ));
        try {
            File xml_file = new File("resources/products_jaxb.xml");
            JAXBContext context = JAXBContext.newInstance(ProduktListe.class);
            //Serialisieren
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(produkte, xml_file);
            //marshaller.marshal(produkte, System.out);

            //Deserialisieren
            Unmarshaller unmarshaller = context.createUnmarshaller();
            ProduktListe geladen = (ProduktListe) unmarshaller.unmarshal(xml_file);
            geladen.produkte.forEach(System.out::println);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
