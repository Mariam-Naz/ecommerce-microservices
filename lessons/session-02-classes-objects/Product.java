public class Product {

    // --- Fields (also called instance variables) ---
    // 'private' means only code inside THIS class can touch these directly.
    // This is "encapsulation": the object controls access to its own data.
    private String name;
    private double price;
    private int quantityInStock;
    private boolean available;

    // --- Constructor ---
    // This runs whenever someone writes "new Product(...)".
    // It's how an object gets its initial values.
    public Product(String name, double price, int quantityInStock, boolean available) {
        this.name = name;                       // "this.name" = the field
        this.price = price;                     // "name" (no this.) = the parameter
        this.quantityInStock = quantityInStock;
        this.available = available;
    }

    // --- Getters ---
    // Since the fields are private, this is how outside code reads them.
    // We're not adding setters yet on purpose — we'll talk about why
    // (immutability, controlled mutation) when we revisit this class later.
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public boolean isAvailable() {
        return available;
    }

    // --- Behavior ---
    // A class isn't just data — it can also define what a Product can DO.
    public double totalInventoryValue() {
        return price * quantityInStock;
    }

    public boolean isLowStock() {
        return quantityInStock < 10;
    }

    public void sell(int units) {
        quantityInStock = quantityInStock - units;
    }

    // --- toString ---
    // Java calls this automatically when you print an object.
    // Without it, System.out.println(product) would print something
    // useless like "Product@1b6d3586". Try removing this method and see.
    @Override
    public String toString() {
        return name + " | $" + price + " | stock: " + quantityInStock + " | available: " + available;
    }
}
