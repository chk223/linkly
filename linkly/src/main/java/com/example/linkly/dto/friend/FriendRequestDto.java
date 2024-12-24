package com.example.linkly.dto.friend;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class FriendRequestDto {
    //속성
    @NotNull(message = "팔로워 ID를 입력하세요.")
    private UUID followerId;  //merge후 String->UUID로 타입변경
    @NotNull(message = "팔로잉 ID를 입력하세요.")
    private UUID followingId;  //merge후 String->UUID로 타입변경
}
