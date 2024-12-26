package com.example.linkly.controller;


import com.example.linkly.dto.friend.FriendRequestDto;
import com.example.linkly.dto.friend.FriendResponseDto;
import com.example.linkly.service.friend.FriendService;
import com.example.linkly.service.friend.FriendServiceImpl;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    //속성
    private final FriendService friendService;

    //기능
    //친구추가 (Follow)
    @PostMapping
    public ResponseEntity<FriendResponseDto> addFriend(@Valid @RequestBody FriendRequestDto request) {
        return new ResponseEntity<>(friendService.addFriend(request), HttpStatus.CREATED);
    }
    //나를 팔로우한 사람 조회(Follower)
    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<FriendResponseDto>> getMyFollowers(@PathVariable UUID userId) {
        return new ResponseEntity<>(friendService.getMyFollowers(userId), HttpStatus.OK);
    }
    //내가 팔로우한 사람 조회(Following)
    @GetMapping("/followings/{userId}")
    public ResponseEntity<List<FriendResponseDto>> getMyFollowings(@PathVariable UUID userId) {
        return new ResponseEntity<>(friendService.getMyFollowings(userId), HttpStatus.OK);
    }
    //친구삭제 (Unfollow)
    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> deleteFollowing(@PathVariable Long friendId) {
        friendService.deleteFollowing(friendId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
