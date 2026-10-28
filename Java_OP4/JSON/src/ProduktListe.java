import java.util.List;

//Muss Java-Bean sein (Leerer Konstruktor, getter/setter)
public class ProduktListe {
    List<Produkt> produkte;

    public List<Produkt> getProdukte() {
        return produkte;
    }

    public void setProdukte(List<Produkt> produkte) {
        this.produkte = produkte;
    }

    ProduktListe() {}
    ProduktListe(List<Produkt> produkte) {
        this.produkte = produkte;
    }
}
