public class Playground {

    public static void main(String[] args) {

        // --- Variables and primitive types ---
        int quantityInStock = 42;          // whole numbers
        double price = 19.99;              // decimal numbers
        boolean isAvailable = true;        // true/false
        char grade = 'A';                  // a single character
        String productName = "Wireless Mouse"; // text (not a primitive, but used constantly)

        System.out.println("Product: " + productName);
        System.out.println("Price: $" + price);
        System.out.println("In stock: " + quantityInStock);
        System.out.println("Available: " + isAvailable);

        // --- Operators ---
        double totalValue = price * quantityInStock;
        System.out.println("Total inventory value: $" + totalValue);

        int newStock = quantityInStock - 5; // 5 units sold
        System.out.println("Stock after selling 5: " + newStock);

        boolean lowStock = newStock < 10;
        System.out.println("Is stock low? " + lowStock);

        // --- Additional product: Gaming Mouse ---
        int quantityInStockMouse = 50;
        double priceMouse = 29.99;
        boolean isAvailableMouse = false;
        char gradeMouse = 'B';
        String productNameMouse = "Gaming Mouse";
        System.out.println("Mouse Product: " + productNameMouse);
        System.out.println("Mouse Price: $" + priceMouse);
        System.out.println("Mouse In stock: " + quantityInStockMouse);
        System.out.println("Mouse Available: " + isAvailableMouse);

        // --- Operators for mouse product ---
        double totalValueMouse = priceMouse * quantityInStockMouse;
        System.out.println("Mouse Total inventory value: $" + totalValueMouse);

        int newStockMouse = quantityInStockMouse - 10; // 10 units sold
        System.out.println("Mouse Stock after selling 10: " + newStockMouse);

        boolean lowStockMouse = newStockMouse < 10;
        System.out.println("Is mouse stock low? " + lowStockMouse);
    }
}
