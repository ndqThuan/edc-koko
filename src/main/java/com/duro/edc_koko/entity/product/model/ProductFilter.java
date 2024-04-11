package com.duro.edc_koko.entity.product.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class ProductFilter {
    private List<String> categories = new ArrayList<>();
    private float min_price;
    private float max_price;
    private String orderBy;
    private String similarName;

    public boolean hasCategories () {
        return !this.categories.isEmpty();
    }

    public boolean hasMinPrice () {
        return this.min_price != 1.00;
    }

    public boolean hasMaxPrice () {
        return this.max_price != 999.00;
    }

    public boolean hasOrderBy () {
        return this.orderBy != null;
    }

    public boolean hasSimilarName () {
        return this.similarName != null;
    }
}
