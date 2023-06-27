package com.example.bloghw2.user.dto;

import lombok.Getter;

@Getter
public class LoginResponseDTO {

    private String success;

    private int status;

    private String jwt;

    public LoginResponseDTO(String success, int status, String jwt) {
        this.success = success;
        this.status = status;
        this.jwt = jwt;
    }
}
