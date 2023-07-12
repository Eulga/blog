package com.example.blog.domain.post.controller;

import java.util.List;
import java.util.Map;

import com.example.blog.domain.comment.service.CommentService;
import com.example.blog.domain.post.service.PostService;
import com.example.blog.global.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.blog.domain.post.dto.PostRequestDTO;
import com.example.blog.domain.post.dto.PostResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    // 게시글 생성
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO postRequestDTO,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDTO response = postService.createPost(postRequestDTO, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 전체 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> response = postService.getPosts();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 게시글 지정 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable("postId") Long postId) {
        PostResponseDTO response = postService.getPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 게시글 수정
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> modifyPost(@PathVariable("postId") Long postId
                                                    ,@Valid @RequestBody PostRequestDTO postRequestDTO
                                                    ,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDTO response = postService.modifyPost(postId, postRequestDTO, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Map<String,String>> deletePost(@PathVariable("postId") Long postId
                                                        ,  @AuthenticationPrincipal UserDetailsImpl userDetails){
        Map<String, String> response = postService.deletePost(postId, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
