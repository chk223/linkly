package com.example.linkly.dto.feed;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FeedPageResponseDto {

    private final String userName;
    private final String title;
    private final String imgUrl;
    private final String content;
    private final Long heartCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    public FeedPageResponseDto(String userName, String title, String imgUrl, String content, Long heartCount, LocalDateTime createdAt) {
        this.userName = userName;
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
        this.heartCount = heartCount;
        this.createdAt = createdAt;
    }
}
