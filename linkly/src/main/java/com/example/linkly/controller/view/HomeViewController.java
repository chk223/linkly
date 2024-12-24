package com.example.linkly.controller.view;

import com.example.linkly.dto.comment.CommentRequestDto;
import com.example.linkly.dto.comment.CommentResponseDto;
import com.example.linkly.dto.login.LoginRequestDto;
import com.example.linkly.dto.user.UserRequestDto;
import com.example.linkly.entity.Feed;
import com.example.linkly.entity.User;
import com.example.linkly.exception.AuthException;
import com.example.linkly.service.auth.AuthService;
import com.example.linkly.service.feed.FeedService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeViewController {
    private final FeedService feedService;

    @GetMapping("/")
    public String getFeeds(
            @RequestParam(defaultValue = "1") int page,   // 기본값: 1
            @RequestParam(defaultValue = "10") int size,  // 기본값: 10
            Model model) {
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
