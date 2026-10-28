package unterricht;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Produkt {
    @XmlAttribute
    String id;

    @XmlElement
    String name;

    @XmlElement
    int price; //@XmlTransient: nicht mit in xml

    @XmlElement
    boolean inStock;

    Produkt() {}
    Produkt(String id, String name, int price, boolean inStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return String.format("%s: %s, %d, Auf Lager: %b", id, name, price, inStock);
    }
}
