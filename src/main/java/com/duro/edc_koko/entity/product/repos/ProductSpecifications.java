package com.duro.edc_koko.entity.product.repos;

import com.duro.edc_koko.entity.category.domain.Category;
import com.duro.edc_koko.entity.product.domain.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecifications {
    public static Specification<Product> hasCategory (List<Category> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories == null || categories.isEmpty()) {
                return null; // Return null if no categories are provided
            }

            // Initialize an empty list to hold predicates for each category
            List<Predicate> predicates = new ArrayList<>();
            for (Category category : categories) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }

            // Combine predicates with OR
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Product> hasPriceLessThan (double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("price"), price);
    }

    public static Specification<Product> hasPriceGreaterThan (double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("price"), price);
    }

    public static Specification<Product> hasSimilarName (String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), name);
    }
    
    public static Sort orderBy (String orderBy) {
        return switch (orderBy) {
            case "name_asc" -> Sort.by(Sort.Direction.ASC, "name");
            case "price_asc" -> Sort.by(Sort.Direction.ASC, "price");
            default -> throw new IllegalArgumentException("Invalid sorting order: " + orderBy);
        };
    }
}
