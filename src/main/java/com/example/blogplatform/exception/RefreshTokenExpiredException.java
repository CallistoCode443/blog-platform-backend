package com.example.blogplatform.exception;

public class RefreshTokenExpiredException extends RefreshTokenException {
    public RefreshTokenExpiredException(String message) {
        super(message);
    }
}
