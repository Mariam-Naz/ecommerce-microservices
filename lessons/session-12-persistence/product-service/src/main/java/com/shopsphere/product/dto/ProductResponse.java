package com.shopsphere.product.dto;

import com.shopsphere.product.Product;

// Same role as Session 11: the one deliberate response shape, built by
// from(Product). New this session: id, now that every product has a
// real, meaningful database identity (an auto-generated primary key)
// instead of just being looked up by name.
public class ProductResponse {

    private final Long id;
    private final String name;
    private final double price;
    private final int quantityInStock;
    private final String category;
    private final String shippingInfo;
    private final boolean lowStock;

    public ProductResponse(Long id, String name, double price, int quantityInStock,
                            String category, String shippingInfo, boolean lowStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.category = category;
        this.shippingInfo = shippingInfo;
        this.lowStock = lowStock;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantityInStock(),
                product.getCategory(),
                product.getShippingInfo(),
                product.isLowStock());
    }

    public Long getId() {
        return id;
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
