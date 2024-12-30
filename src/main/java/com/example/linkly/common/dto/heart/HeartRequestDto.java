package com.example.linkly.common.dto.heart;

import com.example.linkly.entity.Feed;
import com.example.linkly.entity.User;
import lombok.Getter;
import lombok.extern.java.Log;

@Getter
public class HeartRequestDto {

    private final String userId;
    private final Long feedId;

    public HeartRequestDto(String userId, Long feedId) {
        this.userId = userId;
        this.feedId = feedId;
    }
}
