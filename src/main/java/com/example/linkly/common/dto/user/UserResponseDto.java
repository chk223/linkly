package com.example.linkly.common.dto.user;

import com.example.linkly.entity.User;
import com.example.linkly.common.util.grade.UserGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {

    private final UUID id;

    private final String email;

    private final String name;

    private final String profileImgUrl;

    private final String profileIntro;

    private final String profileUrl;

    private final UserGrade gradeVal;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.profileImgUrl = user.getProfileImg();
        this.profileIntro = user.getProfileIntro();
        this.profileUrl = user.getProfileUrl();
        this.gradeVal = user.getGrade();
    }
}
