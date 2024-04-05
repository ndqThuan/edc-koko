package com.duro.edc_koko.entity.image.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    @NotNull
    @Size(max = 255)
    private String url;

    private Integer product;

}
