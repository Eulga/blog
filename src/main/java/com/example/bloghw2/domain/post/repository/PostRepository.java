package com.example.bloghw2.domain.post.repository;

import com.example.bloghw2.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByOrderByCreatedDateDesc();
}
