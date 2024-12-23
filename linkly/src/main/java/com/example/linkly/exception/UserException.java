package com.example.linkly.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class UserException extends BaseException {
    public UserException(String message, HttpStatus status) {
      super(message,status);
    }
    public UserException(String message, HttpStatus status, List<String> errorField) {
      super(message,status,errorField);
    }
}
