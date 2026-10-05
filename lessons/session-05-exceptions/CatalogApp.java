public class CatalogApp {

    public static void main(String[] args) {

        Catalog catalog = new Catalog();
        catalog.addProduct(new PhysicalProduct("Wireless Mouse", 19.99, 42, 0.15));
        catalog.addProduct(new PhysicalProduct("4K Monitor", 399.99, 8, 6.2));
        catalog.addProduct(new DigitalProduct("Java Fundamentals eBook", 14.99, 999,
                "https://shopsphere.example/downloads/java-fundamentals"));

        // --- try / catch: handling an exception gracefully ---
        // Code inside "try" runs normally until something throws. The
        // instant a throw happens, Java jumps straight to a matching
        // "catch" block, skipping any remaining lines in the try block.
        System.out.println("--- Attempting to sell more monitors than in stock ---");
        try {
            Product monitor = catalog.findByName("4K Monitor");
            monitor.sell(100); // only 8 in stock - this will throw
            System.out.println("This line never runs.");
        } catch (InsufficientStockException e) {
            // e.getMessage() returns the string we built in the
            // exception's constructor with super(...).
            System.out.println("Handled error: " + e.getMessage());
        }

        System.out.println("\n--- Looking up a product that doesn't exist ---");
        try {
            Product ghost = catalog.findByName("Nonexistent Product");
            System.out.println("Found: " + ghost); // never reached
        } catch (ProductNotFoundException e) {
            System.out.println("Handled error: " + e.getMessage());
        }

        try{
            Product negativePrice = new PhysicalProduct("Negative Price Product", -10.00, 5, 0.5);
        }catch(IllegalArgumentException e){
            System.out.println("Negative Price: " + e.getMessage());
        }
        // --- Catching multiple exception types ---
        // A single try can have multiple catch blocks. Java checks them
        // in order and runs the first one that matches the thrown
        // exception's type.
        System.out.println("\n--- Multiple catch blocks ---");
        String[] productsToSell = { "4K Monitor", "Nonexistent Product", "Wireless Mouse" };
        for (String name : productsToSell) {
            try {
                Product p = catalog.findByName(name);
                p.sell(3);
                System.out.println("Sold 3 units of " + name + ". Remaining: " + p.getQuantityInStock());
            } catch (ProductNotFoundException e) {
                System.out.println("Skipping - " + e.getMessage());
            } catch (InsufficientStockException e) {
                System.out.println("Skipping - " + e.getMessage());
            }
        }

        // --- finally: always runs, exception or not ---
        // This is for cleanup code - closing a file, a database
        // connection, a network socket - that MUST happen whether the
        // try block succeeded or threw. We don't have real resources to
        // close yet (that comes with JDBC/files later), but the
        // mechanics matter now.
        System.out.println("\n--- finally block demo ---");
        try {
            System.out.println("Doing risky work...");
            catalog.findByName("Nonexistent Product");
        } catch (ProductNotFoundException e) {
            System.out.println("Caught: " + e.getMessage());
        } finally {
            System.out.println("This always runs - success or failure.");
        }

        System.out.println("\nProgram finished normally - no uncaught exception crashed it.");
    }
}
