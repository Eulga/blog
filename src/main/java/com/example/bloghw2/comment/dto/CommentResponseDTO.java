package com.example.bloghw2.comment.dto;

import com.example.bloghw2.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDTO {
    private final Long commentId;
    private final String content;
    private final LocalDateTime createDate;
    private final String username;

    public CommentResponseDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.createDate = comment.getCreatedDate();
        this.username = comment.getUser().getUsername();
    }
}
