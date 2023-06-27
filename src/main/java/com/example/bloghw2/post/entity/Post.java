package com.example.bloghw2.post.entity;

import java.time.LocalDateTime;

import com.example.bloghw2.post.dto.PostRequestDTO;
import com.example.bloghw2.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotBlank(message = "title input error")
    private String title;

    private String contents;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    private Post(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public static Post createPost(String title, String contents, User user) {
        return Post.builder()
                .title(title)
                .contents(contents)
                .user(user)
                .build();
    }

    private void setUser(User user) {
        this.user = user;
    }

    public void modifyPost(String title, String contents){
        this.title = title;
        this.contents = contents;
    }
}
