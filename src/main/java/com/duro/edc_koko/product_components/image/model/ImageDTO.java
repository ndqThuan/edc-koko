package com.duro.edc_koko.product_components.image.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ImageDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String url;

    private Integer product;

}
