package com.example.linkly.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class CommentException extends BaseException {
    public CommentException(String message, HttpStatus status) {
        super(message,status);
    }
    public CommentException(String message, HttpStatus status, List<String> errorField) {
        super(message,status,errorField);
    }
}
