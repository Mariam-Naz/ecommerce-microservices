package com.shopsphere.catalog;

public class CatalogApp {

    public static void main(String[] args) {

        Catalog catalog = new Catalog();
        catalog.addProduct(new PhysicalProduct("Wireless Mouse", 19.99, 42, 0.15));
        catalog.addProduct(new PhysicalProduct("4K Monitor", 399.99, 8, 6.2));
        catalog.addProduct(new DigitalProduct("Java Fundamentals eBook", 14.99, 999,
                "https://shopsphere.example/downloads/java-fundamentals"));
        catalog.addProduct(new SubscriptionProduct("Netflix", 40.0, 1, 30));

        System.out.println("--- Original catalog (in memory) ---");
        for (Product p : catalog.allProducts()) {
            System.out.println(p);
        }

        CatalogJsonStorage storage = new CatalogJsonStorage();
        String fileName = "catalog.json";

        storage.save(catalog, fileName);
        System.out.println("\nSaved catalog to " + fileName + " as real JSON - open it in a text editor.");

        Catalog reloaded = storage.load(fileName);

        System.out.println("\n--- Reloaded catalog (parsed back from JSON) ---");
        for (Product p : reloaded.allProducts()) {
            System.out.println(p);
        }

        System.out.println("\nSizes match: " + (catalog.size() == reloaded.size()));
    }
}
