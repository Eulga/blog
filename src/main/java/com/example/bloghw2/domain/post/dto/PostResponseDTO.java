package com.example.bloghw2.domain.post.dto;

import com.example.bloghw2.domain.comment.dto.CommentResponseDTO;
import com.example.bloghw2.domain.comment.entity.Comment;
import com.example.bloghw2.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDTO {

    private final Long postId;

    private final String title;

    private final String username;

    private final String content;

    private final LocalDateTime createdDate;

    private List<CommentResponseDTO> commentList;

    public PostResponseDTO(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        if (!(post.getCommentList() == null)) setCommentList(post.getCommentList());
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList.stream().map(CommentResponseDTO::new).toList();
    }
}
