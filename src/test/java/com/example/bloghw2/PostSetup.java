package com.example.bloghw2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.bloghw2.post.repository.PostRepository;

@Component
public class PostSetup {

    @Autowired
    private PostRepository postRepository;

    public void clearPosts(){
        postRepository.deleteAll();
    }
}
