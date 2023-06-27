package com.example.bloghw2.post.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bloghw2.jwtutil.JwtUtil;
import com.example.bloghw2.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

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
    private final JwtUtil jwtUtil;


    // 게시글 생성
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO postRequestDTO, HttpServletRequest req) {
        String username = ((User) req.getAttribute("user")).getUsername();
        PostResponseDTO response = postService.createPost(postRequestDTO, username);
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
    public ResponseEntity<PostResponseDTO> modifyPost(@PathVariable("postId") Long postId,@Valid @RequestBody PostRequestDTO postRequestDTO
    , HttpServletRequest req) {
        String username = ((User) req.getAttribute("user")).getUsername();
        PostResponseDTO response = postService.modifyPost(postId, postRequestDTO, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Map<String,String>> deletePost(@PathVariable("postId") Long postId, HttpServletRequest req){
        String username = ((User) req.getAttribute("user")).getUsername();
        Map<String, String> response = postService.deletePost(postId, username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 예외 관리
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleException(MethodArgumentNotValidException ex, HttpStatus status){
        Map<String, Object> result = new HashMap<>();
        result.put("success", "false");
        result.put("message", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(status.value()).body(result);
    }
}
