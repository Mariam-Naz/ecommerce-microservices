public class PhysicalProduct extends Product {

    private double weightKg;

    public PhysicalProduct(String name, double price, int quantityInStock, double weightKg) {
        super(name, price, quantityInStock);
        this.weightKg = weightKg;
    }

    @Override
    public String getShippingInfo() {
        double shippingCost = weightKg * 2.50;
        return "Ships (" + weightKg + "kg, ~$" + shippingCost + " shipping)";
    }
}
