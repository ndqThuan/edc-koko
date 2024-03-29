package com.duro.edc_koko.models.category.repos;

import com.duro.edc_koko.models.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByNameIgnoreCase(String name);

}
