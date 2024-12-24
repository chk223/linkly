package com.example.linkly.controller;

import com.example.linkly.dto.login.LoginRequestDto;
import com.example.linkly.exception.AuthException;
import com.example.linkly.service.auth.AuthService;
import com.example.linkly.util.auth.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public AuthController(JwtUtil jwtUtil, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info("토큰 발급 시도");
        return authService.apiLogin(loginRequestDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestParam String refreshToken) {
        if (jwtUtil.validateRefreshToken(refreshToken)) {
            String userEmail = jwtUtil.extractUsername(refreshToken);  // 기존 이메일을 추출
            String newAccessToken = jwtUtil.generateAccessToken(userEmail);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);

            return ResponseEntity.ok(tokens);  // HTTP 200 OK와 함께 새 accessToken 반환
        } else {
            throw new AuthException("Invalid refresh token", HttpStatus.UNAUTHORIZED);
        }
    }
}
