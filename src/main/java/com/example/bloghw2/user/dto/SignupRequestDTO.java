package com.example.bloghw2.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDTO {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]+$")
    private final String username;

    //특수문자 정규표현식 https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
    @Size(min=8, max=15)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,15}$")
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
