package com.example.linkly.service.auth;

import com.example.linkly.dto.login.LoginRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {
    ResponseEntity<Map<String, String>> apiLogin(LoginRequestDto loginRequestDto);
    void login(LoginRequestDto loginRequestDto, HttpServletResponse response);
    void logout(HttpServletResponse response);
}
