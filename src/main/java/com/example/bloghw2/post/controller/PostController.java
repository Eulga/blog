package com.example.bloghw2.post.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.bloghw2.comment.dto.CommentResponseDTO;
import com.example.bloghw2.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bloghw2.jwtutil.LoginUser;
import com.example.bloghw2.post.dto.PostRequestDTO;
import com.example.bloghw2.post.dto.PostResponseDTO;
import com.example.bloghw2.post.service.PostService;

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
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO postRequestDTO, @LoginUser String username) {
        PostResponseDTO response = postService.createPost(postRequestDTO, username);
        setCommentListInPostResponseDTO(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 전체 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> response = postService.getPosts();
        for (PostResponseDTO prsd : response) {
            setCommentListInPostResponseDTO(prsd);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 게시글 지정 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable("postId") Long postId) {
        PostResponseDTO response = postService.getPost(postId);
        setCommentListInPostResponseDTO(response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 게시글 수정
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> modifyPost(@PathVariable("postId") Long postId,@Valid @RequestBody PostRequestDTO postRequestDTO
    , @LoginUser String username) {
        PostResponseDTO response = postService.modifyPost(postId, postRequestDTO, username);
        setCommentListInPostResponseDTO(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Map<String,String>> deletePost(@PathVariable("postId") Long postId, @LoginUser String username){
        Map<String, String> response = postService.deletePost(postId, username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 댓글 목록 주입
    private void setCommentListInPostResponseDTO(PostResponseDTO prsd) {
        List<CommentResponseDTO> commentList = commentService.getCommentsByPostId(prsd.getPostId());
        prsd.setCommentList(commentList.isEmpty() ? new ArrayList<>() : commentList);
    }
}
