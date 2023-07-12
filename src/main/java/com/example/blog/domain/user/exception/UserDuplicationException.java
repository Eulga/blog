package com.example.blog.domain.user.exception;

public class UserDuplicationException extends RuntimeException {
    public UserDuplicationException(String message) {
        super(message);
    }
}
