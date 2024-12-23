package com.example.linkly.dto.feed;

import lombok.Getter;

@Getter
public class UpdateFeedRequestDto {

    private final String title;
    private final String content;

    public UpdateFeedRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

//    public UpdateFeedRequestDto(String title, String content, String userName) {
//        this.title = title;
//        this.content = content;
//        this.userName = userName;
//    }
}
