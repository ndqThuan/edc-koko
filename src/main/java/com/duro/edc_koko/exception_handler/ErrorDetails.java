package com.duro.edc_koko.exception_handler;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorDetails extends NullPointerException {
    private Date encounterTime;
    private String message;
}
