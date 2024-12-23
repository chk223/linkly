package com.example.linkly.exception.handler;


import com.example.linkly.util.exception.ExceptionUtil;
import com.example.linkly.exception.AuthException;
import com.example.linkly.exception.util.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
@ControllerAdvice
@Slf4j
public class AuthExceptionHandler extends BaseExceptionHandler{
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(AuthException e) {
        log.info("AuthExceptionHandler: AuthException 처리 중");
        List<String> errorField = e.getErrorField();
        if (errorField != null && !errorField.isEmpty()) {
            return super.handleException(e, errorField);  // 검증 예외 처리
        }
        // 로그인 오류에 대한 특화된 처리

        return ExceptionUtil.GenerateResponseEntity(e.getStatus().value(), e.getMessage(), e.getStatus());
    }
}
