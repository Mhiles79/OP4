import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.annotations.SerializedName;

//Muss Java-Bean sein (Leerer Konstruktor, getter/setter)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
     @JsonSubTypes.Type(value = Produkt.class, name = "physisch"),
     @JsonSubTypes.Type(value = DigitalProdukt.class, name = "digital")
})
public class Produkt {
    private String id;
    private String name;
    @SerializedName("Preis")
    private int price;
    @SerializedName("Im_Lager")
    private boolean inStock;

    public Produkt() {}
    public Produkt(String id, String name, int price, boolean inStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return String.format("%s: %s, %d, Auf Lager: %b", id, name, price, inStock);
    }
}
