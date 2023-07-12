package com.example.blog.domain.user.service;

import com.example.blog.domain.comment.entity.Comment;
import com.example.blog.domain.comment.entity.CommentLike;
import com.example.blog.domain.comment.exception.CommentNotFoundException;
import com.example.blog.domain.comment.repository.CommentLikeRepository;
import com.example.blog.domain.comment.repository.CommentRepository;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.entity.PostLike;
import com.example.blog.domain.post.exception.PostNotFoundException;
import com.example.blog.domain.post.repository.PostLikeRepository;
import com.example.blog.domain.post.repository.PostRepository;
import com.example.blog.domain.user.dto.LikeResponseDTO;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.exception.UserNotFoundException;
import com.example.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;


    public ResponseEntity<LikeResponseDTO> togglePostLike(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post with id " + postId + " not found."));

        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndUserId(postId, userId);

        if (postLike.isPresent()) {
            postLikeRepository.delete(postLike.get());
            return ResponseEntity.status(201).body(new LikeResponseDTO(200, "게시글 좋아요 취소"));
        } else {
            postLikeRepository.save(new PostLike(user, post));
            return ResponseEntity.status(201).body(new LikeResponseDTO(200, "게시글 좋아요 완료"));
        }

    }


    public ResponseEntity<LikeResponseDTO> toggleCommentLike(Long commentId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment with id " + commentId + " not found."));

        Optional<CommentLike> commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, userId);

        if (commentLike.isPresent()) {
            commentLikeRepository.delete(commentLike.get());
            return ResponseEntity.status(201).body(new LikeResponseDTO(200, "댓글 좋아요 취소"));
        } else {
            commentLikeRepository.save(new CommentLike(user, comment));
            return ResponseEntity.status(201).body(new LikeResponseDTO(200, "댓글 좋아요 완료"));
        }

    }

}