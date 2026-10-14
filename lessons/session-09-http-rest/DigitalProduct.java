public class DigitalProduct extends Product {

    private String downloadUrl;

    public DigitalProduct(String name, double price, int quantityInStock, String downloadUrl) {
        super(name, price, quantityInStock);
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String getShippingInfo() {
        return "No shipping - instant download (" + downloadUrl + ")";
    }

    @Override
    public String getCategory() {
        return "Digital";
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }
}
