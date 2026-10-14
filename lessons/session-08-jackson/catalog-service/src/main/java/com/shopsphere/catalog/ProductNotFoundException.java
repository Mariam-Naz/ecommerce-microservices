package com.shopsphere.catalog;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productName) {
        super("Product not found: " + productName);
    }
}
