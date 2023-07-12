package com.example.blog.domain.post.exception;

public class PostPermissionException extends RuntimeException{
    public PostPermissionException(String message) {
        super(message);
    }
}
