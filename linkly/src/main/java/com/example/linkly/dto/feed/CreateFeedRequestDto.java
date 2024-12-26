package com.example.linkly.dto.feed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CreateFeedRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 40, message = "40글자 이내로 작성해주세요.")
    private String title;
    private String imgUrl;
    @NotBlank(message = "일정을 입력해주세요.")
    @Size(max = 100, message = "100글자 이내로 작성해주세요.")
    private String content;

    public CreateFeedRequestDto(String title, String imgUrl, String content) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
    }

    public CreateFeedRequestDto() {
    }
}
