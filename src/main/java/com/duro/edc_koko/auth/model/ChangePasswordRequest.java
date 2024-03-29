package com.duro.edc_koko.auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    @Value("${JDBC_URL}")
    private String jdbc;

    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}
