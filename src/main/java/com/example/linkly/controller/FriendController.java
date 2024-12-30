package com.example.linkly.controller;


import com.example.linkly.common.dto.friend.FriendResponseDto;
import com.example.linkly.service.friend.FriendService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<FriendResponseDto> addFriend(@Valid @RequestBody UUID id, HttpServletRequest request) {
        return new ResponseEntity<>(friendService.toggleFollow(id,request), HttpStatus.CREATED);
    }
    //나를 팔로우한 사람 조회(Follower)
    @GetMapping("/followers")
    public ResponseEntity<List<FriendResponseDto>> getMyFollowers(HttpServletRequest request) {
        return new ResponseEntity<>(friendService.getMyFollowers(request), HttpStatus.OK);
    }
    //내가 팔로우한 사람 조회(Following)
    @GetMapping("/followings")
    public ResponseEntity<List<FriendResponseDto>> getMyFollowings(HttpServletRequest request) {
        return new ResponseEntity<>(friendService.getMyFollowings(request), HttpStatus.OK);
    }
    //친구삭제 (Unfollow)
    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> deleteFollowing(@PathVariable Long friendId) {
        friendService.deleteFollowing(friendId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
