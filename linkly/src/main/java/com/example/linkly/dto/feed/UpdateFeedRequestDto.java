package com.example.linkly.dto.feed;

import lombok.Getter;

@Getter
public class UpdateFeedRequestDto {

    private final String title;
    private final String content;
    private final String imgUrl;

    public UpdateFeedRequestDto(String title, String content, String imgUrl) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
    }

//    public UpdateFeedRequestDto(String title, String content, String userName) {
//        this.title = title;
//        this.content = content;
//        this.userName = userName;
//    }
}
