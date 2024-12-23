package com.example.linkly.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class FeedException extends BaseException {
  public FeedException(String message, HttpStatus status) {
    super(message,status);
  }
  public FeedException(String message, HttpStatus status, List<String> errorField) {
    super(message,status,errorField);
  }
}
