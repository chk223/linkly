package com.example.linkly.controller.view;

import com.example.linkly.config.PasswordEncoder;
import com.example.linkly.dto.user.PwUpdateRequestDto;
import com.example.linkly.dto.user.SignUpRequestDto;
import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.dto.user.UserUpdateRequestDto;
import com.example.linkly.exception.UserException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.service.friend.FriendService;
import com.example.linkly.service.user.UserService;
import com.example.linkly.util.auth.ValidatorUser;
import com.example.linkly.util.exception.ExceptionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String signUp(@ModelAttribute SignUpRequestDto requestDto, BindingResult result) {
        if (result.hasErrors()) {
            return "auth/signUp";
        }
        userService.signUp(
            requestDto.getEmail(),
            bcrypt.encode(requestDto.getPassword()),
            requestDto.getName()
        );

        return "redirect:/";
    }

    @GetMapping("/info/{id}")
    public String displayUserProfile(@PathVariable UUID id, Model model, HttpServletRequest request) {
        boolean isFollowing = friendService.isFollowed(id, request);
        UserResponseDto userResponseDto = userService.getInfo(id);
//        log.info("등급 ={},이미지 ={}", userResponseDto.getGradeVal(), userResponseDto.getProfileImgUrl());
        model.addAttribute("userResponseDto", userResponseDto);
        model.addAttribute("isFollowing", isFollowing);
        return "user/myInfo";  // userProfile.html 템플릿 반환
    }

    // 유저 조회
    @GetMapping("/filter")
    public String findByNameContains(Model model, @RequestParam String name) {
        List<UserResponseDto> userResponseDtoList = userService.findByNameContains(name);
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
    public String updateUser(@PathVariable UUID id,@ModelAttribute UserUpdateRequestDto requestDto) {
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
            ExceptionUtil.throwErrorMessage(ErrorMessage.VALID_ERROR, UserException.class);
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
    public String withdrawUser(@PathVariable UUID id, @RequestParam String password) {
//        log.info("탈퇴 감지!!!! id={}, pw = {}", id, password);
        userService.deleteUser(id, password);
        return "redirect:/view/user/sign-up";
    }

    // 등급 설정
    @PostMapping("/change-grade/{id}")
    public String updateGrade(@PathVariable UUID id){
//        log.info("등급 변경 시도!!!!");
        userService.updateGrade(id);
//        log.info("등급 변경 완료!!!!");
        return "redirect:/";
    }



}