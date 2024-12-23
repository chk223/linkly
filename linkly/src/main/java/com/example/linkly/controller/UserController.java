package com.example.linkly.controller;


import com.example.linkly.config.PasswordEncoder;
import com.example.linkly.dto.user.SignUpRequestDto;
import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.dto.user.UserUpdateRequestDto;
import com.example.linkly.entity.User;
import com.example.linkly.repository.UserRepository;
import com.example.linkly.service.user.UserService;
import com.example.linkly.service.user.UserServiceImpl;
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

    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;
    private final PasswordEncoder bcypt;

    // 유저 생성
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequestDto requestDto) {
        userServiceImpl.signUp(
                requestDto.getEmail(),
                bcypt.encode(requestDto.getPassword()),
                requestDto.getName()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 유저 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findByNameLike(@RequestParam String name) {
        List<UserResponseDto> userResponseDtoList = userServiceImpl.findByNameLike(name);

        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    // 유저 수정
    // 이름, 패스워드 변경 및 프로필 사진, 소개, 링크 설정
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable UUID id,
            @RequestBody UserUpdateRequestDto requestDto
    ) {
        userServiceImpl.updateUser(id, requestDto);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id, @RequestBody String password){

        userServiceImpl.deleteUser(id, password);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
