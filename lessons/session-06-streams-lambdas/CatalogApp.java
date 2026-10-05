import java.util.List;
import java.util.Map;

public class CatalogApp {

    public static void main(String[] args) {

        Catalog catalog = new Catalog();
        catalog.addProduct(new PhysicalProduct("Wireless Mouse", 19.99, 42, 0.15));
        catalog.addProduct(new PhysicalProduct("4K Monitor", 399.99, 8, 6.2));
        catalog.addProduct(new PhysicalProduct("USB-C Hub", 24.99, 5, 0.2));
        catalog.addProduct(new DigitalProduct("Java Fundamentals eBook", 14.99, 999,
                "https://shopsphere.example/downloads/java-fundamentals"));

        System.out.println("--- Low stock (old loop way) ---");
        for (Product p : catalog.lowStockProductsOldWay()) {
            System.out.println(p);
        }

        System.out.println("\n--- Low stock (stream way, same result) ---");
        for (Product p : catalog.lowStockProducts()) {
            System.out.println(p);
        }

        System.out.println("\n--- Low stock (method reference version) ---");
        for (Product p : catalog.lowStockProductsMethodRef()) {
            System.out.println(p);
        }

        System.out.println("\n--- Most expensive product ---");
        System.out.println(catalog.findMostExpensive());

        System.out.println("\n--- Grouped by category ---");
        Map<String, List<Product>> byCategory = catalog.groupByCategory();
        for (String category : byCategory.keySet()) {
            System.out.println(category + ":");
            for (Product p : byCategory.get(category)) {
                System.out.println("  " + p.getName());
            }
        }

        System.out.println("\n--- All product names, sorted alphabetically ---");
        System.out.println(catalog.allProductNamesSorted());

        System.out.println("\n--- Total catalog value ---");
        System.out.println("$" + catalog.totalCatalogValue());
        System.out.println("\n--- Products in price range ---");
        System.out.println(catalog.productsInPriceRange(10.0, 50.0));

        // --- A lambda used directly, without a Catalog method wrapping it ---
        // Runnable is a "functional interface" - an interface with exactly
        // one abstract method - which is what makes lambdas possible at
        // all. A lambda is really just a shorthand way of implementing a
        // functional interface's single method inline.
        Runnable task = () -> System.out.println("\nThis code was stored in a variable and run later.");
        task.run();
    }
}
