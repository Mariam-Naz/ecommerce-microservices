import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Catalog {

    private List<Product> products = new ArrayList<>();
    private Map<String, Product> byName = new HashMap<>();

    public void addProduct(Product product) {
        products.add(product);
        byName.put(product.getName(), product);
    }

    public Product findByName(String name) {
        Product product = byName.get(name);
        if (product == null) {
            throw new ProductNotFoundException(name);
        }
        return product;
    }

    // --- OLD WAY (Session 4): manual loop ---
    public List<Product> lowStockProductsOldWay() {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.isLowStock()) {
                result.add(p);
            }
        }
        return result;
    }

    // --- NEW WAY: Stream API ---
    // ".stream()" turns the List into a pipeline you can chain operations
    // on. ".filter(...)" keeps only elements matching a condition.
    // ".collect(Collectors.toList())" turns the pipeline back into a List.
    //
    // "p -> p.isLowStock()" is a LAMBDA EXPRESSION: an anonymous, inline
    // function. Read it as "given a Product p, evaluate p.isLowStock()".
    // It's shorthand for writing a whole separate method just to pass
    // around as a piece of logic.
    public List<Product> lowStockProducts() {
        return products.stream()
                .filter(p -> p.isLowStock())
                .collect(Collectors.toList());
    }

    // Even shorter: when the lambda does nothing but call one existing
    // method on its argument, Java lets you write a METHOD REFERENCE
    // instead: "Product::isLowStock" means exactly the same thing as
    // "p -> p.isLowStock()", just without naming the parameter.
    public List<Product> lowStockProductsMethodRef() {
        return products.stream()
                .filter(Product::isLowStock)
                .collect(Collectors.toList());
    }

    // --- OLD WAY (Session 4): manual max-tracking loop ---
    public Product findMostExpensiveOldWay() {
        Product mostExpensive = null;
        for (Product p : products) {
            if (mostExpensive == null || p.compareTo(mostExpensive) > 0) {
                mostExpensive = p;
            }
        }
        return mostExpensive;
    }

    // --- NEW WAY: Stream API ---
    // ".max(Comparator...)" needs to know HOW to compare two products.
    // "Comparator.comparingDouble(Product::getPrice)" builds a comparator
    // that compares products by price - built from a method reference,
    // not from Product's own compareTo(), just to show the alternative.
    // ".max()" returns an Optional<Product> - a box that either contains
    // a value or is empty (for example, if the catalog has zero products).
    // We handle "empty" explicitly instead of risking a null.
    public Product findMostExpensive() {
        return products.stream()
                .max(Comparator.comparingDouble(Product::getPrice))
                .orElseThrow(() -> new IllegalStateException("Catalog is empty"));
    }

    // A completely new capability that would be tedious to hand-write:
    // group every product by category into a Map<String, List<Product>>.
    public Map<String, List<Product>> groupByCategory() {
        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
    }

    // Sum up total catalog value using ".mapToDouble(...)" (turn each
    // Product into a double) then ".sum()". Compare this one line to the
    // manual accumulator loop we wrote back in Session 4.
    public double totalCatalogValue() {
        return products.stream()
                .mapToDouble(Product::totalInventoryValue)
                .sum();
    }

    // Get just the names, sorted alphabetically - a one-liner.
    public List<String> allProductNamesSorted() {
        return products.stream()
                .map(Product::getName)   // transform each Product into its name
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Product> productsInPriceRange(double min, double max){
        return products.stream().filter(p -> p.getPrice() >= min && p.getPrice() <=max).collect(Collectors.toList());
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
}
