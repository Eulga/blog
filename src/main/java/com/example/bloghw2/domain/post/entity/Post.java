package com.example.bloghw2.domain.post.entity;

import com.example.bloghw2.domain.comment.entity.Comment;
import com.example.bloghw2.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "title input error")
    private String title;

    @Lob
    @Column(columnDefinition = "text")
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "post", fetch = LAZY, cascade = CascadeType.ALL)
    @OrderBy("createdDate desc ")
    private List<Comment> commentList;

    @Transient
    private int likeCount;
    public int getLikeCount() {
        return likeCount;
    }
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    private Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void modifyPost(String title, String content){
        this.title = title;
        this.content = content;
    }
}
