package com.shopsphere.product.dto;

// --- REQUEST DTO ---
// "DTO" = Data Transfer Object: a plain class whose only job is to carry
// data across a boundary (here, the HTTP request body) - no behavior, no
// business logic, just fields. This is the shape of JSON a CLIENT sends
// to create or replace a product:
//
//   { "type": "PHYSICAL", "name": "Desk Lamp", "price": 24.99,
//     "quantityInStock": 15, "weightKg": 1.2 }
//
// Notice this looks nothing like Product's @JsonTypeInfo/@JsonSubTypes
// setup from Session 8. A client creating a product shouldn't need to
// know our internal class hierarchy exists at all - just a flat "type"
// string plus whichever extra field that type needs. weightKg and
// downloadUrl are both nullable (Double, not double; plain String) for
// exactly that reason: only one of them will actually be present,
// depending on type. CatalogService decides which, and validates it.
//
// Jackson builds this a DIFFERENT way than it builds Product/PhysicalProduct
// (Session 8's @JsonCreator constructor approach): it calls the no-arg
// constructor to make an empty ProductRequest, then calls setType(...),
// setName(...), etc. for whichever fields are present in the incoming
// JSON. For a DTO with several optional fields, plain getters/setters
// like this are simpler than wiring up a constructor with every
// combination.
public class ProductRequest {

    private String type;
    private String name;
    private double price;
    private int quantityInStock;
    private Double weightKg;
    private String downloadUrl;

    public ProductRequest() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
