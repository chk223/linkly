package com.example.linkly.filter;

import com.example.linkly.util.auth.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter implements Filter {
    private final JwtUtil jwtUtil;
    private static final String[] WHITE_LIST = {"/api/auth/login","/api/user/sign-up","/login","auth/login","user/sign-up","/*"};
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Autowired
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authorizationHeader = httpRequest.getHeader("Authorization");
        String requestURI = httpRequest.getRequestURI();
        log.info("접근 URI ={} ", requestURI);

        // 화이트리스트 경로 확인
        if (isWhiteList(requestURI)) {
            log.info("화이트리스트에서 URI 발견: {}",requestURI);
            chain.doFilter(request, response); // 화이트리스트에 있는 경우 바로 통과
            return;
        }
        log.info("인증 검증!!");
        // 인증 검증
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                if (jwtUtil.validateAccessToken(token)) {
                    log.info("access 토큰 검사완료!");
                    chain.doFilter(request, response); // Access Token이 유효하면 요청 처리
                    return;
                }
            } catch (ExpiredJwtException e) {
                // Access Token이 만료된 경우
                log.error("Access Token expired: {}", requestURI);
                httpResponse.setHeader("Token-Expired", "true"); // 클라이언트가 토큰 만료 여부를 감지하도록 헤더 추가
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Access Token expired");
                return;
            }
        }

        // 인증 실패 처리
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("Invalid Token");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    /**
     * 로그인 여부를 확인하는 URL인지 체크
     * @param requestURI 요청하는 URI
     * @return URI가 whiteListURI에 포함되는지 여부
     */
    private boolean isWhiteList(String requestURI) {
        for (String pattern : WHITE_LIST) {
            if (pathMatcher.match(pattern, requestURI)) {
                return true;
            }
        }
        return false;
    }
}
