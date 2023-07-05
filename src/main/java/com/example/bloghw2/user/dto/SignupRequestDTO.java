package com.example.bloghw2.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDTO {

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{4,10}$", message = ("영문과 숫자 조합으로 작성하셔야 합니다."))
    private final String username;

    //특수문자 정규표현식 https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[₩\\]^_`{|}~]).{8,15}$")
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
