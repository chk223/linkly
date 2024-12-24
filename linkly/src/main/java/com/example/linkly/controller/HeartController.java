package com.example.linkly.controller;

import com.example.linkly.service.heart.HeartService;
import com.example.linkly.util.HeartCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/hearts")
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;

    @PostMapping("/toggle")
    public ResponseEntity<String> toggleHeart(
            @RequestParam UUID userId,
            @RequestParam Long categoryId,
            @RequestParam HeartCategory category
            ) {
        String result = heartService.toggleHeart(userId, categoryId, category);

        return ResponseEntity.ok(result);
    }
}
