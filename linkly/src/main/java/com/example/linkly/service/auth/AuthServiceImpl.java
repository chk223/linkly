package com.example.linkly.service.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.linkly.config.PasswordEncoder;
import com.example.linkly.dto.login.LoginRequestDto;
import com.example.linkly.entity.User;
import com.example.linkly.exception.AuthException;
import com.example.linkly.exception.UserException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.UserRepository;
import com.example.linkly.util.auth.JwtUtil;
import com.example.linkly.util.exception.ExceptionUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    PasswordEncoder bcrypt = new PasswordEncoder();
    /**
     * 로그인 처리
     * @param loginRequestDto 로그인 요청 정보 (이메일, 비밀번호)
     * @return 로그인 성공 시 User 객체 반환
     * @throws AuthException 로그인 실패 시 예외 발생
     */
    @Override
    public ResponseEntity<Map<String, String>> apiLogin(LoginRequestDto loginRequestDto) throws AuthException {
        ErrorMessage errorMessage = ErrorMessage.UNCERTIFIED;
        // 사용자 이메일로 사용자 찾기
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.UNCERTIFIED, UserException.class));

        // 비밀번호 검증
        if (!bcrypt.matches(loginRequestDto.getPassword(),user.getPassword())) {
            throw new AuthException(errorMessage.getMessage(), errorMessage.getStatus());
        }
        // 로그인 성공 시
        log.info("이메일,비밀번호 일치 확인");
        String accessToken = jwtUtil.generateAccessToken(loginRequestDto.getEmail()); // 로그인 유저의 이메일로 수정
        String refreshToken = jwtUtil.generateRefreshToken(loginRequestDto.getEmail());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return ResponseEntity.ok(tokens);  // HTTP 200 OK와 함께 토큰 반환
    }

    /**
     * 뷰에서 사용할 로그인 로직
     * @param loginRequestDto 로그인 정보
     * @param response
     */
    @Override
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        log.info("로그인 시도 감지! email = {} , pw = {}",loginRequestDto.getEmail(),loginRequestDto.getPassword());
        // Email로 User 검증
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.EMAIL_NOT_FOUND, UserException.class));
        log.info("유저 탐색 완료! email ={}", user.getEmail());

        // 비밀번호 검증
        if (!bcrypt.matches(loginRequestDto.getPassword(),user.getPassword())) {
            log.info("비밀번호 틀림");
            throw ExceptionUtil.throwErrorMessage(ErrorMessage.UNCERTIFIED, AuthException.class);
        }

        // 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(loginRequestDto.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(loginRequestDto.getEmail());

        // Access 토큰 쿠키에 저장
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(60 * 60); // 1시간
        accessTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);

        // Refresh 토큰 쿠키에 저장
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7일
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);

        log.info("로그인 완료!");
    }
}
