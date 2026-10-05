public abstract class Product implements Comparable<Product> {

    private String name;
    private double price;
    private int quantityInStock;

    public Product(String name, double price, int quantityInStock) {
        // Carried forward from the Session 5 exercise: reject bad data at
        // the moment of construction, before an invalid Product can exist.
        if (price < 0 || quantityInStock < 0) {
            throw new IllegalArgumentException(
                    "Price and quantity cannot be negative, got: " + price + ", " + quantityInStock);
        }
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        // A simple hook so streams have something more interesting than
        // price to group/filter by. Subclasses override this.
        return "General";
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public double totalInventoryValue() {
        return price * quantityInStock;
    }

    public boolean isLowStock() {
        return quantityInStock < 10;
    }

    // Previously this just did "quantityInStock = quantityInStock - units",
    // no matter what "units" was. Selling 100 units of a product with 8 in
    // stock would silently make quantityInStock negative — nonsense data,
    // and a bug that stays hidden until something downstream breaks
    // mysteriously. Now we FAIL LOUDLY AND IMMEDIATELY the moment invalid
    // state would be created. This is a core principle: validate at the
    // boundary, fail fast, and give a message specific enough to debug from.
    public void sell(int units) {
        if (units > quantityInStock) {
            throw new InsufficientStockException(name, units, quantityInStock);
        }
        if (units <= 0) {
            throw new IllegalArgumentException("Units to sell must be positive, got: " + units);
        }
        quantityInStock = quantityInStock - units;
    }

    public abstract String getShippingInfo();

    @Override
    public int compareTo(Product other) {
        return Double.compare(this.price, other.price);
    }

    @Override
    public String toString() {
        return name + " | $" + price + " | stock: " + quantityInStock
                + " | " + getShippingInfo();
    }
}
