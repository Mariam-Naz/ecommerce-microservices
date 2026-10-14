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

// Unchanged from Session 11's shape - this is the whole point of
// layering. CatalogService's internals flipped from an ArrayList/HashMap
// to a real Postgres-backed repository, and this controller didn't have
// to change its public behavior at all beyond the one method rename
// below. The controller only ever talked to CatalogService's methods and
// DTOs - it never knew or cared how products were stored.
@RestController
@RequestMapping("/products")
public class ProductController {

    private final CatalogService catalogService;

    public ProductController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return catalogService.allProducts().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    public ProductResponse getProduct(@PathVariable String name) {
        return ProductResponse.from(catalogService.findByName(name));
    }

    @GetMapping("/category/{category}")
    public List<ProductResponse> getProductsByCategory(@PathVariable String category) {
        return catalogService.findByCategory(category).stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return ProductResponse.from(catalogService.createProduct(request));
    }

    // Renamed from Session 11's replaceProduct() to updateProduct() -
    // matching what CatalogService actually does now (mutate price and
    // quantity on the existing row, not replace the whole thing). The
    // route and request/response shape are identical; only the method
    // name changed to stay honest about the new semantics.
    @PutMapping("/{name}")
    public ProductResponse updateProduct(@PathVariable String name, @RequestBody ProductRequest request) {
        return ProductResponse.from(catalogService.updateProduct(name, request));
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String name) {
        catalogService.deleteProduct(name);
    }
}
