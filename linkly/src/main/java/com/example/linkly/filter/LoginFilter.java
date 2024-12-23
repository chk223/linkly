package com.example.linkly.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    // 회원가입, 로그인 요청은 인증 처리에서 제외
    private static final String[] WHITE_LIST = {"/users/signup", "/users/login", "/users/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // request를 쉽게 사용하기 위해 down casting
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.info("로그인 필터 로직 실행");

        // 로그인을 체크해야하는지 검사
        // WHITE_LIST에 포함된 경우 true -> !true = false

        if(!isWhiteList((requestURI))) { // WHITE_LIST에 포함되어 있지 않다면

            // 로그인 확인 (로그인 하면 session에 값이 저장되어 있다는 가정)
            // 세션이 존재하면 가져온다. 세션이 없으면 session = null
            HttpSession session = httpRequest.getSession(false);

            // 로그인하지 않은 사용자인 경우
            if (session == null || session.getAttribute("USER_ID") == null) {
                throw new RuntimeException("로그인 해주세요. ");
            }

        }
        log.info("로그인 성공1"); // doFilter 전
        chain.doFilter(request, response);
        log.info("로그인 성공2"); // 루프를 돌고옴
    }

    public boolean isWhiteList(String requestURI) {
        //WHITE_LIST로 만들어놨던 URL에 포함되어 있지 않다면 false 반환
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}