
public class ProductDemo {

    public static void main(String[] args) {

        // Each "new Product(...)" creates a separate OBJECT from the Product
        // CLASS blueprint. No renaming variables like "priceMouse" needed —
        // the class already knows every product has a name, price, stock,
        // and availability.
        Product mouse = new Product("Wireless Mouse", 19.99, 42, true);
        Product keyboard = new Product("Gaming Keyboard", 29.99, 50, false);
        Product monitor = new Product("4K Monitor", 399.99, 8, true);

        System.out.println(mouse);
        System.out.println(keyboard);
        System.out.println(monitor);

        System.out.println("Mouse total inventory value: $" + mouse.totalInventoryValue());
        System.out.println("Keyboard total inventory value: $" + keyboard.totalInventoryValue());
        System.out.println("Monitor total inventory value: $" + monitor.totalInventoryValue());

        mouse.sell(5);
        System.out.println("After selling 5 mice: " + mouse);
        System.out.println("Is mouse stock low? " + mouse.isLowStock());
        monitor.sell(3);
        System.out.println("After selling 3 monitors: " + monitor);
        System.out.println("Is monitor stock low? " + monitor.isLowStock());

        // The real payoff: a list-worthy amount of products with zero
        // variable-name duplication. Try adding a third product yourself.
        Product[] catalog = { mouse, keyboard, monitor };
        System.out.println("\n--- Full catalog ---");
        for (Product p : catalog) {
            System.out.println(p);
        }
    }
}