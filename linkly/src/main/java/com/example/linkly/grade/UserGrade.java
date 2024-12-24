package com.example.linkly.grade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserGrade {
    BASIC("BASIC"),
    VIP("VIP");

    private final String value;

    UserGrade(String value) {
        this.value = value;
    }

    //대소문자 구분 x
    @JsonCreator
    public static UserGrade from(String value) {
        return UserGrade.valueOf(value.toUpperCase());
    }
}
