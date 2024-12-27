package com.example.linkly.controller.view;

import com.example.linkly.dto.login.LoginRequestDto;
import com.example.linkly.exception.ApiException;
import com.example.linkly.service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/view/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthViewController {
    private final AuthService authService;

    @GetMapping("/login")
    public String displayLogin(Model model) {
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute @Valid LoginRequestDto loginRequestDto, BindingResult result, HttpServletResponse response, Model model) {
        if (result.hasErrors()) {
            return "auth/login";
        }
        try {
            authService.login(loginRequestDto, response);
            model.addAttribute("message", "로그인 성공");
            return "redirect:/";
        } catch (ApiException e) {
            String errorMessage = e.getErrorResponse().getMessage();
            log.info("에러메세지 = {} ", errorMessage);
            model.addAttribute("error", errorMessage);
            return "auth/login"; // login.html로 이동
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        authService.logout(response);
        return "redirect:/view/auth/login";
    }
}
