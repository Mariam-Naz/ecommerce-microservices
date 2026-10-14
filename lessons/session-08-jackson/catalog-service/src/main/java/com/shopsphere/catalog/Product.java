package com.shopsphere.catalog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// --- Jackson annotations for polymorphic JSON ---
// Product is abstract - JSON itself has no concept of "this object is
// actually a PhysicalProduct". @JsonTypeInfo tells Jackson to write (and
// expect, when reading) an extra "type" field in the JSON so it knows
// which concrete subclass to build. @JsonSubTypes lists every subclass
// and the string that identifies it. Compare the EFFORT here - a few
// annotations - to Session 7's hand-rolled CatalogFileStorage, which
// needed a manual if/else per type and forgot SubscriptionProduct
// entirely until you fixed it. Jackson still needs to be told about each
// subtype, but it handles everything else (field names, types, nesting,
// escaping) automatically.
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PhysicalProduct.class, name = "PHYSICAL"),
        @JsonSubTypes.Type(value = DigitalProduct.class, name = "DIGITAL"),
        @JsonSubTypes.Type(value = SubscriptionProduct.class, name = "SUBSCRIPTION")
})
public abstract class Product implements Comparable<Product> {

    private String name;
    private double price;
    private int quantityInStock;

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

    // @JsonIgnore on all three of these: Jackson's default rule is "any
    // public getXxx()/isXxx() method is a property to serialize", which
    // is meant for real stored fields. These three are COMPUTED values
    // with no matching constructor parameter to rebuild them from - left
    // unannotated, Jackson would write them out on save, then choke on
    // them on load exactly like "category" just did. totalInventoryValue()
    // didn't cause trouble because it doesn't start with "get"/"is", so
    // Jackson's naming convention never picked it up as a property at all.
    @JsonIgnore
    public String getCategory() {
        return "General";
    }

    public double totalInventoryValue() {
        return price * quantityInStock;
    }

    @JsonIgnore
    public boolean isLowStock() {
        return quantityInStock < 10;
    }

    public void sell(int units) {
        if (units > quantityInStock) {
            throw new InsufficientStockException(name, units, quantityInStock);
        }
        if (units <= 0) {
            throw new IllegalArgumentException("Units to sell must be positive, got: " + units);
        }
        quantityInStock = quantityInStock - units;
    }

    @JsonIgnore
    public abstract String getShippingInfo();

    @Override
    public int compareTo(Product other) {
        return Double.compare(this.price, other.price);
    }

    @Override
    public String toString() {
        return name + " | $" + price + " | stock: " + quantityInStock
                + " | " + getShippingInfo();
    }
}