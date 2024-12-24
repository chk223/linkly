package com.example.linkly.dto.friend;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class FriendResponseDto {

    //속성
    private final Long id;
    private final UUID followerId;      //merge후 String->UUID로 타입변경
    private final UUID followingId;     //merge후 String->UUID로 타입변경
    private LocalDateTime createdAt;

    //생성자

    public FriendResponseDto(Long id, UUID followerId, UUID followingId, LocalDateTime createdAt) {  //merge후 String->UUID로 타입변경
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

    public UUID getFollowerId() {
        return followerId;
    }

    public UUID getFollowingId() {
        return followingId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
