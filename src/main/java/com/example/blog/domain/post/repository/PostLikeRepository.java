package com.example.blog.domain.post.repository;

import com.example.blog.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
    int countByPostId(Long postId);
}