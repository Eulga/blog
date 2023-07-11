package com.example.bloghw2.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponseDTO {
    private int statusCode;
    private String message;
}
