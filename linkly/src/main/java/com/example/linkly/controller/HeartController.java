package com.example.linkly.controller;

import com.example.linkly.dto.feed.FeedResponseDto;
import com.example.linkly.entity.Feed;
import com.example.linkly.entity.User;
import com.example.linkly.service.feed.FeedService;
import com.example.linkly.service.heart.HeartService;
import com.example.linkly.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/hearts")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    @PostMapping("/toggle")
    public ResponseEntity<String> toggleHeart(
           @RequestBody String userId,
           @RequestBody Long feedId
    ) {

        String result = heartService.toggleHeart(userId, feedId);
        log.info(result);
        return ResponseEntity.ok(result);
    }
}
