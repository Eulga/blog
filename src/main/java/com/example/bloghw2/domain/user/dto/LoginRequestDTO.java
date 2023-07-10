package com.example.bloghw2.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequestDTO {
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])"
            + "[0-9a-z]{4,10}$"
            , message = "유저명 규칙에 어긋남")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!\"#$%&'()*+,\\-./:;<=>?@\\[₩\\]^_`{|}~])"
            + "[0-9a-zA-Z!\"#$%&'()*+,\\-./:;<=>?@\\[₩\\]^_`{|}~]{8,15}$"
            , message = "비밀번호 규칙에 어긋남")
    private String password;
}