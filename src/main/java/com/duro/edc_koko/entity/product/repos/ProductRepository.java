package com.duro.edc_koko.entity.product.repos;

import com.duro.edc_koko.entity.category.domain.Category;
import com.duro.edc_koko.entity.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findFirstByCategory(Category category);

}
