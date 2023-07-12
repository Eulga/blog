package com.example.blog.domain.comment.exception;

public class CommentPermissionException extends RuntimeException{
    public CommentPermissionException(String message) {
        super(message);
    }
}
