package com.example.bloghw2.post.dto;

import com.example.bloghw2.comment.dto.CommentResponseDTO;
import com.example.bloghw2.comment.entity.Comment;
import com.example.bloghw2.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDTO {

    private final Long postId;

    private final String title;

    private final String author;

    private final String contents;

    private final LocalDateTime createdDate;

    private List<CommentResponseDTO> commentList;

    public PostResponseDTO(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.author = post.getUser().getUsername();
        this.contents = post.getContents();
        this.createdDate = post.getCreatedDate();
    }

    public void setCommentList(List<CommentResponseDTO> commentList) {
        this.commentList = commentList;
    }
}
