package com.example.linkly.controller.view;

import com.example.linkly.dto.friend.FriendResponseDto;
import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.entity.Feed;
import com.example.linkly.service.feed.FeedService;
import com.example.linkly.service.friend.FriendService;
import com.example.linkly.service.user.UserService;
import com.example.linkly.util.auth.ValidatorUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeViewController {
    private final FeedService feedService;
    private final ValidatorUser validatorUser;
    private final UserService userService;
    private final FriendService friendService;

    @GetMapping()
    public String getFeeds(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, Model model, HttpServletRequest request) {
        String userEmail = validatorUser.getUserEmailFromTokenOrThrow(request);
        if(userEmail == null) return "redirect:/view/auth/login";
        Page<Feed> feedsPagination = feedService.getFeedsPagination(page - 1, size);
        List<FriendResponseDto> myFollowings = friendService.getMyFollowings(request);
        model.addAttribute("feeds", feedsPagination);
        model.addAttribute("pageNumber", page);
        model.addAttribute("totalPages", feedsPagination.getTotalPages());
        model.addAttribute("myFollowing", myFollowings);
        return "index";  // index.html로 반환
    }
}
