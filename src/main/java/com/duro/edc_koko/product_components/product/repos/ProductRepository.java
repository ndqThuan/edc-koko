package com.duro.edc_koko.product_components.product.repos;

import com.duro.edc_koko.product_components.category.domain.Category;
import com.duro.edc_koko.product_components.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findFirstByCategory(Category category);

    @Query("SELECT p FROM Product p JOIN Order o ON p.id = o.product.id GROUP BY p.id ORDER BY COUNT(o.id) DESC LIMIT 7")
    Optional<List<Product>> findTop7ProductsInSale();
}
