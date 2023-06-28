package com.example.bloghw2.post.Exception;

public class PermissionException extends RuntimeException{
    public PermissionException(String message) {
        super(message);
    }
}
