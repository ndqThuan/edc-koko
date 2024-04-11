package com.duro.edc_koko.entity.product.repos;

import com.duro.edc_koko.entity.category.domain.Category;
import com.duro.edc_koko.entity.product.domain.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    static Specification<Product> withCategory (List<String> categories) {
        return ProductSpecifications.hasCategory(categories);
    }

    static Specification<Product> withPriceLessThan (double price) {
        return ProductSpecifications.hasPriceLessThan(price);
    }

    static Specification<Product> withPriceGreaterThan (double price) {
        return ProductSpecifications.hasPriceGreaterThan(price);
    }

    static Specification<Product> withSimilarName (String name) {
        return ProductSpecifications.hasSimilarName(name);
    }

    static Specification<Product> orderBy (String orderBy) {
        return ProductSpecifications.withSort(orderBy);
    }

    Product findFirstByCategory (Category category);

    @Query("select p from Product p where upper(p.category.name) like upper(?1)")
    List<Product> findByCategoryName (String name);

    @Query("select p from Product p where upper(p.category.name) in ?1")
    List<Product> findByCategory_NameIn (Collection<String> names);

    @Query("select count(p) from Product p")
    int countAllProducts ();

    @Query("select count(distinct p) from Product p where p.category.id = ?1")
    int countDistinctByCategory (int id);
}
