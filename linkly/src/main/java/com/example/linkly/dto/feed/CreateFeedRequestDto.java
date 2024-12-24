package com.example.linkly.dto.feed;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateFeedRequestDto {
    private String title;
    private String imgUrl;
    private String content;

    public CreateFeedRequestDto(String title, String imgUrl, String content) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
    }

    public CreateFeedRequestDto() {
    }
}
