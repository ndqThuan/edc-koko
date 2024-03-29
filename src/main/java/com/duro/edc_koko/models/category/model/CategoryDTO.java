package com.duro.edc_koko.models.category.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategoryDTO {

    private Integer id;

    @NotNull
    @Size(max = 64)
    @CategoryNameUnique
    private String name;

}
