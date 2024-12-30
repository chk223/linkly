package com.example.linkly.controller.view;

import com.example.linkly.common.dto.comment.CommentRequestDto;
import com.example.linkly.common.dto.comment.CommentResponseDto;
import com.example.linkly.common.exception.ApiException;
import com.example.linkly.service.comment.CommentService;
import com.example.linkly.service.heart.HeartService;
import com.example.linkly.common.util.HeartCategory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/view/comment")
@Slf4j
public class CommentViewController {
    private final CommentService commentService;
    private final HeartService heartService;
    @GetMapping("/comments/{feedId}")
    public String getComments(@PathVariable Long feedId, Model model, HttpServletRequest request) {
        log.info("여기?");
        List<CommentResponseDto> comments = commentService.findAllCommentFromFeed(feedId);

        List<CommentResponseDto> commentResponses = comments.stream().map(CommentResponseDto -> {
            boolean isLiked = heartService.isILikeThis(CommentResponseDto.getId(), HeartCategory.COMMENT, request);
            CommentResponseDto.setLiked(isLiked);
            return CommentResponseDto;
        }).collect(Collectors.toList());

        model.addAttribute("comments", commentResponses);
        model.addAttribute("feedId", feedId);
        model.addAttribute("commentRequestDto", new CommentRequestDto());
        return "comment/comments"; // Thymeleaf template name
    }

    // 댓글 생성
    @PostMapping("/add-comment/{feedId}")
    public String addComment(@PathVariable Long feedId, @ModelAttribute @Valid CommentRequestDto requestDto,BindingResult result, HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("commentRequestDto", requestDto); // 기존 데이터 유지
            model.addAttribute("feedId", feedId); // 경로 변수 유지
            return "comment/comments";
        }
        try{
            commentService.addComment(requestDto.getContent(), feedId,request);
            return "redirect:/view/comment/comments/"+feedId;
        } catch (ApiException e) {
            String errorMessage = e.getErrorResponse().getMessage();
            log.info("에러메세지: {}",errorMessage);
            model.addAttribute("error", errorMessage);
            return "comment/comments";
        }

    }

    @GetMapping("/edit-comment/{feedId}")
    public String displayCommentUpdateForm(@PathVariable Long feedId,Model model) {
        CommentResponseDto commentResponseDto = commentService.findCommentFeedById(feedId);
        model.addAttribute("comment",commentResponseDto);
        return "comment/editComment";
    }

    // 댓글 수정
    @RequestMapping("/edit-comment/{id}")
    public String update(@PathVariable Long id, @RequestParam String content, @RequestParam Long feedId) {
        log.info("댓글 수정 감지 !!내용= {} ",content);
        commentService.update(id, content);
        return "redirect:/view/comment/comments/"+feedId;
    }

    // 댓글 삭제
    @PostMapping("/remove-comment/{commentId}")
    public String delete(@PathVariable Long commentId, @RequestParam Long feedId) {
        commentService.delete(commentId);

        return "redirect:/view/comment/comments/" + feedId;
    }

    @GetMapping("/best/{feedId}")
    public String getBestComments(@PathVariable Long feedId,Model model) {

        List<CommentResponseDto> bestComments = commentService.heartCountNumber();
        model.addAttribute("feedId", feedId);
        model.addAttribute("bestComments",bestComments);
        return "comment/bestComments";
    }
}
