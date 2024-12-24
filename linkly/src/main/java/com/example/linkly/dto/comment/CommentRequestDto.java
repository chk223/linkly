package com.example.linkly.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CommentRequestDto {

    private final UUID userId;
    private final String content;

}
