package com.example.bloghw2.post.service;

import com.example.bloghw2.post.exception.PermissionException;
import com.example.bloghw2.post.exception.PostNotFoundException;
import com.example.bloghw2.post.dto.PostRequestDTO;
import com.example.bloghw2.post.dto.PostResponseDTO;
import com.example.bloghw2.post.entity.Post;
import com.example.bloghw2.post.repository.PostRepository;
import com.example.bloghw2.user.exception.UserNotFoundException;
import com.example.bloghw2.user.entity.User;
import com.example.bloghw2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;


    // 게시글 생성
    @Transactional
    @Override
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Not Found User")
        );
        Post post = Post.builder()
                .title(postRequestDTO.getTitle())
                .contents(postRequestDTO.getContents())
                .user(user)
                .build();
        Post savedPost = postRepository.save(post);

        PostResponseDTO response = new PostResponseDTO(savedPost);
        return response;
    }


    // 게시글 전체 조회
    @Transactional(readOnly = true)
    @Override
    public List<PostResponseDTO> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedDateDesc();

        List<PostResponseDTO> response = posts.stream()
            .map(PostResponseDTO::new)
            .collect(Collectors.toList());
        return response;
    }


    // 게시글 지정 조회
    @Transactional(readOnly = true)
    @Override
    public PostResponseDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException("Not Found Post"));
        PostResponseDTO response = new PostResponseDTO(post);
        return response;
    }


    // 게시글 수정
    @Transactional
    @Override
    public PostResponseDTO modifyPost(Long postId, PostRequestDTO postRequestDTO, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Not Found User")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Not Found Post")
        );

        // 해당 유저가 쓴 포스트가 맞는지 검사
        if(!(post.getUser().getUserId() == user.getUserId())) {
            throw new PermissionException("Not The User's Post");
        }

        post.modifyPost(postRequestDTO.getTitle(), postRequestDTO.getContents());
        PostResponseDTO response = new PostResponseDTO(post);
        return response;
    }


    // 게시글 삭제
    @Transactional
    @Override
    public Map<String,String> deletePost(Long postId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Not Found User")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Not Found Post")
        );

        // 해당 유저가 쓴 포스트가 맞는지 검사
        if(!(post.getUser().getUserId() == user.getUserId())) {
            throw new PermissionException("Not The User's Post");
        }

        postRepository.delete(post);
        return Collections.singletonMap("success","true");
    }
}
