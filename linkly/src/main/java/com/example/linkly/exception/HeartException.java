package com.example.linkly.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class HeartException extends BaseException {
    public HeartException(String message, HttpStatus status) {
        super(message,status);
    }
    public HeartException(String message, HttpStatus status, List<String> errorField) {
        super(message,status,errorField);
    }
}
