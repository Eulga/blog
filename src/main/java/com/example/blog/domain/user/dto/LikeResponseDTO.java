package com.example.blog.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponseDTO {
    private int statusCode;
    private String message;
}
