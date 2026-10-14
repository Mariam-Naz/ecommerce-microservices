package com.shopsphere.catalog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PhysicalProduct extends Product {

    private double weightKg;

    // @JsonCreator + @JsonProperty on each parameter tells Jackson exactly
    // how to rebuild a PhysicalProduct from JSON fields when there's no
    // no-argument constructor or setters to fall back on (we deliberately
    // never added setters - encapsulation from Session 2 still holds).
    @JsonCreator
    public PhysicalProduct(
            @JsonProperty("name") String name,
            @JsonProperty("price") double price,
            @JsonProperty("quantityInStock") int quantityInStock,
            @JsonProperty("weightKg") double weightKg) {
        super(name, price, quantityInStock);
        this.weightKg = weightKg;
    }

    public double getWeightKg() {
        return weightKg;
    }

    @Override
    public String getShippingInfo() {
        double shippingCost = weightKg * 2.50;
        return "Ships (" + weightKg + "kg, ~$" + shippingCost + " shipping)";
    }

    @Override
    public String getCategory() {
        return "Physical";
    }
}
