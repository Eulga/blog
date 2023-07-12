package com.example.blog.domain.user.exception;

public class AdminTokenMismatchException extends RuntimeException{
    public AdminTokenMismatchException(String message) {
        super(message);
    }
}
