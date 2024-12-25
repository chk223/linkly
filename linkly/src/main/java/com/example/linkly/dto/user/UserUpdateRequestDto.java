package com.example.linkly.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateRequestDto {

    @Size(max = 10)
    private final String name;

    // 프로필 사진 링크 (지금 단계에는..!)
    private final String profileImg;

    @Size(max = 50)
    private final String profileIntro;

    @Pattern(regexp = "^(https?://)?([\\w.-]+)?(\\.[a-z]{2,})?(:\\d+)?(/.*)?$")
    private final String profileUrl;

}
