package com.example.linkly.common.exception;

import com.example.linkly.common.exception.util.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ApiException extends BaseException {
  public ApiException(String message, HttpStatus status) {
    super(message,status);
  }
  public ApiException(String message, HttpStatus status, List<String> errorField, ErrorResponse response) {
    super(message,status,errorField,response);
  }
}
