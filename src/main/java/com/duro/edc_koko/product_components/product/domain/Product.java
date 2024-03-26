package com.duro.edc_koko.product_components.product.domain;

import com.duro.edc_koko.product_components.category.domain.Category;
import com.duro.edc_koko.product_components.comment.domain.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name = "Products")
@Getter
@Setter
public class Product {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, name = "\"description\"")
    private String description;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean available;

    @OneToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    @OneToOne(mappedBy = "product")
    private Comment comment;

}
