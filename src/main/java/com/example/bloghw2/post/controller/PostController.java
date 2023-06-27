package com.example.bloghw2.post.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloghw2.post.Exception.PasswordMismatchException;
import com.example.bloghw2.post.Exception.PostNotFoundException;
import com.example.bloghw2.post.dto.PostRequestDTO;
import com.example.bloghw2.post.dto.PostResponseDTO;
import com.example.bloghw2.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestDTO request) {
        PostResponseDTO response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> response = postService.getPosts();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable("postId") Long postId) {
        PostResponseDTO response = postService.getPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> modifyPost(@PathVariable("postId") Long postId, @RequestBody PostRequestDTO request) {
        PostResponseDTO response = postService.modifyPost(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Map<String,String>> deletePost(@PathVariable("postId") Long postId, @RequestBody Map<String,String> password){
        Map<String, String> response = postService.deletePost(postId, password.get("password"));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,String>> handleException(Exception ex){
        Map<String, String> result = Collections.singletonMap("success", "false");
        if (ex instanceof PasswordMismatchException){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        } else if (ex instanceof PostNotFoundException){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
