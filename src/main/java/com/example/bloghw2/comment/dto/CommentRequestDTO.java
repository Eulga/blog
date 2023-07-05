package com.example.bloghw2.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDTO {

    private final Long postId;

    @NotBlank(message = "댓글은 공백이 불가능 합니다.")
    private final String content;

    public CommentRequestDTO(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }
}
