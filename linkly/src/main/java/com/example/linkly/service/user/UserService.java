package com.example.linkly.service.user;

import com.example.linkly.dto.user.PwUpdateRequestDto;
import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.dto.user.UserUpdateRequestDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {

    void signUp(String name, String email, String password);

    UserResponseDto getInfo(UUID id);

    List<UserResponseDto> findByNameContains(String name);

    UserResponseDto findByEmail(String email);

    void updateUser(UUID id, UserUpdateRequestDto dto);

    void updatePw(UUID id, PwUpdateRequestDto dto);

    void deleteUser(UUID id, String password);

    void updateGrade(UUID id);

}
