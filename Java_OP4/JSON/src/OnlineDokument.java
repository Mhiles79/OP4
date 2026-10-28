public class OnlineDokument  extends  Produkt{
    String downloadUrl;
    int groesseMB;

    public OnlineDokument() {}
    public OnlineDokument(String id, String name, int price, boolean inStock, String downloadUrl, int groesseMB) {
        super(id, name, price, inStock);
        this.downloadUrl = downloadUrl;
        this.groesseMB = groesseMB;
    }
}
