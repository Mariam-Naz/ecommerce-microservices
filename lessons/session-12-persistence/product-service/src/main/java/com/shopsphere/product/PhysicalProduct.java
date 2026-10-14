package com.shopsphere.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

// @DiscriminatorValue sets what goes in the "product_type" column for
// rows of this subclass - the database equivalent of Session 8's
// @JsonSubTypes.Type(name = "PHYSICAL"). No table of its own: thanks to
// SINGLE_TABLE on Product, these rows live in the same "products" table,
// just with product_type = 'PHYSICAL' and a populated weight_kg column
// (nullable, since a DIGITAL row never fills it in).
@Entity
@DiscriminatorValue("PHYSICAL")
public class PhysicalProduct extends Product {

    private double weightKg;

    protected PhysicalProduct() {
    }

    public PhysicalProduct(String name, double price, int quantityInStock, double weightKg) {
        super(name, price, quantityInStock);
        this.weightKg = weightKg;
    }

    public double getWeightKg() {
        return weightKg;
    }

    // Re-annotating an overridden @Transient getter in the subclass -
    // Hibernate reads annotations per-method, so marking it only on the
    // abstract method in Product isn't reliably enough once a subclass
    // overrides it.
    @Override
    @Transient
    public String getShippingInfo() {
        double shippingCost = weightKg * 2.50;
        return "Ships (" + weightKg + "kg, ~$" + shippingCost + " shipping)";
    }

    @Override
    @Transient
    public String getCategory() {
        return "Physical";
    }
}
