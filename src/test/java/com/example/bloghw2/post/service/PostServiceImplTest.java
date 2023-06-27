package com.example.bloghw2.post.service;

import com.example.bloghw2.post.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceImplTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    @DisplayName("게시글 저장")
    void createPost() {

    }
}