package com.example.bloghw2.domain.comment.exception;

public class CommentPermissionException extends RuntimeException{
    public CommentPermissionException(String message) {
        super(message);
    }
}
