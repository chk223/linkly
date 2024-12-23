package com.example.linkly.service.friend;

import com.example.linkly.dto.friend.FriendRequestDto;
import com.example.linkly.dto.friend.FriendResponseDto;

import java.util.List;


public interface FriendService {
    //친구 추가(C)
    FriendResponseDto addFriend(FriendRequestDto request);
    //나를 팔로우한 사람 조회 (R)
    List<FriendResponseDto> getMyFollowers(String userId);
    //내가 팔로우한 사람 조회 (R)
    List<FriendResponseDto> getMyFollowings(String userId);
    //친구삭제(D)
    void deleteFollowing(Long friendId);
}
