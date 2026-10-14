package com.shopsphere.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// This interface IS the entire data-access layer - no SQL, no JDBC
// boilerplate, not even an implementation you write yourself.
// JpaRepository<Product, Long> already gives you save(), findAll(),
// findById(), delete(), count(), and more, for free, just by extending
// it. The <Product, Long> means "this repository manages Product
// entities, whose @Id field is a Long." Spring generates a real
// implementation of this interface at startup and injects it anywhere
// you ask for a ProductRepository - the same dependency injection magic
// from Session 10, now creating a bean out of an interface you never
// implemented.
//
// findByName(String name) is a DERIVED QUERY METHOD: you write only the
// method SIGNATURE, and Spring Data parses the method NAME itself
// ("findBy" + "Name") to build the query ("SELECT * FROM products WHERE
// name = ?") and implement the method - no body, no @Query annotation
// needed for something this simple. This replaces the Map<String,
// Product> byName lookup from Session 4 - Spring Data writes that
// lookup's implementation for you now, against a real database instead
// of a HashMap in memory.
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);
}
