import java.util.Collections;
import java.util.List;

public class CatalogApp {

    public static void main(String[] args) {

        Catalog catalog = new Catalog();

        catalog.addProduct(new PhysicalProduct("Wireless Mouse", 19.99, 42, 0.15));
        catalog.addProduct(new PhysicalProduct("4K Monitor", 399.99, 8, 6.2));
        catalog.addProduct(new DigitalProduct("Java Fundamentals eBook", 14.99, 999,
                "https://shopsphere.example/downloads/java-fundamentals"));

        System.out.println("Catalog size: " + catalog.size());
        System.out.println("Total catalog value: $" + catalog.totalCatalogValue());

        // Adding a fourth product is trivial now — no array resizing,
        // no rewriting. This is the whole point of using a List.
        catalog.addProduct(new PhysicalProduct("USB-C Hub", 24.99, 5, 0.2));
        System.out.println("Catalog size after adding one more: " + catalog.size());

        System.out.println("\n--- Low stock products ---");
        List<Product> lowStock = catalog.lowStockProducts();
        for (Product p : lowStock) {
            System.out.println(p);
        }

        System.out.println("\n--- Lookup by name ---");
        Product found = catalog.findByName("4K Monitor");
        System.out.println("Found: " + found);

        Product missing = catalog.findByName("Nonexistent Product");
        System.out.println("Looking up a product that doesn't exist: " + missing);
        // Notice this prints "null" instead of crashing — but the moment
        // you try to call a method on "missing" (like missing.getPrice()),
        // it WILL crash with a NullPointerException. Try adding that line
        // yourself and see the exact error Java gives you.

        System.out.println("\n--- Removing a product ---");
        boolean removed = catalog.removeByName("Wireless Mouse");
        System.out.println("Removed Wireless Mouse? " + removed);
        System.out.println("Catalog size now: " + catalog.size());

        System.out.println("\n--- Most expensive product is ---" + catalog.findMostExpensive());
        System.out.println("\n--- Sorted full catalog (using Comparable from Session 3) ---");
        List<Product> all = catalog.allProducts();
        Collections.sort(all); // Collections.sort works on any List<T> where T is Comparable
        for (Product p : all) {
            System.out.println(p);
        }
    }
}
