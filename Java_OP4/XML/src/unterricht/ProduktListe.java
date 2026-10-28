package unterricht;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD) //Felder direkt mappen (keine Getter)
public class ProduktListe {
    @XmlElement(name = "product")
    List<Produkt> produkte;

    ProduktListe() {} // will jaxb
    ProduktListe(List<Produkt> produkte) {
        this.produkte = produkte;
    }
}
