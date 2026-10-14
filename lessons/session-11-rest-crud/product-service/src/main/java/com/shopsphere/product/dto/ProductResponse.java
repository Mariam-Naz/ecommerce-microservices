package com.shopsphere.product.dto;

import com.shopsphere.product.Product;

// --- RESPONSE DTO ---
// This is the flat shape clients actually SEE in a response. Compare to
// Session 10, where controllers returned Product directly: that only
// stayed clean because we explicitly @JsonIgnore'd getCategory(),
// isLowStock(), and getShippingInfo() in Product.java to hide them from
// Jackson's automatic "any getXxx()/isXxx() method becomes a JSON field"
// behavior. That worked, but it means the API's shape was a SIDE EFFECT
// of internal class design, not a deliberate decision.
//
// With a response DTO, WE decide exactly what a client sees, by hand, in
// one place (the from() method below) - instead of fighting Jackson's
// defaults field by field on the domain class itself. This is the core
// reason real APIs use DTOs for responses too, not just requests: the
// domain model (Product and its subclasses) is free to change shape
// internally without breaking every client depending on the JSON.
public class ProductResponse {

    private final String name;
    private final double price;
    private final int quantityInStock;
    private final String category;
    private final String shippingInfo;
    private final boolean lowStock;

    public ProductResponse(String name, double price, int quantityInStock,
                            String category, String shippingInfo, boolean lowStock) {
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.category = category;
        this.shippingInfo = shippingInfo;
        this.lowStock = lowStock;
    }

    // Factory method: the ONE place that knows how to turn an internal
    // Product (whatever concrete subclass it actually is - the method
    // doesn't care) into the DTO a client receives. Every controller
    // method that hands back product data calls this instead of
    // returning a Product directly.
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getName(),
                product.getPrice(),
                product.getQuantityInStock(),
                product.getCategory(),
                product.getShippingInfo(),
                product.isLowStock());
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public String getCategory() {
        return category;
    }

    public String getShippingInfo() {
        return shippingInfo;
    }

    public boolean isLowStock() {
        return lowStock;
    }
}
