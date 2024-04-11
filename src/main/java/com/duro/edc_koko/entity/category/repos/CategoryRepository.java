package com.duro.edc_koko.entity.category.repos;


import com.duro.edc_koko.entity.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByNameIgnoreCase (String name);

    @Query("select c from Category c order by c.id limit 5")
    List<Category> find5Categories ();

    @Query("select c.id from Category c where upper(c.name) like upper(?1)")
    Integer findIdByName (String name);

}
