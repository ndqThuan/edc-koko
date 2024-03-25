package com.duro.edc_koko.user.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Integer id;

    @NotNull
    @Size(max = 64)
    @UserEmailUnique
    private String email;

    @NotNull
    @Size(max = 32)
    private String username;

    @NotNull
    @Size(max = 255)
    private String password;

    @NotNull
    private Role role;

}
