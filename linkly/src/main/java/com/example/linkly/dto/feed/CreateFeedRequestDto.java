package com.example.linkly.dto.feed;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateFeedRequestDto {

    private final UUID userId;
    private final String title;
    private final String imgUrl;
    private final String content;

    public CreateFeedRequestDto(UUID userId, String title, String imgUrl, String content) {
        this.userId = userId;
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
    }
}
