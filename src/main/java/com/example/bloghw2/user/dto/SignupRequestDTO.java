package com.example.bloghw2.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDTO {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z]+[0-9]+$")
    private final String username;

    @Size(min=8, max=15)
    @Pattern(regexp = "^[a-zA-Z]+[0-9]+[\\_!#\\$%&'\\*\\+/=\\?`\\{\\|\\}~\\^\\.\\-]+$")
    private final String password;

    private boolean admin = false;
    private String adminToken = "";

    public SignupRequestDTO(String username, String password, boolean admin, String adminToken) {
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.adminToken = adminToken;
    }
}
