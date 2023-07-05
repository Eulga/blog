package com.example.bloghw2.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequestDTO {
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{4,10}$")
    private final String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[₩\\]^_`{|}~]).{8,15}$")
    private final String password;

    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
