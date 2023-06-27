package com.example.bloghw2.user.dto;

import lombok.Getter;

@Getter
public class BaseResponseDTO {

    private final String success;
    private final int status;

    public BaseResponseDTO(String success, int status) {
        this.success = success;
        this.status = status;
    }
}
