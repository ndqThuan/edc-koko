package com.duro.edc_koko.auth.model.e_num;

import lombok.Getter;

@Getter
public enum TokenType {
    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token");

    private final String label;

    TokenType(String label) {
        this.label = label;
    }

}
