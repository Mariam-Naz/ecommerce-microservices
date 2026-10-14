package com.shopsphere.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// Unchanged from Session 8's Jackson annotations - this is the payoff of
// having already learned them properly: Spring Boot will use the exact
// same Jackson engine under the hood to serialize this when a controller
// returns it, so everything you already understand still applies.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PhysicalProduct.class, name = "PHYSICAL"),
        @JsonSubTypes.Type(value = DigitalProduct.class, name = "DIGITAL"),
        @JsonSubTypes.Type(value = SubscriptionProduct.class, name = "SUBSCRIPTION")
})
public abstract class Product implements Comparable<Product> {

    private final String name;
    private final double price;
    private final int quantityInStock;

    public Product(String name, double price, int quantityInStock) {
        if (price < 0 || quantityInStock < 0) {
            throw new IllegalArgumentException(
                    "Price and quantity cannot be negative, got: " + price + ", " + quantityInStock);
        }
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
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

    @JsonIgnore
    public String getCategory() {
        return "General";
    }

    @JsonIgnore
    public boolean isLowStock() {
        return quantityInStock < 10;
    }

    @JsonIgnore
    public abstract String getShippingInfo();

    @Override
    public int compareTo(Product other) {
        return Double.compare(this.price, other.price);
    }
}
