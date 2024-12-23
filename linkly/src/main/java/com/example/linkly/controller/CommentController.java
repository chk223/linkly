package com.example.linkly.controller;

import com.example.linkly.dto.comment.CommentRequestDto;
import com.example.linkly.dto.comment.CommentResponseDto;
import com.example.linkly.service.comment.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/{feedId}")
    public ResponseEntity<CommentResponseDto> addComment(
            @RequestBody CommentRequestDto requestDto,
            @PathVariable Long feedId
            ){

        CommentResponseDto commentResponseDto = commentService.addComment(requestDto.getContent(),feedId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    // TODO findFeedById 이름바꿀예정!
    // 댓글 특정 피드 리스트
    @GetMapping("/{feedId}")
    public ResponseEntity<CommentResponseDto> findFeedById(@PathVariable Long feedId) {

        CommentResponseDto commentResponseDto = commentService.findFeedById(feedId);

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    // 댓글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponseDto> update(@PathVariable Long id, @RequestBody CommentRequestDto dto) {
      //  log.info("id={} content{} userId{} ",id,dto.getContent(),dto.getUserId());
        CommentResponseDto updateComment = commentService.update(id, dto.getContent(),dto.getUserId());

        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, String userId) {

        commentService.delete(id,userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
