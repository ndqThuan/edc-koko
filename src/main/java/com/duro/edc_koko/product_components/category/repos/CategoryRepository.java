package com.duro.edc_koko.product_components.category.repos;

import com.duro.edc_koko.product_components.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByNameIgnoreCase(String name);

}
