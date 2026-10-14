package com.shopsphere.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

// @RestController tells Spring: this class handles HTTP requests, and
// every method's return value should be serialized to the response body
// as JSON (via Jackson - the exact same library from Session 8, just
// wired in automatically now). @RequestMapping("/products") means every
// endpoint in this class is relative to "/products".
//
// Compare this ENTIRE file to Session 9's ProductHttpServer.java: no
// HttpServer.create(), no manual port binding, no exchange.sendResponseHeaders(),
// no hand-written JSON string building, no manual method-not-allowed
// checking. Spring Boot replaced ALL of that with annotations, because
// you now understand exactly what each one is standing in for.
@RestController
@RequestMapping("/products")
public class ProductController {

    private final CatalogService catalogService;

    // --- DEPENDENCY INJECTION, happening right here ---
    // This class needs a CatalogService to do its job. Instead of writing
    // "new CatalogService()" ourselves (tight coupling - this class would
    // be stuck with exactly one way of getting its data forever), we just
    // ASK for one as a constructor parameter. Spring sees this
    // constructor, notices ProductController is itself a Spring-managed
    // bean (@RestController is one of the annotations @ComponentScan
    // looks for), and automatically finds the CatalogService bean it
    // already created and passes it in here. Neither class ever calls
    // "new" on the other - Spring wires them together. This is what
    // "Dependency Injection" means: dependencies are INJECTED from
    // outside, not constructed from inside.
    public ProductController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // @GetMapping means "handle GET requests". With no path given, this
    // handles GET /products exactly - the same request Session 9's
    // ProductHttpServer handled by hand. Returning a List<Product>
    // directly - Spring serializes it to a JSON array automatically.
    // No toJsonArray(), no manual Content-Type header, no writing bytes
    // to an OutputStream.
    @GetMapping
    public List<Product> getAllProducts() {
        return catalogService.allProducts();
    }

    // @PathVariable captures a piece of the URL itself. GET /products/Wireless%20Mouse
    // calls this method with name = "Wireless Mouse". If findByName() throws
    // ProductNotFoundException, Spring Boot's default error handling turns
    // ANY uncaught RuntimeException into a 500 response right now - not
    // the proper 404 you'd want. Session 13 (@ControllerAdvice) fixes
    // this properly; for now, try hitting a missing product's URL and see
    // the default error response Spring Boot generates on its own.
    @GetMapping("/{name}")
    public Product getProduct(@PathVariable String name) {
        return catalogService.findByName(name);
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }
}
