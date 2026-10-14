package com.shopsphere.catalog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionProduct extends Product{
    private int renewalPeriodDays;

    @JsonCreator 
    public SubscriptionProduct(@JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("quantityInStock") int quantityInStock, @JsonProperty("renewalPeriodDays") int renewalPeriodDays) {
        super(name, price, quantityInStock);
        this.renewalPeriodDays = renewalPeriodDays;
    }

    @Override 
    public String getShippingInfo(){
        return "No shipping - recurring subscription, renews every " + renewalPeriodDays + " days";
    }

    public int getRenewalPeriodDays() {
        return renewalPeriodDays;
    }
    
}
