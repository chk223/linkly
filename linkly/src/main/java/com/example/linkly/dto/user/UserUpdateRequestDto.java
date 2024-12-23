package com.example.linkly.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    @Size(max = 10)
    private final String name;

    // 비밀번호 형식 : 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    @Size(min = 8)
    private final String password;

    // 프로필 사진 링크 (지금 단계에는..!)
    private final String profileImg;

    @Size(max = 50)
    private final String profileIntro;

    @Pattern(regexp = "^(https?://)?([\\w.-]+)?(\\.[a-z]{2,})?(:\\d+)?(/.*)?$")
    private final String profileUrl;

    public UserUpdateRequestDto(String name, String password, String profileImg, String profileIntro, String profileUrl) {
        this.name = name;
        this.password = password;
        this.profileImg = profileImg;
        this.profileIntro = profileIntro;
        this.profileUrl = profileUrl;
    }
}
