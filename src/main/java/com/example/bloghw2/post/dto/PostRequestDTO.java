package com.example.bloghw2.post.dto;

import com.example.bloghw2.post.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDTO {

    @NotBlank(message = "제목은 공백이 불가능 합니다.")
    private final String title;

    private final String contents;

    public PostRequestDTO(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Post toEntity(){
        return Post.builder()
            .title(title)
            .contents(contents)
            .build();
    }
}
