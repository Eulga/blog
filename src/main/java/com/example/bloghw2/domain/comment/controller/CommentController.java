package com.example.bloghw2.domain.comment.controller;

import com.example.bloghw2.domain.comment.dto.CommentRequestDTO;
import com.example.bloghw2.domain.comment.dto.CommentResponseDTO;
import com.example.bloghw2.domain.comment.service.CommentService;
import com.example.bloghw2.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommentResponseDTO response = commentService.createComment(commentRequestDTO, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> modifyComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("commentId") Long commentId) {
        CommentResponseDTO response = commentService.modifyComment(commentId, commentRequestDTO,  userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("commentId") Long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(commentId, userDetails.getUsername()));
    }


}
