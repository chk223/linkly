package com.example.linkly.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String userEmail;
    private String userPassword;
    private String userName;

    public UserRequestDto() {
    }

    public UserRequestDto(String userEmail, String userPassword, String userName) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
    }
}
