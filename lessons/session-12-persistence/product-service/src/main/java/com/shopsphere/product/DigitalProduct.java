package com.shopsphere.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

@Entity
@DiscriminatorValue("DIGITAL")
public class DigitalProduct extends Product {

    private String downloadUrl;

    protected DigitalProduct() {
    }

    public DigitalProduct(String name, double price, int quantityInStock, String downloadUrl) {
        super(name, price, quantityInStock);
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    @Override
    @Transient
    public String getShippingInfo() {
        return "No shipping - instant download (" + downloadUrl + ")";
    }

    @Override
    @Transient
    public String getCategory() {
        return "Digital";
    }
}
