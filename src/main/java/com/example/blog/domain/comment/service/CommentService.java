package com.example.blog.domain.comment.service;

import com.example.blog.domain.comment.dto.CommentRequestDTO;
import com.example.blog.domain.comment.dto.CommentResponseDTO;

import java.util.List;
import java.util.Map;

public interface CommentService {

    List<CommentResponseDTO> getCommentsByPostId(Long postId);
    CommentResponseDTO createComment(CommentRequestDTO crqd, String username);

    CommentResponseDTO modifyComment(Long commentId, CommentRequestDTO crqd, String username);

    Map<String,String> deleteComment(Long commentId, String username);
}
