package com.shopsphere.catalog;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.List;

// Compare this ENTIRE class to Session 7's CatalogFileStorage. No manual
// comma-splitting, no if/else per product type, no forgetting a subtype -
// ObjectMapper uses reflection (inspecting your classes' structure at
// runtime) plus the @JsonTypeInfo/@JsonCreator annotations on Product and
// its subclasses to do all of this automatically, for ANY subtype we've
// registered, including ones we add later.
public class CatalogJsonStorage {

    // ObjectMapper is Jackson's central class - it's what actually
    // converts Java objects to JSON text and back.
    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT); // pretty-print, so the file is human-readable

    public void save(Catalog catalog, String fileName) {
        try {
            // mapper.writeValue(File, Object) only ever sees "Object" - by
            // the time it reaches Jackson, Java's type erasure has thrown
            // away the fact that this is specifically a List<Product>, not
            // just "some List". Without that context, Jackson serializes
            // each element by its own runtime class and can skip writing
            // the "type" discriminator @JsonTypeInfo needs. mapper.writerFor(
            // new TypeReference<List<Product>>() {}) explicitly tells
            // Jackson "treat every element as a Product for type-info
            // purposes", the exact same fix load() already uses in reverse.
            mapper.writerFor(new TypeReference<List<Product>>() {})
                    .writeValue(new File(fileName), catalog.allProducts());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save catalog to " + fileName, e);
        }
    }

    public Catalog load(String fileName) {
        try {
            // Java's generics have a limitation: you can't write
            // "List<Product>.class" - that information gets erased at
            // runtime (a detail called "type erasure"). TypeReference is
            // Jackson's standard workaround: an anonymous subclass whose
            // only job is to preserve the full generic type for Jackson
            // to inspect.
            List<Product> products = mapper.readValue(new File(fileName), new TypeReference<List<Product>>() {});
            Catalog catalog = new Catalog();
            for (Product p : products) {
                catalog.addProduct(p);
            }
            return catalog;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load catalog from " + fileName, e);
        }
    }
}