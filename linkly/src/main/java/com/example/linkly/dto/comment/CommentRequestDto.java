package com.example.linkly.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String content;

    public CommentRequestDto() {
    }

    public CommentRequestDto(String content) {
        this.content = content;
    }
}
