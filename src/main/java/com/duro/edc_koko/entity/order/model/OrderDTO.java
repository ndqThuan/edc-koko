package com.duro.edc_koko.entity.order.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Integer id;

    @NotNull
    private Integer quantity;

    private Integer user;

    @NotNull
    @OrderProductUnique
    private Integer product;

}