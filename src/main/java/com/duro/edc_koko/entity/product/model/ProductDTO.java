package com.duro.edc_koko.entity.product.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
public class ProductDTO implements Serializable {

    private Integer id;

    @NotNull
    @Size(max = 64)
    private String name;

    @NotNull
    @Digits(integer = 15, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;

    @NotNull
    private Boolean available;

    private ProductTrend trend;

    @NotNull
    private String category;

    private List<String> imageUrl = new ArrayList<>();

    public void addImageUrl (String url) {
        this.imageUrl.add(url);
    }
}
