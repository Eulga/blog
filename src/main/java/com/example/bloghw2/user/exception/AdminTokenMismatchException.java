package com.example.bloghw2.user.exception;

public class AdminTokenMismatchException extends RuntimeException{
    public AdminTokenMismatchException(String message) {
        super(message);
    }
}
