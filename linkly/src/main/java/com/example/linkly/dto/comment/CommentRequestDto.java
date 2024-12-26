package com.example.linkly.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommentRequestDto {

    private UUID userId;
    @NotBlank(message = "댓글 내용을 작성해주세요.")
    @Size(max = 100, message = "100글자 이내로 작성해주세요.")
    private String content;

    public CommentRequestDto(UUID userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public CommentRequestDto() {
    }
}
