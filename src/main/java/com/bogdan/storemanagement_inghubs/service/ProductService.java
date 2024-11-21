package com.bogdan.storemanagement_inghubs.service;

import com.bogdan.storemanagement_inghubs.model.Product;
import com.bogdan.storemanagement_inghubs.model.constants.ProductCategory;
import com.bogdan.storemanagement_inghubs.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  public Page<Product> findAll(Pageable pageable,
                               String nameSubString,
                               Integer minPrice,
                               Integer maxPrice,
                               ProductCategory category) {
    return productRepository.findAll((Specification<Product>) (root, query, cb) -> {
      if (query == null) {
        return null;
      }
      if (nameSubString != null) {
        query.where(cb.like(root.get("name"), "%" + nameSubString + "%"));
      }
      if (minPrice != null) {
        query.where(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
      }
      if (maxPrice != null) {
        query.where(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
      }
      if (category != null) {
        query.where(cb.equal(root.get("category"), category));
      }
      return query.getRestriction();
    }, pageable);
  }

  public Optional<Product> findById(Long id) {
    return productRepository.findById(id);
  }

  public Product save(Product product) {
    return productRepository.save(product);
  }

  public void deleteById(Long id) {
    productRepository.deleteById(id);
  }

  public Product update(Long id, double newPrice) {
    Optional<Product> product = findById(id);
    if (product.isEmpty()) {
      throw new IllegalArgumentException("Product with id " + id + " not found");
    }
    Product updatedProduct = product.get();
    updatedProduct.setPrice(newPrice);
    return save(updatedProduct);
  }
}
