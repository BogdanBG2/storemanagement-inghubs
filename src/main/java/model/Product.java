package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.constants.ProductCategory;

@Entity
@Table(name = "products", indexes = {
  @Index(columnList = "category")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private double price;
  @Enumerated(EnumType.STRING)
  private ProductCategory category;
}
