// "extends Product" means PhysicalProduct IS A Product — it automatically
// gets name, price, quantityInStock, totalInventoryValue(), sell(), etc.
// for free. We only write what's NEW or DIFFERENT here.
public class PhysicalProduct extends Product {

    private double weightKg;

    public PhysicalProduct(String name, double price, int quantityInStock, double weightKg) {
        // "super(...)" calls the Product constructor above to set up the
        // shared fields. Every subclass constructor must do this — Java
        // even inserts it automatically if you forget and there's a
        // no-arg constructor available, but here Product has no such
        // constructor, so we must call it explicitly.
        super(name, price, quantityInStock);
        this.weightKg = weightKg;
    }

    // This is what Product's "abstract String getShippingInfo();" demanded.
    // Without this method, PhysicalProduct wouldn't compile at all.
    @Override
    public String getShippingInfo() {
        double shippingCost = weightKg * 2.50; // simple flat rate per kg
        return "Ships (" + weightKg + "kg, ~$" + shippingCost + " shipping)";
    }
}
