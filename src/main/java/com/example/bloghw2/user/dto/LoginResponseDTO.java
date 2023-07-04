package com.example.bloghw2.user.dto;

import lombok.Getter;

@Getter
public class LoginResponseDTO {

    private final String success;
    private final int status;


    public LoginResponseDTO(String success, int status) {
        this.success = success;
        this.status = status;
    }
}
