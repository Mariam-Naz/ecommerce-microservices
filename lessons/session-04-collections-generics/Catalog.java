import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// A Catalog manages a collection of Products. Arrays (Session 3) have a
// fixed size decided at creation — you cannot add a 4th product to a
// Product[3]. A real catalog needs to grow and shrink freely, which is
// exactly what the Collections Framework (List, Set, Map, ...) is for.
public class Catalog {

    // "List<Product>" is a GENERIC type: List by itself doesn't know what
    // it holds, but List<Product> is a list that can ONLY ever hold
    // Product objects (or subclasses of Product). The compiler enforces
    // this — try to add a String to this list and it won't compile.
    // This is generics doing its job: catching type mistakes before
    // the program even runs, the same spirit as static typing in Session 1.
    //
    // ArrayList is the actual implementation (a resizable array under the
    // hood). We declare the variable as the interface type "List" and
    // only instantiate the concrete "ArrayList" — this is a common Java
    // convention: code against the interface, not the implementation, so
    // you could swap ArrayList for LinkedList later without changing
    // anything else in this class.
    private List<Product> products = new ArrayList<>();

    // A Map<K, V> stores key-value pairs. Here: product name -> Product
    // object, so we can look a product up instantly by name instead of
    // scanning the whole list every time. This trade-off (a bit more
    // memory, much faster lookup) is exactly why product-service will
    // later use a real database index for the same reason.
    private Map<String, Product> byName = new HashMap<>();

    public void addProduct(Product product) {
        products.add(product);
        byName.put(product.getName(), product);
    }

    public Product findByName(String name) {
        // HashMap.get() returns null if the key isn't found — there's no
        // exception thrown. Any code calling this MUST check for null
        // before using the result, or risk a NullPointerException. This
        // exact problem (forgetting the null check) is one of the most
        // common real bugs in Java, and it's precisely why Session 5
        // (exceptions) and later Optional<T> exist.
        return byName.get(name);
    }

    public List<Product> lowStockProducts() {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.isLowStock()) {
                result.add(p);
            }
        }
        return result;
    }

    public Product findMostExpensive(){
        Product mostExpensive = null;
        for (Product p : products) {
            if (mostExpensive == null || p.compareTo(mostExpensive) > 0) {
                mostExpensive = p;
            }
        }
        return mostExpensive;
    }

    public double totalCatalogValue() {
        double total = 0;
        for (Product p : products) {
            total += p.totalInventoryValue();
        }
        return total;
    }

    public boolean removeByName(String name) {
        Product product = byName.remove(name);
        if (product == null) {
            return false; // wasn't in the catalog
        }
        products.remove(product);
        return true;
    }

    public int size() {
        return products.size();
    }

    public List<Product> allProducts() {
        return products;
    }
}
