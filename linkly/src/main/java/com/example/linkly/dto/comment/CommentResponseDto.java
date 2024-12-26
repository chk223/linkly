package com.example.linkly.dto.comment;

import com.example.linkly.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private final Long id;
    private final UUID userId;
    private final String userEmail;
    private final String userName;
    private final Long feedId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Long heartNumber;

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getUser().getEmail(),
                comment.getUser().getName(),
                comment.getFeed().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getHeartCount()
        );
    }


}
