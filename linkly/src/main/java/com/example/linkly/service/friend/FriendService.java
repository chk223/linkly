package com.example.linkly.service.friend;

import com.example.linkly.dto.friend.FriendRequestDto;
import com.example.linkly.dto.friend.FriendResponseDto;

import java.util.List;
import java.util.UUID;


public interface FriendService {
    //친구 추가(C)
    FriendResponseDto addFriend(FriendRequestDto request);
    //나를 팔로우한 사람 조회 (R)
    List<FriendResponseDto> getMyFollowers(UUID userId);
    //내가 팔로우한 사람 조회 (R)
    List<FriendResponseDto> getMyFollowings(UUID userId);
    //친구삭제(D)
    void deleteFollowing(Long friendId);
}
