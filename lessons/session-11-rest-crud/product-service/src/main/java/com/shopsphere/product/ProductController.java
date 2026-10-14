package com.shopsphere.product;

import com.shopsphere.product.dto.ProductRequest;
import com.shopsphere.product.dto.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final CatalogService catalogService;

    public ProductController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // Returning Product directly (Session 10) meant the client saw
    // whatever Jackson's default serialization produced for whichever
    // getters weren't @JsonIgnore'd. Now every method maps through
    // ProductResponse.from() instead - the response shape is a decision,
    // not a side effect.
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return catalogService.allProducts().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    public ProductResponse getProduct(@PathVariable String name) {
        // Still throws a plain ProductNotFoundException on a miss, which
        // Spring Boot's default handling still turns into a 500 instead
        // of the proper 404 - noted back in Session 10, still not fixed
        // until Session 13 (@ControllerAdvice). Try it yourself and look
        // at the raw response if you're curious what it looks like today.
        return ProductResponse.from(catalogService.findByName(name));
    }

    @GetMapping("/category/{category}")
    public List<ProductResponse> getProductByCategory(@PathVariable String category) {
        List<Product> productsByCategory = catalogService.findByCategory(category);
        if (productsByCategory.isEmpty()) {
            throw new ProductNotFoundException("No products found in category: " + category);
        }
        return productsByCategory.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }

    // --- CREATE ---
    // @PostMapping handles POST /products. @RequestBody tells Spring to
    // deserialize the request's JSON body into a ProductRequest (Jackson
    // again, same engine as always) instead of reading it off the URL
    // the way @PathVariable does. @ResponseStatus(HttpStatus.CREATED)
    // sends back 201 instead of the default 200 - the correct status
    // code for "a new resource now exists," which Session 9's GET-only
    // server never had a reason to use.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return ProductResponse.from(catalogService.createProduct(request));
    }

    // --- UPDATE ---
    // @PutMapping handles PUT /products/{name}: replace whatever
    // currently lives at this name with the request body. (Default
    // response status for a method that returns a value is 200, which
    // is correct here.)
    @PutMapping("/{name}")
    public ProductResponse replaceProduct(@PathVariable String name, @RequestBody ProductRequest request) {
        return ProductResponse.from(catalogService.replaceProduct(name, request));
    }

    // --- DELETE ---
    // @DeleteMapping handles DELETE /products/{name}. 204 No Content is
    // the conventional status for "deleted successfully, nothing
    // meaningful to send back" - note the return type is void, not
    // ProductResponse.
    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String name) {
        catalogService.deleteProduct(name);
    }
}
