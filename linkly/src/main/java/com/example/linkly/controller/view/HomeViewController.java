package com.example.linkly.controller.view;

import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.entity.Feed;
import com.example.linkly.service.feed.FeedService;
import com.example.linkly.service.user.UserService;
import com.example.linkly.util.auth.ValidatorUser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeViewController {
    private final FeedService feedService;
    private final ValidatorUser validatorUser;
    private final UserService userService;

    @GetMapping()
    public String getFeeds(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, Model model) {
        Page<Feed> feedsPagination = feedService.getFeedsPagination(page - 1, size);
        model.addAttribute("feeds", feedsPagination);
        model.addAttribute("pageNumber", page);
        model.addAttribute("totalPages", feedsPagination.getTotalPages());
        return "index";  // index.html로 반환
    }
    /**
     * 여기서부터 테스트용
     */
    @GetMapping("/add-feed")
    public String addFeed() {
        return "addFeed";
    }

    @RequestMapping("/feed-detail")
    public String feedDetail() {
        return "feedDetail";
    }






}
