package com.example.linkly.controller.view;

import com.example.linkly.exception.AuthException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.util.auth.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthViewController {
    private final JwtUtil jwtUtil;

    public AuthViewController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String userEmail, @RequestParam String password) {
        if ("user".equals(userEmail) && "password".equals(password)) {
            String accessToken = jwtUtil.generateAccessToken(userEmail);
            String refreshToken = jwtUtil.generateRefreshToken(userEmail);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return tokens;
        } else {
            ErrorMessage errorMessage = ErrorMessage.UNCERTIFIED;
            throw new AuthException(errorMessage.getMessage(), errorMessage.getStatus());
        }
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestParam String refreshToken) {
        if (jwtUtil.validateRefreshToken(refreshToken)) {
            String userEmail = jwtUtil.extractUsername(refreshToken);  // 기존 이메일을 추출
            String newAccessToken = jwtUtil.generateAccessToken(userEmail);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);
            return tokens;
        } else {
            ErrorMessage errorMessage = ErrorMessage.INVALID_TOKEN;
            throw new AuthException(errorMessage.getMessage(), errorMessage.getStatus());
        }
    }
}
