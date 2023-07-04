package com.example.bloghw2.user.dto;

import lombok.Getter;

@Getter
public class LoginResponseDTO {

    private String success;

    private int status;


    public LoginResponseDTO(String success, int status) {
        this.success = success;
        this.status = status;
    }
}
