package com.example.linkly.controller.view;

import com.example.linkly.service.heart.HeartService;
import com.example.linkly.util.HeartCategory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/view/heart")
public class HeartViewController {
    private final HeartService heartService;
    @PostMapping("/toggle/{feedId}")
    public String toggleFeedHeart(@PathVariable Long feedId, HttpServletRequest request){
        heartService.toggleHeart(feedId, HeartCategory.FEED, request);
//        log.info("피드 좋아요 버튼 누름");
        return "redirect:/view/feed/" + feedId;
    }
    @PostMapping("/toggle/{feedId}/{commentId}")
    public String toggleCommentHeart(@PathVariable Long feedId, @PathVariable Long commentId, HttpServletRequest request){
        heartService.toggleHeart(commentId, HeartCategory.COMMENT, request);
//        log.info("댓글 좋아요 버튼 누름");
        return "redirect:/view/comment/comments/" + feedId;
    }
}
