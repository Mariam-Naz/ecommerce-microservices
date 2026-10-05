import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Catalog {

    private List<Product> products = new ArrayList<>();
    private Map<String, Product> byName = new HashMap<>();

    public void addProduct(Product product) {
        products.add(product);
        byName.put(product.getName(), product);
    }

    // Old version returned null when a product wasn't found, silently
    // pushing the problem onto whoever called this method. New version:
    // THROW immediately with a clear message. Now it's IMPOSSIBLE to
    // accidentally use a missing product without realizing it - the
    // program stops right here with an exception that says exactly
    // what went wrong, instead of crashing three lines later somewhere
    // else with a useless NullPointerException.
    public Product findByName(String name) {
        Product product = byName.get(name);
        if (product == null) {
            throw new ProductNotFoundException(name);
        }
        return product;
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
            return false;
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

    public Product findMostExpensive() {
        Product mostExpensive = null;
        for (Product p : products) {
            if (mostExpensive == null || p.compareTo(mostExpensive) > 0) {
                mostExpensive = p;
            }
        }
        return mostExpensive;
    }
}
