package com.example.linkly.common.util.grade;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserGrade {
    BASIC("BASIC"),
    VIP("VIP");

    private final String value;

    UserGrade(String value) {
        this.value = value;
    }
}
