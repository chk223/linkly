package com.example.linkly.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwUpdateRequestDto {

    // 비밀번호 형식 : 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[a-zA-Z\\d@$!%*?&]{8,}$")
    @Size(min = 8)
    private String originalPw;

    // 비밀번호 형식 : 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함
    @NotBlank
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[a-zA-Z\\d@$!%*?&]{8,}$")
    @Size(min = 8)
    private String newPw;

    public PwUpdateRequestDto(String originalPw, String newPw) {
        this.originalPw = originalPw;
        this.newPw = newPw;
    }

    public PwUpdateRequestDto() {
    }
}
