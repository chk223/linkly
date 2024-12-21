package com.example.linkly.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class LikeException extends BaseException {
    public LikeException(String message, HttpStatus status) {
        super(message,status);
    }
    public LikeException(String message, HttpStatus status, List<String> errorField) {
        super(message,status,errorField);
    }
}
