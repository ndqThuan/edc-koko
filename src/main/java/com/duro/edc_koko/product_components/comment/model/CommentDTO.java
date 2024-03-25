package com.duro.edc_koko.product_components.comment.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String content;

    @NotNull
    @CommentUserUnique
    private Integer user;

    private String username;

    @NotNull
    @CommentProductUnique
    private Integer product;

}
