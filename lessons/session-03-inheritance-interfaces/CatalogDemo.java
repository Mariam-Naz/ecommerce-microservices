import java.util.Arrays;

public class CatalogDemo {

    public static void main(String[] args) {

        // Note the declared type on the left: "Product". Even though a
        // PhysicalProduct and a DigitalProduct are genuinely different
        // classes with different fields, they can BOTH be stored as
        // "Product" references, because they both extend Product.
        // This is POLYMORPHISM: one array, multiple real shapes of object.
        Product mouse = new PhysicalProduct("Wireless Mouse", 19.99, 42, 0.15);
        Product monitor = new PhysicalProduct("4K Monitor", 399.99, 8, 6.2);
        Product ebook = new DigitalProduct("Java Fundamentals eBook", 14.99, 999,
                "https://shopsphere.example/downloads/java-fundamentals");
        Product subscription = new SubscriptionProduct("Netflix", 10.00, 1000, 30);

        Product[] catalog = { mouse, monitor, ebook, subscription };

        System.out.println("--- Catalog (unsorted) ---");
        for (Product p : catalog) {
            // Even though this loop doesn't know or care whether "p" is
            // physical or digital, calling p.getShippingInfo() runs the
            // RIGHT version automatically, because Java looks at the
            // object's ACTUAL class at runtime, not the "Product" label
            // we're using to refer to it. This is called dynamic dispatch.
            System.out.println(p);
        }

        // Because Product implements Comparable<Product>, Arrays.sort
        // already knows how to put these in order — no extra code needed
        // here at all. That's the payoff of implementing an interface.
        Arrays.sort(catalog);

        System.out.println("\n--- Catalog (sorted by price) ---");
        for (Product p : catalog) {
            System.out.println(p);
        }
    }
}
