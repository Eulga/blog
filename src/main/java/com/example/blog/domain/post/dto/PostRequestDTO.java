package com.example.blog.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostRequestDTO {

    @NotBlank(message = "제목은 공백이 불가능 합니다.")
    private final String title;

    private final String content;

    public PostRequestDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
