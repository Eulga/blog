package com.example.bloghw2.jwtutil;

public class TokenValidException extends RuntimeException{
    public TokenValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
