package com.shopsphere.catalog;

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

    public List<Product> lowStockProducts() {
        return products.stream()
                .filter(Product::isLowStock)
                .collect(Collectors.toList());
    }

    public Product findMostExpensive() {
        return products.stream()
                .max(Comparator.comparingDouble(Product::getPrice))
                .orElseThrow(() -> new IllegalStateException("Catalog is empty"));
    }

    public List<Product> productsInPriceRange(double min, double max) {
        return products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public double totalCatalogValue() {
        return products.stream()
                .mapToDouble(Product::totalInventoryValue)
                .sum();
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
