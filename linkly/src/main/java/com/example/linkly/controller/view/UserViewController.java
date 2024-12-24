package com.example.linkly.controller.view;

import com.example.linkly.config.PasswordEncoder;
import com.example.linkly.dto.user.SignUpRequestDto;
import com.example.linkly.dto.user.UserRequestDto;
import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.dto.user.UserUpdateRequestDto;
import com.example.linkly.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/view/user/")
@RequiredArgsConstructor
public class UserViewController {

    private final UserService userService;
    private final PasswordEncoder bcrypt;

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("SignUpRequestDto", new SignUpRequestDto());
        return "signUp";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute SignUpRequestDto requestDto, BindingResult result) {
        if (result.hasErrors()) {
            return "signUp";
        }
        userService.signUp(
            requestDto.getEmail(),
            bcrypt.encode(requestDto.getPassword()),
            requestDto.getName()
        );

        return "redirect:/";
    }

    // 유저 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findByNameLike(@RequestParam String name) {
        List<UserResponseDto> userResponseDtoList = userService.findByNameLike(name);
        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    // 유저 수정
    // 이름, 패스워드 변경 및 프로필 사진, 소개, 링크 설정
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable UUID id,
            @RequestBody UserUpdateRequestDto requestDto
    ) {
        userService.updateUser(id, requestDto);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable UUID id, @RequestBody String password){

        userService.deleteUser(id, password);

        return "redirect:/";
    }

    @RequestMapping("/my-info")
    public String myInfo() {
        return "myInfo";
    }

}
