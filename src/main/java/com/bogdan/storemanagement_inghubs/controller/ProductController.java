package com.bogdan.storemanagement_inghubs.controller;

import com.bogdan.storemanagement_inghubs.model.Product;
import com.bogdan.storemanagement_inghubs.model.constants.ProductCategory;
import com.bogdan.storemanagement_inghubs.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false) String nameSubString,
                                                   @RequestParam(required = false) Integer minPrice,
                                                   @RequestParam(required = false) Integer maxPrice,
                                                   @RequestParam(required = false) ProductCategory category,
                                                   Pageable pageable) {
    return ResponseEntity.ok(productService.findAll(pageable, nameSubString, minPrice, maxPrice, category));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProduct(@PathVariable Long id) {
    return productService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Product> addProduct(@RequestBody Product product) {
    return ResponseEntity.ok(productService.save(product));
  }

  @PostMapping("/{id}/price")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Product> updateProductPrice(@PathVariable Long id,
                                                    @RequestParam double newPrice) {
    try {
      return ResponseEntity.ok(productService.updatePrice(id, newPrice));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    try {
      productService.deleteById(id);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
