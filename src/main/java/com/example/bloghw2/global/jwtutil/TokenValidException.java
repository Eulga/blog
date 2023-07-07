package com.example.bloghw2.global.jwtutil;

public class TokenValidException extends RuntimeException{
    public TokenValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
