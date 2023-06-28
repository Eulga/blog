package com.example.bloghw2.global.config.dto;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ExceptionDTO {
    private final String success;
    private final int status;
    private final Map<String, String> errors;

    public ExceptionDTO(String success, int status, Map<String, String> errors) {
        this.success = success;
        this.status = status;
        this.errors = errors;
    }
}
