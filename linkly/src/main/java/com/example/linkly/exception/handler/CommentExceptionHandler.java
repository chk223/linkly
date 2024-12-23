package com.example.linkly.exception.handler;

import com.example.linkly.util.exception.ExceptionUtil;
import com.example.linkly.exception.CommentException;
import com.example.linkly.exception.util.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
@ControllerAdvice
public class CommentExceptionHandler extends BaseExceptionHandler{
    @ExceptionHandler(CommentException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(CommentException e) {
        List<String> errorField = e.getErrorField();
        if (errorField != null && !errorField.isEmpty()) {
            return super.handleException(e, errorField);  // 검증 예외 처리
        }
        // 로그인 오류에 대한 특화된 처리

        return ExceptionUtil.GenerateResponseEntity(e.getStatus().value(), e.getMessage(), e.getStatus());
    }
}
