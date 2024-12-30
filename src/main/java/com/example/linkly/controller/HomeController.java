package com.example.linkly.controller;

import com.example.linkly.common.dto.comment.CommentRequestDto;
import com.example.linkly.common.dto.comment.CommentResponseDto;
import com.example.linkly.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {
    private final AuthService authService;
    /**
     * 홈 페이지 API
     * @return 홈 페이지 정보
     */
    @GetMapping("/")
    public ResponseEntity<String> displayHome() {
        return ResponseEntity.ok("Welcome to the home page!");
    }

    /**
     * 피드 추가 페이지 API
     * @return 피드 추가 페이지 정보
     */
    @GetMapping("/add-feed")
    public ResponseEntity<String> addFeed() {
        return ResponseEntity.ok("Add feed page");
    }

    /**
     * 피드 상세 페이지 API
     * @return 피드 상세 페이지 정보
     */
    @GetMapping("/feed-detail")
    public ResponseEntity<String> feedDetail() {
        return ResponseEntity.ok("Feed detail page");
    }

    /**
     * 댓글 페이지 API
     * @param feedId 피드 ID
     * @return 댓글 목록
     */
    @GetMapping("/add-comment/{feedId}")
    public ResponseEntity<?> getComments(@PathVariable Long feedId) {
        List<CommentResponseDto> comments = new ArrayList<>();
        // 실제 데이터는 서비스에서 가져오는 방식으로 수정
        return ResponseEntity.ok(comments);
    }

    /**
     * 댓글 추가 API
     * @param commentRequestDto 댓글 정보
     * @param feedId 피드 ID
     * @return 댓글 추가 처리 결과
     */
    @PostMapping("/add-comment")
    public ResponseEntity<?> addComment(@RequestBody CommentRequestDto commentRequestDto, @RequestParam Long feedId) {
        // 댓글 추가 처리 로직
        return ResponseEntity.ok("Comment added successfully");
    }


}
