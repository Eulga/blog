package com.example.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.repository.PostRepository;
import com.example.blog.domain.user.entity.User;

@Component
public class PostSetup {

    @Autowired
    private PostRepository postRepository;

    public void clearPosts() {
        postRepository.deleteAll();
    }

    public Post savePosts(User user) {
        Post post = Post.builder()
            .title("title1")
            .content("content")
            .user(user)
            .build();
        Post savedPost = postRepository.save(post);
        return savedPost;
    }
}
