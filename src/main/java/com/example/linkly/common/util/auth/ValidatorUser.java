package com.example.linkly.common.util.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidatorUser {
    private final JwtUtil jwtUtil;
    public String getUserEmailFromTokenOrThrow(HttpServletRequest request) {
        // 쿠키에서 Access Token 가져오기
        Cookie[] cookies = request.getCookies();
        String accessToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                    break;
                }
            }
        }
        // Access Token이 없거나 유효하지 않은 경우 처리
        if (accessToken == null || !jwtUtil.validateAccessToken(accessToken)) {
//            ExceptionUtil.throwErrorMessage(ErrorMessage.INVALID_TOKEN, ApiException.class);
            return null;
        }
        return jwtUtil.extractUsername(accessToken);
    }
}
