package com.example.linkly.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommentRequestDto {

    private UUID userId;
    private String content;

    public CommentRequestDto(UUID userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public CommentRequestDto() {
    }
}
