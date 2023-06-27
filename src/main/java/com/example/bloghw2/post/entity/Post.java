package com.example.bloghw2.post.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    private String contents;

    private String author;

    private String password;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    private Post(String title, String contents, String author, String password) {
        this.title = title;
        this.contents = contents;
        this.author = author;
        this.password = password;
    }

    public void modifyPost(String title, String author, String contents){
        this.title = title;
        this.author = author;
        this.contents = contents;
    }
}
