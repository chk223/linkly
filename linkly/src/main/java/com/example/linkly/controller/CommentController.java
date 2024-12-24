package com.example.linkly.controller;

import com.example.linkly.dto.comment.CommentRequestDto;
import com.example.linkly.dto.comment.CommentResponseDto;
import com.example.linkly.entity.Heart;
import com.example.linkly.entity.User;
import com.example.linkly.service.comment.CommentService;
import com.example.linkly.service.heart.HeartService;
import com.example.linkly.util.HeartCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final HeartService heartService;

    // 댓글 생성
    @PostMapping("/{feedId}")
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable Long feedId,
            @RequestBody CommentRequestDto requestDto
            ){

        //log.info("feedid={} userid={} commnet={}",feedId,requestDto.getUserId(),requestDto.getContent());
        CommentResponseDto commentResponseDto = commentService.addComment(requestDto.getUserId(),requestDto.getContent(),feedId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    // 댓글 특정 피드 리스트
    @GetMapping("/{feedId}")
    public ResponseEntity<CommentResponseDto> findCommentFeedById(@PathVariable Long feedId) {

        CommentResponseDto commentResponseDto = commentService.findCommentFeedById(feedId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    // 댓글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable Long id, @RequestBody CommentRequestDto dto) {
        log.info("id={} content{} userId{} ",id,dto.getContent(),dto.getUserId());
        CommentResponseDto updateComment = commentService.update(id, dto.getContent(),dto.getUserId());

        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        commentService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
