package com.shopsphere.product;

import com.shopsphere.product.dto.ProductRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final List<Product> products = new ArrayList<>();
    private final Map<String, Product> byName = new ConcurrentHashMap<>();

    public CatalogService() {
        addProduct(new PhysicalProduct("Wireless Mouse", 19.99, 42, 0.15));
        addProduct(new PhysicalProduct("4K Monitor", 399.99, 8, 6.2));
        addProduct(new DigitalProduct("Java Fundamentals eBook", 14.99, 999,
                "https://shopsphere.example/downloads/java-fundamentals"));
    }

    public void addProduct(Product product) {
        products.add(product);
        byName.put(product.getName(), product);
    }

    public List<Product> allProducts() {
        return products;
    }

    public Product findByName(String name) {
        Product product = byName.get(name);
        if (product == null) {
            throw new ProductNotFoundException(name);
        }
        return product;
    }

    // --- CREATE (backs POST /products) ---
    // Rejects a name that already exists rather than silently
    // overwriting it - the same "fail fast with a clear exception"
    // instinct from Session 5. A silent overwrite would be a much
    // nastier bug to track down later than an exception thrown right
    // here, at the moment something's actually wrong.
    public Product createProduct(ProductRequest request) {
        if (byName.containsKey(request.getName())) {
            throw new ProductAlreadyExistsException(request.getName());
        }
        Product product = buildProduct(request);
        addProduct(product);
        return product;
    }

    // --- UPDATE (backs PUT /products/{name}) ---
    // Product's fields are all `final` - Session 10 made it immutable on
    // purpose (constructor sets everything once, no setters exist). So
    // you literally CANNOT mutate an existing Product's price in place.
    // The only way to "update" one is to build a brand-new Product from
    // the request and REPLACE the old one, in both the list and the map.
    // That's exactly what HTTP PUT means by definition: replace the
    // resource at this URL with this representation - not "patch a few
    // fields" (that's what PATCH is for, and it's deliberately not here
    // yet - full replace is the simpler semantic to learn first).
    public Product replaceProduct(String name, ProductRequest request) {
        Product existing = findByName(name);
        Product replacement = buildProduct(request);
        products.remove(existing);
        products.add(replacement);
        byName.remove(name);
        byName.put(replacement.getName(), replacement);
        return replacement;
    }

    // --- DELETE (backs DELETE /products/{name}) ---
    public void deleteProduct(String name) {
        Product existing = findByName(name);
        products.remove(existing);
        byName.remove(name);
    }

    // One place that knows how to turn a flat ProductRequest into the
    // correct Product subclass - the mirror image of ProductResponse.from(),
    // which is the one place that knows how to go the other direction.
    // Note: an unknown/missing type currently throws a plain
    // IllegalArgumentException, which Spring Boot turns into a generic
    // 500 error - not a clean 400. That's the same rough edge
    // ProductNotFoundException has today (see the comment in
    // ProductController); Session 13's @ControllerAdvice fixes both at
    // once.
    private Product buildProduct(ProductRequest request) {
        if (request.getType() == null) {
            throw new IllegalArgumentException("type is required (PHYSICAL or DIGITAL)");
        }
        switch (request.getType()) {
            case "PHYSICAL":
                if (request.getWeightKg() == null) {
                    throw new IllegalArgumentException("weightKg is required for PHYSICAL products");
                }
                return new PhysicalProduct(request.getName(), request.getPrice(),
                        request.getQuantityInStock(), request.getWeightKg());
            case "DIGITAL":
                if (request.getDownloadUrl() == null) {
                    throw new IllegalArgumentException("downloadUrl is required for DIGITAL products");
                }
                return new DigitalProduct(request.getName(), request.getPrice(),
                        request.getQuantityInStock(), request.getDownloadUrl());
            default:
                throw new IllegalArgumentException("Unknown product type: " + request.getType());
        }
    }
    public List<Product> findByCategory(String category) {
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}
