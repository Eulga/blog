package com.example.bloghw2.domain.user.exception;

public class UserDuplicationException extends RuntimeException {
    public UserDuplicationException(String message) {
        super(message);
    }
}
