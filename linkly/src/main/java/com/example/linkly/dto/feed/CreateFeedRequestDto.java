package com.example.linkly.dto.feed;

import lombok.Getter;

@Getter
public class CreateFeedRequestDto {

    private final String userId;
    private final String imgUrl;
    private final String content;

    public CreateFeedRequestDto(String userId, String imgUrl, String content) {
        this.userId = userId;
        this.imgUrl = imgUrl;
        this.content = content;
    }
}
