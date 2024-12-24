package com.example.linkly.controller.view;

import com.example.linkly.dto.comment.CommentRequestDto;
import com.example.linkly.dto.comment.CommentResponseDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class CommentViewController {
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
}
