package com.example.bloghw2.post.dto;

import com.example.bloghw2.post.entity.Post;

import lombok.Getter;

@Getter
public class PostRequestDTO {

    private final String title;

    private final String contents;

    private final String author;

    private final String password;

    public PostRequestDTO(String title, String contents, String author, String password) {
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.password = password;
    }

    public Post toEntity(){
        return Post.builder()
            .title(title)
            .contents(contents)
            .author(author)
            .password(password)
            .build();
    }
}
