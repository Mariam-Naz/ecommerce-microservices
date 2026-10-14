package com.shopsphere.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionProduct extends Product{
    private int renewalPeriodDays;

    @JsonCreator
    public SubscriptionProduct(@JsonProperty("name") String name, @JsonProperty("price") double price, @JsonProperty("quantityInstock") int quantityInstock, @JsonProperty("renewalPeriodDays") int renewalPeriodDays) {
        super(name, price, quantityInstock);
        this.renewalPeriodDays = renewalPeriodDays;
    }

    @Override 
    public String getShippingInfo(){
        return "No shipping - recurring subscription, renews every " + renewalPeriodDays + " days";
    }

    public int getRenewalPeriodDays() {
        return renewalPeriodDays;
    }

    @Override 
    public String getCategory(){
        return "Subscription";
    }
    
}
