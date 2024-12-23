package com.example.linkly.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class FriendException extends BaseException {
    public FriendException(String message, HttpStatus status) {
        super(message,status);
    }
    public FriendException(String message, HttpStatus status, List<String> errorField) {
        super(message,status,errorField);
    }
}
