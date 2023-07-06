package com.example.bloghw2.domain.user.dto;

import lombok.Getter;

@Getter
public class SignupResponseDTO {

    private final String success;
    private final int status;

    public SignupResponseDTO(String success, int status) {
        this.success = success;
        this.status = status;
    }
}
