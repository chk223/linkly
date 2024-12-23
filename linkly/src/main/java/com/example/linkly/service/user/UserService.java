package com.example.linkly.service.user;

import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.dto.user.UserUpdateRequestDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    void signUp(String name, String email, String password);

    List<UserResponseDto> findByNameLike(String name);

    void updateUser(UUID id, UserUpdateRequestDto userUpdateRequestDto);

    void deleteUser(UUID id, String password);
}
