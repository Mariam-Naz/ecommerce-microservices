package com.shopsphere.product;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// @Service marks this as a SPRING BEAN - an object Spring itself creates
// and manages, instead of you writing "new CatalogService()" anywhere.
// This is INVERSION OF CONTROL (IoC): normally YOUR code controls when
// objects get created. With Spring, control is INVERTED - the FRAMEWORK
// creates objects and hands them to whoever needs them. @Service is one
// of several annotations (@Component, @Repository, @Controller are the
// others) that tell Spring's @ComponentScan "manage this class".
//
// In-memory storage, same as Session 4-9's Catalog, just renamed/adapted
// to live inside a Spring-managed bean instead of a plain object you
// construct yourself in a main() method.
@Service
public class CatalogService {

    private final List<Product> products = new ArrayList<>();
    private final Map<String, Product> byName = new ConcurrentHashMap<>();

    // This constructor runs ONCE, when Spring creates this bean at
    // startup - seeding some sample data so there's something to see
    // immediately. A real version would load this from a database
    // instead (Session 12).
    public CatalogService() {
        addProduct(new PhysicalProduct("Wireless Mouse", 19.99, 42, 0.15));
        addProduct(new PhysicalProduct("4K Monitor", 399.99, 8, 6.2));
        addProduct(new DigitalProduct("Java Fundamentals eBook", 14.99, 999,
                "https://shopsphere.example/downloads/java-fundamentals"));
        addProduct(new SubscriptionProduct("Premium Subscription", 9.99, 100, 30));

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
}
