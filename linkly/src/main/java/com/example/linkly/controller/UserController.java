package com.example.linkly.controller;


import com.example.linkly.config.PasswordEncoder;
import com.example.linkly.dto.user.PwUpdateRequestDto;
import com.example.linkly.dto.user.SignUpRequestDto;
import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.dto.user.UserUpdateRequestDto;
import com.example.linkly.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;
    private final PasswordEncoder bcrypt;

    // 유저 생성
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequestDto requestDto) {
        userService.signUp(
                requestDto.getEmail(),
                bcrypt.encode(requestDto.getPassword()),
                requestDto.getName()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 유저 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findByNameContains(@RequestParam String name) {
        List<UserResponseDto> userResponseDtoList = userService.findByNameContains(name);

        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    // 유저 수정
    // 이름 변경 및 프로필 사진, 소개, 링크 설정
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable UUID id,
            @RequestBody UserUpdateRequestDto requestDto
    ) {
        userService.updateUser(id, requestDto);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    // 비밀번호 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePw(
            @PathVariable UUID id,
            @RequestBody PwUpdateRequestDto requestDto
    ) {
        userService.updatePw(id, requestDto);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id, @RequestBody String password){

        userService.deleteUser(id, password);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
