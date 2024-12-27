package com.example.linkly.exception;

import com.example.linkly.exception.util.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus status;
    private final List<String> errorField;
    private final ErrorResponse errorResponse;
    public BaseException(String message, HttpStatus statusCode) {
        super(message);
        this.status = statusCode;
        this.errorResponse = new ErrorResponse(statusCode.value(),message); // 기본 초기화
        this.errorField = Collections.emptyList(); // 빈 리스트로 초기화
    }

    public BaseException(String message, HttpStatus statusCode, List<String> errorField, ErrorResponse errorResponse) {
        super(message);
        this.status = statusCode;
        this.errorField = errorField != null ? errorField : Collections.emptyList();
        this.errorResponse = errorResponse != null ? errorResponse : new ErrorResponse(statusCode.value(),message);
    }
}
