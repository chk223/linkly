package com.example.linkly.common.dto.friend;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class FriendResponseDto {

    //기능
    //Getter
    //속성
    private final Long id;
    private final UUID userId;
    private final String name;
    private final String profileImg;
    private LocalDateTime createdAt;

    //생성자

    public FriendResponseDto(Long id, UUID userId, String name, String profileImg, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.profileImg = profileImg;
        this.createdAt = createdAt;
    }
}
