public class DigitalProdukt extends Produkt {
    String downloadUrl;
    int groesseMB;

    public DigitalProdukt() {}
    public DigitalProdukt(String id, String name, int price, boolean inStock,
                          String downloadUrl, int groesseMB) {
        super(id, name, price, inStock);
        this.downloadUrl = downloadUrl;
        this.groesseMB = groesseMB;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getGroesseMB() {
        return groesseMB;
    }

    public void setGroesseMB(int groesseMB) {
        this.groesseMB = groesseMB;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", %s, %d MB", downloadUrl, groesseMB);
    }
}
