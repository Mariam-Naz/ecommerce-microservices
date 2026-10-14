package com.shopsphere.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DigitalProduct extends Product {

    private final String downloadUrl;

    @JsonCreator
    public DigitalProduct(
            @JsonProperty("name") String name,
            @JsonProperty("price") double price,
            @JsonProperty("quantityInStock") int quantityInStock,
            @JsonProperty("downloadUrl") String downloadUrl) {
        super(name, price, quantityInStock);
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    @Override
    public String getShippingInfo() {
        return "No shipping - instant download (" + downloadUrl + ")";
    }

    @Override
    public String getCategory() {
        return "Digital";
    }
}
