package com.example.linkly.dto.user;

import com.example.linkly.entity.User;
import com.example.linkly.grade.UserGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private final String name;

    private final String profileImgUrl;

    private final String profileIntro;

    private final String profileUrl;

    private final UserGrade gradeVal;

    public UserResponseDto(User user) {
        this.name = user.getName();
        this.profileImgUrl = user.getProfileImg();
        this.profileIntro = user.getProfileIntro();
        this.profileUrl = user.getProfileUrl();
        this.gradeVal = user.getGrade();
    }
}
