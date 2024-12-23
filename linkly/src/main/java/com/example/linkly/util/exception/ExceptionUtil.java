package com.example.linkly.util.exception;

import com.example.linkly.exception.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionUtil {
    public static ResponseEntity<ErrorResponse> GenerateResponseEntity(int status, String message, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(status, message);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
    public static ResponseEntity<ErrorResponse> GenerateResponseEntity(int status, String message, int code, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(status, message, code);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}