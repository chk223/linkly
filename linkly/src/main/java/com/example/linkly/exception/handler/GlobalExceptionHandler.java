package com.example.linkly.exception.handler;

import com.example.linkly.common.exception.ExceptionUtil;
import com.example.linkly.exception.util.ValidationErrorCode;
import com.example.linkly.exception.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
/**
 * API 예외 처리(커스텀)
 */
public class GlobalExceptionHandler {

    /**
     * 그 외의 예외 처리
     * @param e 잡은 예외 객체
     * @return 에외 던지기
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(500, "예상치 못한 오류가 발생했습니다.");
        return ExceptionUtil.GenerateResponseEntity(ValidationErrorCode.GENERAL_ERROR.getCode(), ValidationErrorCode.GENERAL_ERROR.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
