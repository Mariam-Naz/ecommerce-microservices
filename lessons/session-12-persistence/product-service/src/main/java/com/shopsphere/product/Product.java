package com.shopsphere.product;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

// @Entity tells Spring Data JPA (Hibernate, underneath) "this class maps
// to a database table." There's no ArrayList/HashMap living inside
// CatalogService anymore - rows in a real Postgres table ARE the catalog
// now, and they survive an app restart.
//
// Notice there's no @JsonTypeInfo/@JsonSubTypes/@JsonCreator anywhere in
// this file anymore, unlike Session 10. Session 11's DTOs are exactly
// why: Product is NEVER serialized to JSON directly now -
// ProductResponse.from() is the only bridge to the outside world. This
// class's only job is mapping to a table, and it reads a lot simpler for
// it - one concern per class.
//
// @Inheritance(strategy = SINGLE_TABLE) is the database equivalent of
// Session 8's @JsonTypeInfo/@JsonSubTypes: PhysicalProduct and
// DigitalProduct both live in ONE "products" table, with a
// "product_type" discriminator column telling Hibernate which Java
// subclass to build when it reads a row back. Same "one bucket plus a
// type tag" idea you already learned for JSON, now for SQL.
@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Product implements Comparable<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int quantityInStock;

    // JPA REQUIRES a no-arg constructor. Hibernate builds objects by
    // calling this one (via reflection) and then setting fields
    // directly - it never calls the "real" constructor below. protected,
    // not public: visible to Hibernate (and subclasses) but nothing in
    // YOUR code should ever call it.
    protected Product() {
    }

    public Product(String name, double price, int quantityInStock) {
        if (price < 0 || quantityInStock < 0) {
            throw new IllegalArgumentException(
                    "Price and quantity cannot be negative, got: " + price + ", " + quantityInStock);
        }
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public Long getId() {
        return id;
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

    // These two are new: Session 10/11 made Product fully immutable
    // (every field final, no setters) - a deliberate design choice.
    // Real JPA entities are conventionally mutable instead: you load a
    // managed entity, change a field, and Hibernate's "dirty checking"
    // notices the change and writes an UPDATE for you. That's a genuine,
    // debated trade-off in Java backend design (immutability's safety
    // vs. the ORM's expected mutation model) - we're choosing the ORM's
    // convention here on purpose, not because the earlier lesson was
    // wrong.
    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    // @Transient tells Hibernate "this is NOT a database column" - the
    // ORM equivalent of @JsonIgnore from Session 10, just for the table
    // mapping instead of the JSON mapping. Without it, Hibernate would
    // try (and fail) to find a "category" column that doesn't exist.
    @Transient
    public String getCategory() {
        return "General";
    }

    @Transient
    public boolean isLowStock() {
        return quantityInStock < 10;
    }

    @Transient
    public abstract String getShippingInfo();

    @Override
    public int compareTo(Product other) {
        return Double.compare(this.price, other.price);
    }
}
