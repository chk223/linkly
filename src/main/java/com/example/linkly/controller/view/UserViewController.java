package com.example.linkly.controller.view;

import com.example.linkly.common.config.PasswordEncoder;
import com.example.linkly.common.dto.user.PwUpdateRequestDto;
import com.example.linkly.common.dto.user.SignUpRequestDto;
import com.example.linkly.common.dto.user.UserResponseDto;
import com.example.linkly.common.dto.user.UserUpdateRequestDto;
import com.example.linkly.common.exception.ApiException;
import com.example.linkly.common.exception.util.ErrorMessage;
import com.example.linkly.service.friend.FriendService;
import com.example.linkly.service.user.UserService;
import com.example.linkly.common.util.auth.ValidatorUser;
import com.example.linkly.common.exception.ExceptionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/view/user/")
@RequiredArgsConstructor
public class UserViewController {
    private final ValidatorUser validatorUser;
    private final UserService userService;
    private final FriendService friendService;
    private final PasswordEncoder bcrypt;

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("SignUpRequestDto", new SignUpRequestDto());
        return "auth/signUp";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute @Valid SignUpRequestDto requestDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "auth/signUp";
        }
        try {
            userService.signUp(
                    requestDto.getEmail(),
                    bcrypt.encode(requestDto.getPassword()),
                    requestDto.getName()
            );
            model.addAttribute("message", "로그인 성공");
            return "redirect:/";
        } catch (ApiException e) {
            String errorMessage = e.getErrorResponse().getMessage();
            model.addAttribute("error", errorMessage);
            return "auth/signUp";
        }

    }

    @GetMapping("/info/{id}")
    public String displayUserProfile(@PathVariable UUID id, Model model, HttpServletRequest request) {
        boolean isFollowing = friendService.isFollowed(id, request);
        UserResponseDto userResponseDto = userService.getInfo(id);
        model.addAttribute("userResponseDto", userResponseDto);
        model.addAttribute("isFollowing", isFollowing);
        return "user/myInfo";  // userProfile.html 템플릿 반환
    }

    // 유저 조회
    @GetMapping("/filter")
    public String findByNameContains(Model model, @RequestParam String name) {
        List<UserResponseDto> userResponseDtoList = userService.findByNameContaining(name);
        model.addAttribute("users", userResponseDtoList);
        return "user/searchUser";
    }

    @GetMapping("/edit/{id}")
    public String displayUpdateUserFrom(@PathVariable UUID id, Model model) {
        UserResponseDto userResponseDto = userService.getInfo(id);
        UserUpdateRequestDto updateDto = new UserUpdateRequestDto(
                userResponseDto.getName(),
                userResponseDto.getProfileImgUrl(),
                userResponseDto.getProfileIntro(),
                userResponseDto.getProfileUrl()
        );
        model.addAttribute("updateDto", updateDto);
        return "user/editUser";
    }

    // 유저 수정
    // 이름 변경 및 프로필 사진, 소개, 링크 설정
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable UUID id,@ModelAttribute @Valid UserUpdateRequestDto requestDto) {
        userService.updateUser(id, requestDto);
        return "redirect:/view/user/info/" + id;
    }

    // 비밀번호 수정
    @PatchMapping("/{id}")
    public String updatePw(
            @PathVariable UUID id,
            @RequestBody PwUpdateRequestDto requestDto
    ) {
        userService.updatePw(id, requestDto);

        return "redirect:/view/user/sign-up";
    }
    // 비밀번호 수정 폼 표시
    @GetMapping("/edit-password")
    public String showEditPasswordForm(Model model) {
        model.addAttribute("passwordDto", new PwUpdateRequestDto());
        return "user/editPassword";
    }

    // 비밀번호 수정 처리
    @PostMapping("/edit-password")
    public String updatePassword(@ModelAttribute @Valid PwUpdateRequestDto passwordDto, BindingResult result, @ModelAttribute("user") UserResponseDto responseDto) {
//        log.info("비번 변경 감지 id = {} old_pw = {} new_pw ={}", responseDto.getId(), passwordDto.getOriginalPw(),passwordDto.getNewPw());
        if (result.hasErrors()) {
            log.info("검증 에러!!!!!");
            ExceptionUtil.throwErrorMessage(ErrorMessage.VALID_ERROR, ApiException.class);
            return "redirect:/view/user/edit-password";
        }
        log.info("검증 완료 후 비번 변경 감지 id = {} old_pw = {} new_pw ={}", responseDto.getId(), passwordDto.getOriginalPw(),passwordDto.getNewPw());
        // 비밀번호 수정 서비스 호출
        userService.updatePw(responseDto.getId(), passwordDto);
        return "redirect:/view/user/info/"+responseDto.getId();  // 비밀번호 수정 후 리다이렉트할 페이지
    }
    @GetMapping("/withdraw/{id}")
    public String displayWithdrawForm(@PathVariable UUID id, Model model) {
        model.addAttribute("userId", id); // 사용자 ID를 모델에 추가
        return "user/confirmWithdraw";
    }

    @PostMapping("/withdraw/{id}")
    public String withdrawUser(@PathVariable UUID id, @RequestParam String password, HttpServletResponse response) {
//        log.info("탈퇴 감지!!!! id={}, pw = {}", id, password);
        userService.deleteUser(id, password, response);
        return "redirect:/view/user/sign-up";
    }

    // 등급 설정
    @PostMapping("/change-grade/{id}")
    public String updateGrade(@PathVariable UUID id){
//        log.info("등급 변경 시도!!!!");
        userService.toggleGrade(id);
//        log.info("등급 변경 완료!!!!");
        return "redirect:/";
    }



}
