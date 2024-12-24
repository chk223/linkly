package com.example.linkly.controller.view;

import com.example.linkly.dto.login.LoginRequestDto;
import com.example.linkly.entity.User;
import com.example.linkly.exception.AuthException;
import com.example.linkly.exception.UserException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.repository.UserRepository;
import com.example.linkly.service.auth.AuthService;
import com.example.linkly.util.auth.JwtUtil;
import com.example.linkly.util.exception.ExceptionUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/view/auth")
@RequiredArgsConstructor
public class AuthViewController {
    private final AuthService authService;

    @GetMapping("/login")
    public String displayLogin(Model model) {
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDto loginRequestDto, BindingResult result, HttpServletResponse response, Model model) {
        if (result.hasErrors()) {
            return "login";
        }
        try {
            authService.login(loginRequestDto, response);
            model.addAttribute("message", "로그인 성공");
            return "redirect:/";
        } catch (UserException | AuthException e) {
            model.addAttribute("error", e.getMessage());
            return "login"; // login.html로 이동
        }
    }

}
