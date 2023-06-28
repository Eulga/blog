package com.example.bloghw2.post.repository;

import com.example.bloghw2.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByOrderByCreatedDateDesc();
}
