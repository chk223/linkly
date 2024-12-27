package com.example.linkly.controller.view;

import com.example.linkly.dto.friend.FriendResponseDto;
import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.entity.Feed;
import com.example.linkly.grade.UserGrade;
import com.example.linkly.service.feed.FeedService;
import com.example.linkly.service.friend.FriendService;
import com.example.linkly.service.user.UserService;
import com.example.linkly.util.auth.ValidatorUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeViewController {
    private final FeedService feedService;
    private final ValidatorUser validatorUser;
    private final UserService userService;
    private final FriendService friendService;

    @GetMapping
    public String getFeeds(
            @RequestParam(defaultValue = "friends") String feedType, // 기본값은 친구 피드
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model,
            HttpServletRequest request) {

        String userEmail = validatorUser.getUserEmailFromTokenOrThrow(request);
        if (userEmail == null) return "redirect:/view/auth/login";

        UserResponseDto user = userService.findByEmail(userEmail);
        List<FriendResponseDto> myFollowing = friendService.getMyFollowings(request);
        Page<Feed> feeds = null; // 보여줄 피드 리스트
        if ("random".equals(feedType)) {
            feeds = feedService.getFeedsPagination(page - 1, size);
            log.info("랜덤피드 개수: {}",feeds.getSize());
            model.addAttribute("feedType", "random");
        } else {
            feeds = feedService.getFriendFeeds(user.getId(), page - 1, size);
            model.addAttribute("feedType", "friends");
        }
        model.addAttribute("myFollowing", myFollowing);
        model.addAttribute("feeds", feeds.getContent());
        model.addAttribute("pageNumber", page);
        model.addAttribute("totalFeedPages", feeds.getTotalPages());
        model.addAttribute("vip", UserGrade.VIP);
        return "index";  // index.html 반환
    }
    @GetMapping("/getMoreFeeds")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getMoreFeeds(
            @RequestParam int page, @RequestParam int size) {

        Page<Feed> feedPage = feedService.getFeedsPagination(page - 1, size);  // 페이지 0부터 시작

        Map<String, Object> response = new HashMap<>();
        response.put("feeds", feedPage.getContent());
        response.put("totalPages", feedPage.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
