package com.example.linkly.dto.feed;

import com.example.linkly.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class FeedResponseDto {

    private final Long id;
    private final User userId;
    private final String content;
    private final String imgUrl;
    private final Long heartCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    public FeedResponseDto(Long id, User userId, String content, String imgUrl, Long heartCount, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.imgUrl = imgUrl;
        this.content = content;
        this.heartCount = heartCount;
        this.createdAt = createdAt;
    }
}
