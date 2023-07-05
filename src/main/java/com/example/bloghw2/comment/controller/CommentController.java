package com.example.bloghw2.comment.controller;

import com.example.bloghw2.comment.dto.CommentRequestDTO;
import com.example.bloghw2.comment.dto.CommentResponseDTO;
import com.example.bloghw2.comment.service.CommentService;
import com.example.bloghw2.jwtutil.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO,
                                                      @LoginUser String username) {
        CommentResponseDTO response = commentService.createComment(commentRequestDTO, username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> modifyComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO,
                                                            @LoginUser String username, @PathVariable("commentId") Long commentId) {
        CommentResponseDTO response = commentService.modifyComment(commentId, commentRequestDTO, username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
