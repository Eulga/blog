package com.example.blog.domain.post.service;

import com.example.blog.domain.comment.repository.CommentLikeRepository;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.exception.PostNotFoundException;
import com.example.blog.domain.post.repository.PostLikeRepository;
import com.example.blog.domain.post.repository.PostRepository;
import com.example.blog.domain.post.dto.PostRequestDTO;
import com.example.blog.domain.post.dto.PostResponseDTO;
import com.example.blog.domain.user.entity.UserRoleEnum;
import com.example.blog.domain.user.exception.UserNotFoundException;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.repository.UserRepository;
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
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

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

        posts.forEach(post -> {
            // 게시글 좋아요 수
            post.setLikeCount(postLikeRepository.countByPostId(post.getId()));

            // 댓글 좋아요 수
            post.getComments().forEach(comment ->
                    comment.setLikeCount(commentLikeRepository.countByCommentId(comment.getId())));
        });

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

        // 게시글 좋아요 수
        post.setLikeCount(postLikeRepository.countByPostId(post.getId()));

        // 댓글 좋아요 수
        post.getComments().forEach(comment ->
                comment.setLikeCount(commentLikeRepository.countByCommentId(comment.getId())));

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
            if (!(post.getUser().getId().equals(user.getId()))) {
                throw new IllegalArgumentException();
            }
        }

        if (validationAuthority(user, post)) {
            post.modifyPost(postRequestDTO.getTitle(), postRequestDTO.getContent());
        } else {
            throw new IllegalArgumentException();
        }

        // 게시글 좋아요 수
        post.setLikeCount(postLikeRepository.countByPostId(post.getId()));

        // 댓글 좋아요 수
        post.getComments().forEach(comment ->
                comment.setLikeCount(commentLikeRepository.countByCommentId(comment.getId())));

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
            if (!(post.getUser().getId().equals(user.getId()))) {
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
            if (!(post.getUser().getId().equals(user.getId()))) {
                return false;
            }
        }
        return true;
    }
}
