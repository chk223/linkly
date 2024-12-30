package com.example.linkly.controller.view;

import com.example.linkly.common.dto.friend.FriendResponseDto;
import com.example.linkly.service.friend.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/view/friend")
public class FriendViewController {
    private final FriendService friendService;
    //기능
    //친구추가 (Follow)
    @PostMapping("/follow/{id}")
    public String toggleFollow(@PathVariable UUID id, HttpServletRequest request) {
        friendService.toggleFollow(id, request);
        return "redirect:/view/user/info/"+id;
    }
    //나를 팔로우한 사람 조회(Follower)
    @GetMapping("/followers")
    public String getMyFollowers(Model model, HttpServletRequest request) {
        List<FriendResponseDto> myFollowers = friendService.getMyFollowers(request);
        model.addAttribute("followers", myFollowers);
        return "friend/myFollower";
    }
    //내가 팔로우한 사람 조회(Following)
    @GetMapping("/followings")
    public String getMyFollowings(Model model,HttpServletRequest request) {
        List<FriendResponseDto> myFollowings = friendService.getMyFollowings(request);
        model.addAttribute("followings", myFollowings);
        return "friend/myFollowing";
    }
    //친구삭제 (Unfollow)
    @PostMapping("/{friendId}")
    public String deleteFollowing(@PathVariable Long friendId) {
        friendService.deleteFollowing(friendId);
        return "redirect:/";
    }
}
