package com.example.linkly.service.friend;

import com.example.linkly.common.dto.friend.FriendResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;


public interface FriendService {
    //친구 추가(C)
    FriendResponseDto toggleFollow(UUID id, HttpServletRequest request); // (UUID followerId)제거 이유: follower following 합침
    //나를 팔로우한 사람 조회 (R)
    List<FriendResponseDto> getMyFollowers(HttpServletRequest request);
    //내가 팔로우한 사람 조회 (R)
    List<FriendResponseDto> getMyFollowings(HttpServletRequest request);
    //친구삭제(D)
    void deleteFollowing(Long friendId);
    //내가 이 유저를 팔로우 했는지
    boolean isFollowed(UUID id, HttpServletRequest request);
}
