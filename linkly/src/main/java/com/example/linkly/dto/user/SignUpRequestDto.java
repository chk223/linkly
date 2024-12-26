package com.example.linkly.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {

    @Email
    @NotBlank
    private String email;

    // 비밀번호 형식 : 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[a-zA-Z\\d@$!%*?&]{8,}$")
    @Size(min = 8)
    private String password;

    @Size(min = 0, max = 10)
    private String name;

    public SignUpRequestDto(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public SignUpRequestDto() {
    }
}
