package com.duro.edc_koko.auth.model;

import com.duro.edc_koko.auth.model.e_num.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String fullname;
    private String username;
    private String password;
    private Role role = Role.USER;
}
