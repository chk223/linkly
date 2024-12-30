package com.example.linkly.common.dto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank(message = "이메일이 비어있습니다.")
    private String email;
    @NotBlank(message = "비밀번호 값이 비어있습니다.")
    private String password;

    public LoginRequestDto(String userEmail, String userPassword, String userName) {
        this.email = userEmail;
        this.password = userPassword;
    }

    public LoginRequestDto() {
    }
}
