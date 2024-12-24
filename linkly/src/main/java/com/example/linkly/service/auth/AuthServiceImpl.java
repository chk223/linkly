package com.example.linkly.service.auth;

import com.example.linkly.dto.login.LoginRequestDto;
import com.example.linkly.entity.User;
import com.example.linkly.exception.AuthException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.UserRepository;
import com.example.linkly.util.auth.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 로그인 처리
     * @param loginRequestDto 로그인 요청 정보 (이메일, 비밀번호)
     * @return 로그인 성공 시 User 객체 반환
     * @throws AuthException 로그인 실패 시 예외 발생
     */
    @Override
    public ResponseEntity<Map<String, String>> login(LoginRequestDto loginRequestDto) throws AuthException {
        ErrorMessage errorMessage = ErrorMessage.UNCERTIFIED;
        // 사용자 이메일로 사용자 찾기
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new AuthException(errorMessage.getMessage(), errorMessage.getStatus()));

        // 비밀번호 검증
        if (!user.getPassword().equals(loginRequestDto.getPassword())) {
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
}
