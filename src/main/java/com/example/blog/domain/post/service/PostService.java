package com.example.blog.domain.post.service;

import com.example.blog.domain.post.dto.PostRequestDTO;
import com.example.blog.domain.post.dto.PostResponseDTO;

import java.util.List;
import java.util.Map;

public interface PostService {

    PostResponseDTO createPost(PostRequestDTO postRequestDTO, String username);

    List<PostResponseDTO> getPosts();

    PostResponseDTO getPost(Long postId);

    PostResponseDTO modifyPost(Long postId, PostRequestDTO postRequestDTO, String username);

    Map<String,String> deletePost(Long postId, String username);
}
