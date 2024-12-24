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
    @NotNull(message = "내가 팔로우할 사용자 ID를 입력하세요.") //Validation 유효성검증
    private UUID followingId; //merge후 String-> UUID로 타입변경
    @NotNull(message = "나를 팔로우하는 사용자 ID를 입력하세요.") //Validation 유효성검증
    private UUID followerId; //merge후 String-> UUID로 타입변경

}
