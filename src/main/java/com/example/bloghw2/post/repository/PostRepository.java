package com.example.bloghw2.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bloghw2.post.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByOrderByCreatedDateDesc();
    Optional<Post> findPostByPostIdAndUserId(long postId, long userId);
}
