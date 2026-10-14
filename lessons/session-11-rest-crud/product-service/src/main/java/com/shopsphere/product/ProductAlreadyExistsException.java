package com.shopsphere.product;

// Same pattern as ProductNotFoundException (Session 10) and
// InsufficientStockException (Session 5): a specific, named, unchecked
// exception instead of a vague error. This one guards CREATE - trying to
// add a product whose name already exists should fail loudly, not
// silently overwrite the existing one.
public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String productName) {
        super("Product already exists: " + productName);
    }
}
