package com.example.linkly.dto.feed;

import lombok.Getter;

@Getter
public class CreateFeedRequestDto {

    private final String userId;
    private final String title;
    private final String imgUrl;
    private final String content;

    public CreateFeedRequestDto(String userId, String title, String imgUrl, String content) {
        this.userId = userId;
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
    }
}
