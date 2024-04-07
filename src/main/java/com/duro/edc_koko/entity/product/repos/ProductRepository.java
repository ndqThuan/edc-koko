package com.duro.edc_koko.entity.product.repos;

import com.duro.edc_koko.entity.category.domain.Category;
import com.duro.edc_koko.entity.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findFirstByCategory (Category category);

    @Query("select distinct p from Product p where p.category.name = ?1")
    List<Product> findDistinctByCategory_Name (String name);

}
