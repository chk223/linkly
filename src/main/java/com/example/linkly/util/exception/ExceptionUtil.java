package com.example.linkly.util.exception;

import com.example.linkly.exception.BaseException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.exception.util.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@Slf4j
public class ExceptionUtil {
    public static ResponseEntity<ErrorResponse> GenerateResponseEntity(int status, String message, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(status, message);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
    public static ResponseEntity<ErrorResponse> GenerateResponseEntity(int status, String message, int code, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(status, message, code);
        log.info("에러코드 : {}", code);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
    public static <T extends BaseException> T  throwErrorMessage(ErrorMessage errorMessage, Class<T> exceptionClass) {
        try{
            return exceptionClass.getConstructor(String.class, HttpStatus.class)
                    .newInstance(errorMessage.getMessage(), errorMessage.getStatus());
        } catch (ReflectiveOperationException e){
            throw new IllegalArgumentException("예외 객체 생성 오류 발생!! 오류 예외 : " + exceptionClass.getName(), e);
        }
    }
}
