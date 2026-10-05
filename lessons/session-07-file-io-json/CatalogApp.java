public class CatalogApp {

    public static void main(String[] args) {

        Catalog catalog = new Catalog();
        catalog.addProduct(new PhysicalProduct("Wireless Mouse", 19.99, 42, 0.15));
        catalog.addProduct(new PhysicalProduct("4K Monitor", 399.99, 8, 6.2));
        catalog.addProduct(new DigitalProduct("Java Fundamentals eBook", 14.99, 999,
                "https://shopsphere.example/downloads/java-fundamentals"));
        catalog.addProduct(new SubscriptionProduct("Premium Support", 99.99, 100, 30));

        System.out.println("--- Original catalog (in memory) ---");
        for (Product p : catalog.allProducts()) {
            System.out.println(p);
        }

        CatalogFileStorage storage = new CatalogFileStorage();
        String fileName = "catalog.txt";

        storage.save(catalog, fileName);
        System.out.println("\nSaved catalog to " + fileName
                + " - open that file in a text editor and look at it.");

        // Simulate the program restarting: a BRAND NEW, empty Catalog
        // object, with nothing in memory at all.
        Catalog reloaded = storage.load(fileName);

        System.out.println("\n--- Reloaded catalog (read back from disk) ---");
        for (Product p : reloaded.allProducts()) {
            System.out.println(p);
        }

        System.out.println("\nSizes match: " + (catalog.size() == reloaded.size()));
    }
}
