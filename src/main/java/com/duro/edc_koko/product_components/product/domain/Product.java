package com.duro.edc_koko.product_components.product.domain;

import com.duro.edc_koko.product_components.category.domain.Category;
import com.duro.edc_koko.product_components.comment.domain.Comment;
import com.duro.edc_koko.product_components.image.domain.Image;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<Image> images;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private Comment comment;

    public String getImageUrl() {
        Iterator iterator = images.iterator();

        if (iterator.hasNext()) {
            return ((Image) iterator).getUrl();
        }

        return null;
    }
}
