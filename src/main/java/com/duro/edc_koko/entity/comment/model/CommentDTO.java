package com.duro.edc_koko.entity.comment.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class CommentDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String content;

    private String username;

    private LocalDate date;

}
