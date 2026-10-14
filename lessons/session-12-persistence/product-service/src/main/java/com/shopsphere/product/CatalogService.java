package com.shopsphere.product;

import com.shopsphere.product.dto.ProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final ProductRepository productRepository;

    // Dependency injection again (Session 10), except now the injected
    // dependency is a repository backed by a real database instead of a
    // @Service holding an ArrayList/HashMap.
    public CatalogService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        seedIfEmpty();
    }

    // This constructor runs every time the app starts - but
    // productRepository.count() is only 0 the very FIRST time, because
    // after that the rows are sitting in Postgres, outside the JVM. This
    // is the actual, visible difference from Sessions 9-11: restart the
    // app and the data is still there. Try it - stop and re-run
    // product-service and hit GET /products again without the seed data
    // duplicating.
    private void seedIfEmpty() {
        if (productRepository.count() == 0) {
            productRepository.save(new PhysicalProduct("Wireless Mouse", 19.99, 42, 0.15));
            productRepository.save(new PhysicalProduct("4K Monitor", 399.99, 8, 6.2));
            productRepository.save(new DigitalProduct("Java Fundamentals eBook", 14.99, 999,
                    "https://shopsphere.example/downloads/java-fundamentals"));
        }
    }

    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    public Product findByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ProductNotFoundException(name));
    }

    // getCategory() is @Transient - it's not a database column, so it
    // can't be queried for directly the way findByName() can. We still
    // fetch everything and filter in Java with streams, exactly like
    // Session 11's exercise - the repository only replaced the raw data
    // access, business logic like this still lives here in the service.
    public List<Product> findByCategory(String category) {
        return allProducts().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // --- CREATE ---
    public Product createProduct(ProductRequest request) {
        if (productRepository.findByName(request.getName()).isPresent()) {
            throw new ProductAlreadyExistsException(request.getName());
        }
        Product product = buildProduct(request);
        return productRepository.save(product);
    }

    // --- UPDATE ---
    // This looks different from Session 11's replaceProduct()! There,
    // Product's fields were all `final`, so "updating" meant building a
    // whole new object and swapping it in. Now that Product is a mutable
    // JPA entity, we do the normal JPA thing instead: load the managed
    // entity, mutate its fields with the setters we just added, and
    // save() it - Hibernate compares the entity's new state to what it
    // last loaded and generates an UPDATE statement, not an INSERT.
    //
    // Deliberately NOT updatable here: type, weightKg, downloadUrl.
    // Changing what KIND of product something is isn't really an
    // "update" - it's deleting one product and creating a different
    // one. Keeping this method narrow (price + quantity only) is a
    // design choice, not a limitation we forgot to lift.
    public Product updateProduct(String name, ProductRequest request) {
        Product existing = findByName(name);
        existing.setPrice(request.getPrice());
        existing.setQuantityInStock(request.getQuantityInStock());
        return productRepository.save(existing);
    }

    // --- DELETE ---
    public void deleteProduct(String name) {
        Product existing = findByName(name);
        productRepository.delete(existing);
    }

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
}
