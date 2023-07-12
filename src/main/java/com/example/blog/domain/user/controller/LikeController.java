package com.example.blog.domain.user.controller;

import com.example.blog.domain.user.dto.LikeResponseDTO;
import com.example.blog.domain.user.service.LikeService;
import com.example.blog.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<LikeResponseDTO> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.togglePostLike(postId, userDetails.getUser().getId());
    }

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<LikeResponseDTO> likeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.toggleCommentLike(commentId, userDetails.getUser().getId());
    }
}