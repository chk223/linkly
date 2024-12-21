package com.example.linkly.exception.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessage {
    PASSWORD_IS_WRONG("비밀번호 입력이 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
    ENTITY_NOT_FOUND("선택한 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    BLANK_INPUT("입력 값은 비어있을 수 없습니다.", HttpStatus.BAD_REQUEST),
    VALID_ERROR("입력 값이 잘못되었습니다.",HttpStatus.BAD_REQUEST),
    DIFFERENT_USER("사용 권한이 없습니다.",HttpStatus.NOT_ACCEPTABLE);
    private final String message;
    private final HttpStatus status;

    ErrorMessage(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
