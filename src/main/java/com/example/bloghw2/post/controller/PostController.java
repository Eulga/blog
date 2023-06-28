package com.example.bloghw2.post.controller;

import com.example.bloghw2.jwtutil.LoginUser;
import com.example.bloghw2.post.dto.PostRequestDTO;
import com.example.bloghw2.post.dto.PostResponseDTO;
import com.example.bloghw2.post.service.PostService;
import com.example.bloghw2.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;


    // 게시글 생성
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO postRequestDTO, @LoginUser User user) {
        String username = user.getUsername();
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
    , @LoginUser User user) {
        String username = user.getUsername();
        PostResponseDTO response = postService.modifyPost(postId, postRequestDTO, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Map<String,String>> deletePost(@PathVariable("postId") Long postId, @LoginUser User user){
        String username = user.getUsername();
        Map<String, String> response = postService.deletePost(postId, username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
