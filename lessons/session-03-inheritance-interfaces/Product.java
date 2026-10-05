// "abstract" means: you can never write "new Product(...)" directly.
// Product is now a shared BLUEPRINT OF A BLUEPRINT — it defines what every
// kind of product has in common, but forces every actual subclass
// (PhysicalProduct, DigitalProduct, ...) to fill in the parts that differ.
//
// "implements Comparable<Product>" is a separate promise: it says "this
// class can be put in order / sorted". Comparable is an INTERFACE built
// into Java itself — it doesn't give you any code for free, it just
// requires you to write a compareTo() method, and in exchange, tools like
// Arrays.sort() and Collections.sort() know how to use your class.
public abstract class Product implements Comparable<Product> {

    private String name;
    private double price;
    private int quantityInStock;

    public Product(String name, double price, int quantityInStock) {
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public String getName() {
        return name;
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

    public void sell(int units) {
        quantityInStock = quantityInStock - units;
    }

    // --- Abstract method ---
    // No body here at all — just a signature. Every subclass of Product
    // is REQUIRED to provide its own implementation, or it won't compile.
    // This is how we force "physical and digital products ship differently"
    // to actually be handled, instead of forgotten.
    public abstract String getShippingInfo();

    // --- Implementing the Comparable interface ---
    // compareTo returns negative if "this" should sort before "other",
    // positive if after, zero if equal. Here we sort by price, ascending.
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
