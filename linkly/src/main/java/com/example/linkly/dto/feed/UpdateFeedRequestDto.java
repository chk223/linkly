package com.example.linkly.dto.feed;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateFeedRequestDto {

    @Size(max = 40, message = "40글자 이내로 작성해주세요.")
    private final String title;
    @Size(max = 100, message = "100글자 이내로 작성해주세요.")
    private final String content;

    public UpdateFeedRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
