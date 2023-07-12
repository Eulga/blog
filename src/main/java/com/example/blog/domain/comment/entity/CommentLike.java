package com.example.blog.domain.comment.entity;

import com.example.blog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "comment_likes", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "comment_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}