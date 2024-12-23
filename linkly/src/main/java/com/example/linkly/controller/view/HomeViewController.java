package com.example.linkly.controller.view;

import com.example.linkly.dto.comment.CommentRequestDto;
import com.example.linkly.dto.comment.CommentResponseDto;
import com.example.linkly.dto.login.LoginRequestDto;
import com.example.linkly.dto.user.UserRequestDto;
import com.example.linkly.entity.User;
import com.example.linkly.exception.AuthException;
import com.example.linkly.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class HomeViewController {
    private final AuthService authService;

    public HomeViewController(AuthService authService) {
        this.authService = authService;
    }

    @RequestMapping("/")
    public String displayHome() {
        return "index";
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

    @GetMapping("/add-comment/{feedId}")
    public String comments(@PathVariable Long feedId, Model model) {
//        List<CommentResponseDto> comments = commentService.getCommentsByFeedId(feedId);
        List<CommentResponseDto> comments = new ArrayList<>();
        model.addAttribute("comments", comments);
        model.addAttribute("feedId", feedId);
        model.addAttribute("commentRequestDto", new CommentRequestDto());
        return "comments";
    }
    @PostMapping("/add-comment")
    public String addComment(@ModelAttribute CommentRequestDto commentRequestDto, @RequestParam Long feedId, BindingResult result) {
        if (result.hasErrors()) {
            return "comments";
        }
        return "redirect:/";
    }

    @RequestMapping("/my-info")
    public String myInfo() {
        return "myInfo";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "signUp";
    }
    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute UserRequestDto userRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            return "signUp";
        }
        // 회원가입 처리 로직
        return "redirect:/";
    }

}
