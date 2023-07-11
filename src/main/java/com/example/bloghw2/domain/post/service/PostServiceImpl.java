package com.example.bloghw2.domain.post.service;

import com.example.bloghw2.domain.post.entity.Post;
import com.example.bloghw2.domain.post.exception.PostNotFoundException;
import com.example.bloghw2.domain.post.exception.PostPermissionException;
import com.example.bloghw2.domain.post.repository.PostRepository;
import com.example.bloghw2.domain.post.dto.PostRequestDTO;
import com.example.bloghw2.domain.post.dto.PostResponseDTO;
import com.example.bloghw2.domain.user.entity.UserRoleEnum;
import com.example.bloghw2.domain.user.exception.UserNotFoundException;
import com.example.bloghw2.domain.user.entity.User;
import com.example.bloghw2.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final MessageSource messageSource;

    // 게시글 생성
    @Transactional
    @Override
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Not Found User")
        );
        Post post = Post.builder()
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
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

        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!(post.getUser().getUserId().equals(user.getUserId()))) {
                throw new IllegalArgumentException();
            }
        }
        if (validationAuthority(user, post)) {
            post.modifyPost(postRequestDTO.getTitle(), postRequestDTO.getContent());
        } else {
            throw new IllegalArgumentException();
        }

        PostResponseDTO response = new PostResponseDTO(post);
        return response;
    }


    // 게시글 삭제
    @Transactional
    @Override
    public Map<String, String> deletePost(Long postId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Not Found User")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Not Found Post")
        );

        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!(post.getUser().getUserId().equals(user.getUserId()))) {
                throw new IllegalArgumentException();
            }
        }
        if (validationAuthority(user, post)) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException();
        }

        return new LinkedHashMap<>() {{
            put("success", "true");
            put("status", "200");
        }};
    }

    // 수정, 삭제시 권한 확인
    private boolean validationAuthority(User user, Post post) {
        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!(post.getUser().getUserId().equals(user.getUserId()))) {
                return false;
            }
        }
        return true;
    }
}
