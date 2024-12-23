package com.example.linkly.dto.friend;

import java.time.LocalDateTime;

public class FriendResponseDto {

    //속성
    private final Long id;
    private final String followerId;
    private final String followingId;
    private LocalDateTime createdAt;

    //생성자

    public FriendResponseDto(Long id, String followerId, String followingId, LocalDateTime createdAt) {
        this.id = id;
        this.followerId = followerId;
        this.followingId = followingId;
        this.createdAt = createdAt;
    }
    //기능
    //Getter
    public Long getId() {
        return id;
    }

    public String getFollowerId() {
        return followerId;
    }

    public String getFollowingId() {
        return followingId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
