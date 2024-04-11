package com.duro.edc_koko.entity.product.repos;

import com.duro.edc_koko.entity.product.domain.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecifications {
    public static Specification<Product> hasCategory (List<String> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories == null || categories.isEmpty()) {
                return null; // Return null if no categories are provided
            }

            // Initialize an empty list to hold predicates for each category name
            List<Predicate> predicates = new ArrayList<>();
            for (String categoryName : categories) {
                categoryName = categoryName.replace("-", " ");
                // Use upper function to convert both sides to uppercase for case-insensitive comparison
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("category").get("name")),
                        categoryName.toUpperCase()
                                                   ));
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

    public static Specification<Product> withSort (String orderBy) {
        return (root, query, criteriaBuilder) -> {
            applySort(orderBy, query, root, criteriaBuilder);
            return criteriaBuilder.conjunction();
        };
    }

    private static void applySort (String orderBy, CriteriaQuery<?> query, Root<Product> root, CriteriaBuilder criteriaBuilder) {
        if (orderBy == null || orderBy.isEmpty()) {
            return;
        }

        String[] parts = orderBy.split("-");
        String property = parts[0];
        Sort.Direction direction = Sort.Direction.valueOf(parts[1].toUpperCase());

        query.orderBy(direction == Sort.Direction.ASC ? criteriaBuilder.asc(root.get(property)) : criteriaBuilder.desc(root.get(property)));
    }
}
