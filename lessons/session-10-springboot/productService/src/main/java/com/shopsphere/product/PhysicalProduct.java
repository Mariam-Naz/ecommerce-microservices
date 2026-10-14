package com.shopsphere.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PhysicalProduct extends Product {

    private final double weightKg;

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
