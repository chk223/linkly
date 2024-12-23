package com.example.linkly.dto.user;

import com.example.linkly.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String name;

    private final String profileImgUrl;

    private final String profileIntro;

    private final String profileUrl;

    public UserResponseDto(User user) {
        this.name = user.getName();
        this.profileImgUrl = user.getProfileImg();
        this.profileIntro = user.getProfileIntro();
        this.profileUrl = user.getProfileUrl();
    }
}
