package com.example.linkly.controller.view;

import com.example.linkly.dto.comment.CommentRequestDto;
import com.example.linkly.dto.comment.CommentResponseDto;
import com.example.linkly.dto.user.UserResponseDto;
import com.example.linkly.entity.Comment;
import com.example.linkly.entity.User;
import com.example.linkly.exception.UserException;
import com.example.linkly.exception.util.ErrorMessage;
import com.example.linkly.service.comment.CommentService;
import com.example.linkly.service.heart.HeartService;
import com.example.linkly.service.user.UserService;
import com.example.linkly.util.HeartCategory;
import com.example.linkly.util.auth.ValidatorUser;
import com.example.linkly.util.exception.ExceptionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/view/comment")
@Slf4j
public class CommentViewController {
    private final CommentService commentService;
    private final HeartService heartService;
    private final ValidatorUser validatorUser;
    private final UserService userService;

    @GetMapping("/comments/{feedId}")
    public String getComments(@PathVariable Long feedId, Model model, HttpServletRequest request) {
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

    @PostMapping("/add-comment")
    public String addComment(@ModelAttribute CommentRequestDto commentRequestDto, @RequestParam Long feedId, BindingResult result) {
        if (result.hasErrors()) {
            return "comment/comments";
        }
        return "redirect:/";
    }

    // 댓글 생성
    @PostMapping("/add-comment/{feedId}")
    public String addComment(@PathVariable Long feedId, @ModelAttribute CommentRequestDto requestDto, HttpServletRequest request) {
//        log.info("댓글 생성 감지!! feedId={} comment={}",feedId,requestDto.getContent());
        commentService.addComment(requestDto.getContent(), feedId,request);
        return "redirect:/view/comment/comments/"+feedId;
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
        log.info("삭제 시도!! 댓글 id = {} 피드 id ={} ", commentId, feedId);
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
