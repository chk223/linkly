package com.example.linkly.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userGrade = (String) request.getAttribute("userGrade");
        if ("vip".equals(userGrade)) {
            return true; // 관리자 권한 통과
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 권한 없음
        return false;
    }
}
