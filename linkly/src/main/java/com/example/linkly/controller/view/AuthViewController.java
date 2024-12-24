package com.example.linkly.controller.view;

import com.example.linkly.dto.login.LoginRequestDto;
import com.example.linkly.entity.User;
import com.example.linkly.exception.AuthException;
import com.example.linkly.exception.UserException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.UserRepository;
import com.example.linkly.util.auth.JwtUtil;
import com.example.linkly.util.exception.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthViewController {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String displayLogin(Model model) {
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "login";
    }

    @PostMapping("/login")
    public Map<String, String> login(@ModelAttribute LoginRequestDto loginRequestDto, BindingResult result) {
        ErrorMessage errorMessage = ErrorMessage.ENTITY_NOT_FOUND;
        //입력 한 Email 을 갖는 유저가 있는지 검증
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> ExceptionUtil.throwErrorMessage(ErrorMessage.EMAIL_NOT_FOUND, UserException.class));
        if (loginRequestDto.getPassword().equals(user.getPassword())) {//비밀번호 일치 여부 검증
            String accessToken = jwtUtil.generateAccessToken(loginRequestDto.getEmail());
            String refreshToken = jwtUtil.generateRefreshToken(loginRequestDto.getEmail());

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return tokens;
        } else {
            throw ExceptionUtil.throwErrorMessage(ErrorMessage.UNCERTIFIED, AuthException.class);
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
            throw ExceptionUtil.throwErrorMessage(ErrorMessage.INVALID_TOKEN, AuthException.class);
        }
    }
}
