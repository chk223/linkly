package com.example.linkly.dto.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String email;
    private String password;

    public LoginRequestDto(String userEmail, String userPassword, String userName) {
        this.email = userEmail;
        this.password = userPassword;
    }

    public LoginRequestDto() {
    }
}
