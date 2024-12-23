package com.example.linkly.service.auth;

import com.example.linkly.dto.login.LoginRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {
    ResponseEntity<Map<String, String>> login(LoginRequestDto loginRequestDto);
}
