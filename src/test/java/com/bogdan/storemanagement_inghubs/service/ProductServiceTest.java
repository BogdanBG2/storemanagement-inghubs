package com.bogdan.storemanagement_inghubs.service;

import com.bogdan.storemanagement_inghubs.model.Product;
import com.bogdan.storemanagement_inghubs.model.constants.ProductCategory;
import com.bogdan.storemanagement_inghubs.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @InjectMocks
  private ProductService productService;

  @Mock
  private ProductRepository productRepository;

  private Product getMockProduct() {
    Product product = new Product();
    product.setId(1L);
    product.setName("Test Product");
    product.setPrice(10.0);
    product.setCategory(ProductCategory.FRUITS);
    return product;
  }

  @Test
  void testFindAll() {
    Pageable pageable = mock(Pageable.class);
    String nameSubString = "Test";
    Integer minPrice = 5;
    Integer maxPrice = 15;
    ProductCategory category = ProductCategory.FRUITS;
    Page<Product> page = Page.empty();
    page = page.map(p -> getMockProduct());
    when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(page);
    Page<Product> result = productService.findAll(pageable, nameSubString, minPrice, maxPrice, category);
    assertEquals(page, result);
  }

  @Test
  void testFindById() {
    Product product = getMockProduct();
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    Optional<Product> foundProduct = productService.findById(1L);
    assertTrue(foundProduct.isPresent());
    assertEquals(product, foundProduct.get());
  }

  @Test
  void testSave() {
    Product product = getMockProduct();
    when(productRepository.save(product)).thenReturn(product);
    Product savedProduct = productService.save(product);
    assertEquals(product, savedProduct);
  }

  @Test
  void testDeleteById() {
    doNothing().when(productRepository).deleteById(1L);
    productService.deleteById(1L);
    verify(productRepository).deleteById(1L);
  }

  @Test
  void testUpdatePrice_idNotFound() {
    when(productRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(IllegalArgumentException.class, () -> productService.updatePrice(1L, 10.0));
  }

  @Test
  void testUpdate() {
    Product product = getMockProduct();
    product.setPrice(20.0);
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    when(productRepository.save(product)).thenReturn(product);
    Product updatedProduct = productService.updatePrice(1L, 20.0);
    assertEquals(product, updatedProduct);
  }
}
