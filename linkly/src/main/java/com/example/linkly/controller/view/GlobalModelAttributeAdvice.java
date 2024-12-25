package com.example.linkly.controller.view;

import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.service.user.UserService;
import com.example.linkly.util.auth.ValidatorUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
@Slf4j
@ControllerAdvice
public class GlobalModelAttributeAdvice {
    private final ValidatorUser validatorUser;
    private final UserService userService;

    public GlobalModelAttributeAdvice(ValidatorUser validatorUser, UserService userService) {
        this.validatorUser = validatorUser;
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserResponseDto addUserToModel(HttpServletRequest request) {
        try {
            String userEmail = validatorUser.getUserEmailFromTokenOrThrow(request); // null 반환 가능
            if (userEmail == null) {
                return null; // 로그인하지 않은 상태
            }
            return userService.findByEmail(userEmail);
        } catch (Exception e) {
            log.error("Error adding user to model: {}", e.getMessage());
            return null; // 예외 발생 시에도 null 반환
        }
    }
}
