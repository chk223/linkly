package com.example.linkly.common.exception;

import com.example.linkly.exception.util.ErrorMessageGenerator;
import com.example.linkly.exception.util.ValidationErrorCode;

import java.util.List;

public class ValidationMessageHandler {
    public String getCombinedValidationMessage(List<String> errorField, List<String> validFields) {
        int combinedErrorCode = 0;
        // errorField에 따라 비트 연산으로 오류 코드 합산
        for (String field : errorField) {
            if (validFields.contains(field)) {  // 필터링된 필드만 처리
                switch (field) {
                    case "id":
                        combinedErrorCode |= ValidationErrorCode.INVALID_ID.getCode();
                        break;
                    case "password":
                        combinedErrorCode |= ValidationErrorCode.INVALID_PASSWORD.getCode();
                        break;
                    case "email":
                        combinedErrorCode |= ValidationErrorCode.INVALID_EMAIL.getCode();
                        break;
                    case "comment":
                        combinedErrorCode |= ValidationErrorCode.INVALID_COMMENT.getCode();
                        break;
                    default:
                        break;
                }
            }
        }

        // 비트 연산된 오류 코드로 메시지 생성
        return ErrorMessageGenerator.generateErrorMessage(combinedErrorCode);
    }
}
