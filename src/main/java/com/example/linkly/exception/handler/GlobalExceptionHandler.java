package com.example.linkly.exception.handler;

import com.example.linkly.exception.ApiException;
import com.example.linkly.util.exception.ExceptionUtil;
import com.example.linkly.exception.util.ValidationErrorCode;
import com.example.linkly.exception.util.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseExceptionHandler{

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(ApiException e) {
        log.info("AuthExceptionHandler: ApiException 처리 중");
        List<String> errorField = e.getErrorField();
        if (errorField != null && !errorField.isEmpty()) {
            return super.handleException(e, errorField);  // 검증 예외 처리
        }
        //api 오류 처리
        return ExceptionUtil.GenerateResponseEntity(e.getStatus().value(), e.getMessage(), e.getStatus());
    }
    /**
     * 그 외의 예외 처리
     * @param e 잡은 예외 객체
     * @return 에외 던지기
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        log.info("GlobalExceptionHandler: GlobalException 처리 중 에러 ={}",e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(500, "예상치 못한 오류가 발생했습니다.");
        return ExceptionUtil.GenerateResponseEntity(ValidationErrorCode.GENERAL_ERROR.getCode(), ValidationErrorCode.GENERAL_ERROR.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
